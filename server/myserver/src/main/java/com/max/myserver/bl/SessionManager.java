package com.max.myserver.bl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.max.myserver.data.Constants;
import com.max.myserver.data.FileData;
import com.max.myserver.data.QueueItem;
import akka.NotUsed;
import akka.dispatch.MessageDispatcher;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import java.util.Optional;


public class SessionManager implements IFileReadyCallBack {
	private String userName;
	private String sessionID;
	Materializer materializer = null;
	MessageDispatcher executionContext = null;
	HashMap<String,QueueItem> filesQueueToClient = null;
	
	public SessionManager (Materializer materializer,MessageDispatcher executionContext) {
		this.materializer = materializer;
		this.executionContext = executionContext;
	}
	
	/*
	 *create flow template of Constants.FILES_COUNT_TOTAL items based on one 
	 *file request.
	 */
	public  Flow<Message, Message, NotUsed> createGreeter() {
	  return Flow.of(Message.class)
	  .mapConcat (m-> this.startFileCreationAsyncAndModifyOutput(m))
	  .map(m->m.asTextMessage())
	  .mapAsyncUnordered(10, (t) -> {
			   CompletableFuture<Optional<FileData>> complStage =  new CompletableFuture<Optional<FileData>> ();
			   updateFileInQueue(t.getStrictText(), complStage, null);
			   //System.out.println("in mapAsyncUnordered: " +t.getStrictText());
			   return complStage;
		   })
		   .filter(o -> o.isPresent())
		   .map(o1->o1.get())
		   //.buffer(5, OverflowStrategy.backpressure())
		   .map(t->{
			   //System.out.println("file out: " + t.getFileName());
			   return TextMessage.create(t.toJson());
		   });	  
	 
	}


	
	/*
	 * Initialize array of files to create
	 * and start file creation
	 */
	public Iterable<Message> startFileCreationAsyncAndModifyOutput (Message msg) {
		
		ArrayList<Message> arrMessages = new ArrayList<Message>();
		if ((msg==null) ||  (!msg.isText())) {
			arrMessages.add(msg);
			return arrMessages;
		}
		
		initFilesQueueToClient();
		
		for (int i=0;i< Constants.FILES_COUNT_TOTAL;i++) {
			String fileName ="file" + i; 
			addFileToQueue(fileName);
			arrMessages.add(TextMessage.create(fileName));
		}
	
		String msgText = "";
	    if (msg.asTextMessage().isStrict()) // optimization that directly creates a simple response...
	    {
	    	 msgText = msg.asTextMessage().getStrictText();
	    } else { // ... this would suffice to handle all text messages in a streaming fashion
	    	Source<String,NotUsed> source = Source.single("").concat(msg.asTextMessage().getStreamedText()); 
	    	msgText = TextMessage.create(source).getStrictText();
		}
	
		MediaCreator mediaCreator = new MediaCreator(msgText, this,executionContext);
		mediaCreator.runAsync().thenApply(
				a->{
					System.out.println("File creation Completed ....");
					return a;
				}
		);
		
		return arrMessages;
	}
	  
	private void initFilesQueueToClient() {
		if (filesQueueToClient == null) {
			filesQueueToClient  = new  HashMap<String,QueueItem>();
		}else {
			for ( Map.Entry<String,QueueItem> item:filesQueueToClient.entrySet()) {
				if (item.getValue().getStreamCompleationStage()!=null) {
					item.getValue().getStreamCompleationStage().complete(Optional.empty());
				}
			}
			filesQueueToClient.clear();
		}
	}

	/*
	 * Initialize new item in files queue.
	 */
	public void addFileToQueue (String fileName) {
		QueueItem queueItem  = new QueueItem ();
		filesQueueToClient.put(fileName, queueItem);
	}
	
	/*
	 * Update queue by "File Creator" or by "Stream stage"
	 */
	public synchronized void updateFileInQueue (String fileName, CompletableFuture<Optional<FileData>> complStage ,FileData fileData) {
		
		QueueItem queueItem = filesQueueToClient.get(fileName);
		if (queueItem==null) {
			Logger.writeError("updateFileInQueue: no file to update: "+fileName , new Exception("updateFileInQueue: no file to update: " + fileName));
			return;
		}
		
		//if we came from stream
		if (fileData==null) {
			//if file is ready
			if (queueItem.getFile()!=null) {
				System.out.println("COMPLETE READY FILE:" +fileName);
				complStage.complete(Optional.of(queueItem.getFile()));
			}else{
				//System.out.println("File wait for complition:" +fileName);
				System.out.println("WAIT TO COMPLETE FILE:" +fileName);
				queueItem.setStreamCompleationStage(complStage);
			}
		}else { //if we came from File Creator
			//if complition stage was set
			if (queueItem.getStreamCompleationStage()!=null) {
				System.out.println("COMPLETE READY FILE AFTER CREATOR:" +fileName);
				queueItem.getStreamCompleationStage().complete(Optional.of(fileData));
				
			}else {
				System.out.println("WAIT TO COMPLETE FILE AFTER CREATOR:" +fileName);
				queueItem.setFile(fileData);
			}
		}
	}

    
	public String getUserName() {
			return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	@Override
	public void setFileStatus(String fileName, boolean succeded, FileData fileData, String error) {
		this.updateFileInQueue(fileName, null, fileData);
	}
	
}


	//	public  Flow<Message, Message, NotUsed> createGreeterOLD() {
//	     Flow<Message, Message, NotUsed> flow = 
//	    		  Flow.<Message>create()
//	    		  .mapConcat (m-> this.startFileCreationAsyncAndModifyOutput(m))
//	    		  .collect(new JavaPartialFunction<Message, Message>() {
//	    	          @Override
//	    	          public  Message apply(Message msg, boolean isCheck) throws Exception {
//	    	            if (isCheck) {
//	    	              if (msg.isText()) {
//	    	                return null;
//	    	              } else {
//	    	                throw noMatch();
//	    	              }
//	    	            } else {
//	    	              return handleTextMessage(msg.asTextMessage());
//	    	            }
//	    	          }
//	    	        });
//	     return flow;    
//	  }

/*
 * start media file creation process
 * Create Queue of Constants.FILES_COUNT_TOTAL files
 * in order to handle file creation callbacks
 */
/*
 * for each file request create Completion stage and wait
 * till file creation process calls back.
 */
//	public TextMessage handleTextMessage(TextMessage msg) {
//  	        Source<String, NotUsed> filesSource = 
//  			   Source.from(Arrays.asList(msg.getStrictText()))
//  			   .mapAsyncUnordered(10, (t) -> {
//  				   CompletableFuture<Optional<FileData>> complStage =  new CompletableFuture<Optional<FileData>> ();
//  				   //String filename = t.substring(2,7);
//  				   updateFileInQueue(t, complStage, null);
//  				   System.out.println("in mapAsyncUnordered: " +t);
//  				   return complStage;
//  			   })
//  			   .filter(o -> o.isPresent())
//  			   .map(o1->o1.get())
//  			   //.buffer(5, OverflowStrategy.backpressure())
//  			   .map(t->{
//  				   System.out.println("file out: " + t.getFileName());
//  				   return t.toJson();
//  			   });
//        return TextMessage.create(filesSource);
//    }




//TODO:
//json to Objects convertor
//Configuration application.conf (custome data)
//Logging
//Errors Handling - Try Cactch
//Error handling of akka stream
//Alex Integration
//Dima Integration
//
