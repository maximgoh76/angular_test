package com.max.myserver.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import com.max.myserver.MediaServer;

import akka.NotUsed;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.japi.JavaPartialFunction;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;

public class SessionManager implements IFileReadyCallBack {
	private String userName;
	private String sessionID;
	
	public  Flow<Message, Message, NotUsed> createGreeter() {
		
	    return Flow.<Message>create()
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
	    	    
	  }
	
	 //#websocket-handling
	public Iterable<Message> startFileCreationAsyncAndModifyOutput (Message msg) {
		
		MediaCreator mediaCreator = new MediaCreator(msg.asTextMessage().getStrictText(), this);
		mediaCreator.runAsync().thenApply(
				a->{
					System.out.println("File creation started ....");
					return a;
				}
		);
		
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
		
		return arr;
	}
	  
	public TextMessage handleTextMessage(TextMessage msg) {
//	      if (msg.isStrict()) // optimization that directly creates a simple response...
//	      {
//	        return TextMessage.create("Hello " + msg.getStrictText());
//	      } else // ... this would suffice to handle all text messages in a streaming fashion
      
     
  	  Source<String, NotUsed> tweets = 
  			   Source.from(Arrays.asList(msg.getStrictText()))
  			   .mapAsyncUnordered(5, (t) -> {
  				   CompletableFuture<String> complStage =  new CompletableFuture<String>();
  				   String filename = t.substring(2,7);
  				   addFile(filename, complStage, null);
  				   System.out.println("wait for file: " +filename);
  				   return complStage;
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
	
	
	public synchronized void addFile (String fileName, CompletableFuture<String> complStage ,String filePath) {
		//if we came from stream
		if (filePath==null) {
			if (filePaths.get(fileName)!=null) {
				String msg = fileMessagesToClient.get(fileName);
				msg = msg.replace("path", filePaths.get(fileName));
				System.out.println("File (completed early):" +fileName);
				complStage.complete(msg);
			}else{
				System.out.println("File wait:" +fileName);
				fileComplitionStages.put(fileName, complStage);
			}
		}else { //if we came from File Creator
			if (fileComplitionStages.get(fileName)!=null) {
				String msg = fileMessagesToClient.get(fileName);
				msg = msg.replace("path", filePath);
				System.out.println("File (completed):" +fileName);
				fileComplitionStages.get(fileName).complete(msg);
				
			}else {
				System.out.println("File  ready (too early):" +fileName);
				filePaths.put (fileName,filePath);
			}
		}
	}
	
	
    HashMap<String,String> filePaths = new  HashMap<String,String>();
    HashMap<String,CompletableFuture<String>> fileComplitionStages = new   HashMap<String,CompletableFuture<String>>();  
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
