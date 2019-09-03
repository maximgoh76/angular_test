package com.max.myserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import akka.NotUsed;
import akka.http.javadsl.ConnectHttp;
import akka.japi.Function;
import akka.japi.JavaPartialFunction;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.impl.fusing.Log;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.model.ws.WebSocket;

import com.max.myserver.bl.Logger;
import com.max.myserver.bl.SessionManager;

public class MediaServer {

 public static void main(String[] args) throws Exception {
	    ActorSystem system = ActorSystem.create();

	    try {
	      final Materializer materializer = ActorMaterializer.create(system);
	      final Function<HttpRequest, HttpResponse> handler = request -> handleRequest(request);
	      CompletionStage<ServerBinding> serverBindingFuture =
	        Http.get(system).bindAndHandleSync(
	          handler, ConnectHttp.toHost("localhost", 8080), materializer);
	      // will throw if binding fails
	      serverBindingFuture.toCompletableFuture().get(1, TimeUnit.SECONDS);
	      System.out.println("Press ENTER to stop.");
	      new BufferedReader(new InputStreamReader(System.in)).readLine();
	    } catch(Exception ex) {
	    	Logger.writeError("main error",ex);
	    }
	    finally {
	      system.terminate();
	    }
  }
	
  //#websocket-handling
  public static HttpResponse handleRequest(HttpRequest request) {
	  try {
		  System.out.println("Handling request to " + request.getUri());
		    if (request.getUri().path().equals("/mediaChat")) {
		    	SessionManager sessionManager = new SessionManager();
			    final Flow<Message, Message, NotUsed> greeterFlow = sessionManager.createGreeter();
			    return WebSocket.handleWebSocketRequestWith(request, greeterFlow);
		    } else {
		    	return HttpResponse.create().withStatus(404);
		    }
	  }catch (Exception e) {
		  Logger.writeError("main error",e);
		  return HttpResponse.create().withEntity(e.getMessage()).withStatus(500);
	  }
  }
 


 
  
  //#websocket-handler
}
  
