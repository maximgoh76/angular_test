package com.max.myserver.bl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import akka.NotUsed;
import akka.dispatch.MessageDispatcher;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.japi.JavaPartialFunction;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import java.util.Optional;


public class SessionManager implements IFileReadyCallBack {
	private String userName;
	private String sessionID;
	Materializer materializer = null;
	MessageDispatcher executionContext = null;
	public SessionManager (Materializer materializer,MessageDispatcher executionContext) {
		this.materializer = materializer;
		this.executionContext = executionContext;
	}
	
	public  Flow<Message, Message, NotUsed> createGreeter() {
	     Flow<Message, Message, NotUsed> flow = 
	    		  Flow.<Message>create()
	    		  .mapConcat (m-> this.startFileCreationAsyncAndModifyOutput(m))
	    	      .collect(new JavaPartialFunction<Message, Message>() {
	    	          @Override
	    	          public  Message apply(Message msg, boolean isCheck) throws Exception {
	    	            if (isCheck) {
	    	              if (msg.isText()) {
	    	                return null;
	    	              } else {
	    	                throw noMatch();
	    	              }
	    	            } else {
	    	              return handleTextMessage(msg.asTextMessage());
	    	            }
	    	          }
	    	        });
	     return flow;    
	  }
	
	 
	public Iterable<Message> startFileCreationAsyncAndModifyOutput (Message msg) {
		
		System.out.println("File Change stream to 5 files");
		ArrayList<Message> arr = new ArrayList<Message>();
		arr.add(TextMessage.create("{\"file1\":\"path\"}"));
		arr.add(TextMessage.create("{\"file2\":\"path\"}"));
		arr.add(TextMessage.create("{\"file3\":\"path\"}"));
		arr.add(TextMessage.create("{\"file4\":\"path\"}"));
		arr.add(TextMessage.create("{\"file5\":\"path\"}"));
		
		addFile("file1", "{\"file1\":\"path\"}");
		addFile("file2", "{\"file2\":\"path\"}");
		addFile("file3", "{\"file3\":\"path\"}");
		addFile("file4", "{\"file4\":\"path\"}");
		addFile("file5", "{\"file5\":\"path\"}");
		
		MediaCreator mediaCreator = new MediaCreator(msg.asTextMessage().getStrictText(), this,executionContext);
		mediaCreator.runAsync().thenApply(
				a->{
					System.out.println("File creation started ....");
					return a;
				}
		);
		
		return arr;
	}
	  
	public TextMessage handleTextMessage(TextMessage msg) {
			//	      if (msg.isStrict()) // optimization that directly creates a simple response...
			//	      {
			//	        return TextMessage.create("Hello " + msg.getStrictText());
			//	      } else // ... this would suffice to handle all text messages in a streaming fashion
			     
     
  	        Source<String, NotUsed> tweets = 
  			   Source.from(Arrays.asList(msg.getStrictText()))
  			   //.buffer(5, OverflowStrategy.backpressure())
  			   .mapAsyncUnordered(10, (t) -> {
  				   CompletableFuture<Optional<String>> complStage =  new CompletableFuture<Optional<String>> ();
  				   String filename = t.substring(2,7);
  				   addFile(filename, complStage, null);
  				   System.out.println("in mapAsyncUnordered: " +filename);
  				   return complStage;
  			   })
  			   .filter(o -> o.isPresent())
  			   .map(o1->o1.get())
  			   //.buffer(5, OverflowStrategy.backpressure())
  			   .map(t->{
  				   String filename = t.substring(2,7);
  				   System.out.println("in map: " +filename);
  				   return t;
  			   });
  	  
        return TextMessage.create(tweets);
    }
	
	
	/*
	 * Init
	 */
	public void addFile (String fileName,String msgToClient ) {
		filePaths.put(fileName, null);
		fileComplitionStages.put(fileName, null);
		fileMessagesToClient.put(fileName,msgToClient);
	}
	
	
	public synchronized void addFile (String fileName, CompletableFuture<Optional<String>> complStage ,String filePath) {
		//if we came from stream
		if (filePath==null) {
			if (filePaths.get(fileName)!=null) {
				String msg = fileMessagesToClient.get(fileName);
				msg = msg.replace("path", filePaths.get(fileName));
				System.out.println("COMPLETE READY FILE:" +fileName);
				complStage.complete(Optional.of(msg));
			}else{
				//System.out.println("File wait for complition:" +fileName);
				System.out.println("WAIT TO COMPLETE FILE:" +fileName);
				fileComplitionStages.put(fileName, complStage);
			}
		}else { //if we came from File Creator
			if (fileComplitionStages.get(fileName)!=null) {
				String msg = fileMessagesToClient.get(fileName);
				msg = msg.replace("path", filePath);
				System.out.println("COMPLETE READY FILE AFTER CREATOR:" +fileName);
				fileComplitionStages.get(fileName).complete(Optional.of(msg));
				
			}else {
				System.out.println("WAIT TO COMPLETE FILE AFTER CREATOR:" +fileName);
				filePaths.put (fileName,filePath);
			}
		}
	}

    HashMap<String,String> filePaths = new  HashMap<String,String>();
    HashMap<String,CompletableFuture<Optional<String>>> fileComplitionStages = new   HashMap<String,CompletableFuture<Optional<String>>>();  
    HashMap<String,String> fileMessagesToClient = new  HashMap<String,String>();

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
	public void setFileStatus(String fileName, boolean succeded, String filePath, String error) {
		 addFile(fileName, null, filePath);
	}
	
}


//TODO:
//json to Objects convertor
//Configuration application.conf (custome data)
//Logging
//Errors Handling - Try Cactch
//Alex Integration
//Dima Integration
//
