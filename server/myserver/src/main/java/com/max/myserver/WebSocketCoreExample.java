package com.max.myserver;

/*
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */

//#websocket-example-using-core

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.max.myserver.job.MessagesManager;
import com.max.myserver.job.MyWebSocketActor;

import akka.NotUsed;
import akka.http.impl.util.JavaMapping;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.ConnectionContext;
import akka.http.javadsl.model.ws.WebSocketRequest;
import akka.http.javadsl.settings.ClientConnectionSettings;
import akka.http.javadsl.settings.ServerSettings;
import akka.http.javadsl.settings.WebSocketSettings;
import akka.http.scaladsl.model.ws.TextMessage.Strict;
import akka.japi.Function;
import akka.japi.JavaPartialFunction;

import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.model.ws.UpgradeToWebSocket;
import akka.http.javadsl.model.ws.WebSocket;
import akka.util.ByteString;

@SuppressWarnings({"Convert2MethodRef", "ConstantConditions"})
public class WebSocketCoreExample {

	static Flow<Message, Message, NotUsed> greeterFlowGen =null;
  //#websocket-handling
  public static HttpResponse handleRequest(HttpRequest request) {
    System.out.println("Handling request to " + request.getUri());

    if (request.getUri().path().equals("/mediaChat")) {
      final Flow<Message, Message, NotUsed> greeterFlow = greeter();
      greeterFlowGen = greeterFlow;
      //greeterFlow.sou
     
      return  WebSocket.handleWebSocketRequestWith(request, greeterFlow);
    } else {
      return HttpResponse.create().withStatus(404);
    }
  }
private static AtomicInteger connections = new AtomicInteger(0);//connected clients count.

  static ActorSystem system = null;
  static Materializer materializer = null;
  final ActorRef ref = null;
  
  public static void main(String[] args) throws Exception {
    system = ActorSystem.create();

    try {
    	Thread one = new Thread() {
    	    public void run() {
    	        try {
    	        	while (true) {
	    	            System.out.println("Send message");
	    	            //if (source!=null)
	    	            //	source.intersperse("{\"Hello\":\"HELLO\"}");
	    	            
	    	            if (greeterFlowGen!=null) {
	    	            	//Source<Double, NotUsed> flow = Source.single(input).via(tested);
	    	            	ArrayList<Message> a = new ArrayList<Message>();
	    	            	a.add(TextMessage.create("{\"a\":\"aaa\"}"));
	    	            	a.add(TextMessage.create("{\"b\":\"bbb\"}"));
	    	            	Source<Message, NotUsed> out  = Source.from(a)
	    	           			  .map((i)->{
	    	           				  return (Message)i;
	    	           				  }).concat(Source.maybe());
	    	            	
	    	            	greeterFlowGen
		    	              .runWith(out, Sink.ignore(), materializer);
	    	            }
	    	      
	    	          
	    	            
	    	            Thread.sleep(1000);
	    	        }
    	        } catch(InterruptedException v) {
    	            System.out.println(v);
    	        }
    	    }  
    	};
    	//one.start();
    	
      materializer = ActorMaterializer.create(system);

      final Function<HttpRequest, HttpResponse> handler = request -> handleRequest(request);
      CompletionStage<ServerBinding> serverBindingFuture =
        Http.get(system).bindAndHandleSync(
          handler, ConnectHttp.toHost("localhost", 8080), materializer);

      // will throw if binding fails
      serverBindingFuture.toCompletableFuture().get(1, TimeUnit.SECONDS);
      System.out.println("Press ENTER to stop.");
      new BufferedReader(new InputStreamReader(System.in)).readLine();
    } finally {
      system.terminate();
    }
    
    
    
  }

  //#websocket-handler

  /**
   * A handler that treats incoming messages as a name,
   * and responds with a greeting to that name
   */
//  public static Flow<Message, Message, NotUsed> greeter() {
//    return
//      Flow.<Message>create()
//        .collect( new JavaPartialFunction<Message, Message>() {
//          @Override
//          public Message apply(Message msg, boolean isCheck) throws Exception {
//            if (isCheck) {
//              if (msg.isText()) {
//                return null;
//              } else {
//                throw noMatch();
//              }
//            } else {
//              return handleTextMessage(msg.asTextMessage());
//            }
//          }
//          
//        });
//  }
  
  
//  private Flow<Message, Message, NotUsed> greeter(final String routerId) {
//
//	    final ActorRef connection = system.actorOf(WebsocketConnectionActor.props(connectionManager));
//
//	    final Source<Message, NotUsed> source = Source.<RouterWireMessage.Outbound>actorRef(5, OverflowStrategy.fail())
//	            .map((outbound) -> (Message) TextMessage.create(new String(outbound.message, "utf-8")))
//	            .throttle(5, FiniteDuration.create(1, TimeUnit.SECONDS), 10, ThrottleMode.shaping())
//	            .mapMaterializedValue(destinationRef -> {
//	                connection.tell(new RouterConnected(routerId, destinationRef), ActorRef.noSender());
//	                return NotUsed.getInstance();
//	            });
//
//	    final Sink<Message, NotUsed> sink = Flow.<Message>create()
//	            .map((inbound) -> new RouterWireMessage.Inbound(inbound.asTextMessage().getStrictText().getBytes()))
//	            .throttle(5, FiniteDuration.create(1, TimeUnit.SECONDS), 10, ThrottleMode.shaping())
//	            .to(Sink.actorRef(connection, PoisonPill.getInstance()));
//
//	    return Flow.fromSinkAndSource(sink, source);
//	}
  
  
  
  
  
//  
//  public static HttpResponse handleRequest(HttpRequest request) {
//	  HttpResponse result;
//	  if (request.getUri().path().equals("/mediaChat")) {
//	    final Flow<Message, Message, NotUsed> greeterFlow = greeter().watchTermination((nu, cd) -> {
//	      connections.incrementAndGet();
//	      cd.whenComplete((done, throwable) -> connections.decrementAndGet());
//	      return nu;
//	    });
//	    
//	   
//	    result = WebSocket.handleWebSocketRequestWith(request, greeterFlow);
//	    
//	    
//	  } else {
//	    result = HttpResponse.create().withStatus(413);
//	  }
//	  return result;
//	}
  //#websocket-handling
  
  
  public static Flow<Message, Message, NotUsed> greeter() {
	  // Log events to the console
	  Sink<Message, ?> in = Sink.foreach(
			  (i) ->{
				   System.out.println(i.asTextMessage().getStrictText());
				   if (greeterFlowGen!=null) {
	   	            	//Source<Double, NotUsed> flow = Source.single(input).via(tested);
	   	            	ArrayList<Message> a = new ArrayList<Message>();
	   	            	a.add(TextMessage.create("{\"a\":\"aaa\"}"));
	   	            	a.add(TextMessage.create("{\"b\":\"bbb\"}"));
	   	            	Source<Message, NotUsed> out  = Source.from(a)
	   	           			  .map((j)->{
	   	           				  return (Message)j;
	   	           				  }).concat(Source.maybe());
	   	            	
	   	            	greeterFlowGen
		    	              .runWith(out, Sink.ignore(), materializer);
	   	            }
					   
				   
				  }
			  )
		;
	
	  // Send a single 'Hello!' message and then leave the socket open
	  //Source<Message, NotUsed> out  = Source.fromFuture(Futures.successful(TextMessage.create("{\"a\":\"aaa\"}")))
	  //	  .map((i)->{
	  //		  return (Message)i;
	  //		  }).concat(Source.maybe());//Source.actorPublisher(MyWebSocketActor.props(self()));//(TextMessage.create("{\"a\":\"aaa\"}")); // Source.single("Hello!").concat(Source.maybe());
	  
		ArrayList<Message> a = new ArrayList<Message>();
    	a.add(TextMessage.create("{\"a\":\"aaa\"}"));
    	a.add(TextMessage.create("{\"b\":\"bbb\"}"));
    	Source<Message, NotUsed> out  = Source.from(a)
   			  .map((i)->{
   				  return (Message)i;
   				  }).concat(Source.maybe());
    	
	  return Flow.fromSinkAndSource(in, out);
  }
  
//  public static Flow<Message, Message, NotUsed> greeter() {
//	  
//	  Source<Message, ActorRef> jobManagerSource =
//			    Source.actorPublisher(MessagesManager.props());
//
//			final ActorRef ref =
//					jobManagerSource
//			        .map(
//			            elem -> {
//			              System.out.println(elem);
//			              return elem;
//			            })
//			        .to(Sink.ignore())
//			        .run(materializer);
//
//			ref.tell("asdasdasd", ActorRef.noSender());
//			ref.tell("message2", ActorRef.noSender());
//			ref.tell("message3", ActorRef.noSender());
//	  
//	  return Flow.fromSinkAndSource(Sink.ignore(),
//			  jobManagerSource)  ;
//	 
//	  ////Source.single(new akka.http.scaladsl.model.ws.TextMessage.Strict("{\"a\":\"a\"}"))
//	}
//  
  
  
  public static Source<String,NotUsed> source = null; 
  public static TextMessage handleTextMessage(TextMessage msg) {
    if (msg.isStrict()) // optimization that directly creates a simple response...
    {
    	//source =  Source.single("Hello ").concat(msg.getStreamedText()).concat(Source.maybe()).runForeach( i -> i, materializer);
    	 //return TextMessage.create(source);
    	TextMessage t = TextMessage.create(msg.getStrictText());
        return t;
    } else // ... this would suffice to handle all text messages in a streaming fashion
    {
      source = Source.single("Hello ").concat(msg.getStreamedText());
      
      return TextMessage.create(source);
    }
  }
  
  
  
  //#websocket-handler

  {
    ActorSystem system = null;
    ActorMaterializer materializer = null;
    Flow<HttpRequest, HttpResponse, NotUsed> handler = null;
    //#websocket-ping-payload-server
    ServerSettings defaultSettings = ServerSettings.create(system);

    AtomicInteger pingCounter = new AtomicInteger();

    WebSocketSettings customWebsocketSettings = defaultSettings.getWebsocketSettings()
        .withPeriodicKeepAliveData(() ->
            ByteString.fromString(String.format("debug-%d", pingCounter.incrementAndGet()))
        );


    ServerSettings customServerSettings = defaultSettings.withWebsocketSettings(customWebsocketSettings);

    Http http = Http.get(system);
    http.bindAndHandle(handler,
        ConnectHttp.toHost("127.0.0.1"),
        customServerSettings, // pass the configuration
        system.log(),
        materializer);
    //#websocket-ping-payload-server
  }

  {
    ActorSystem system = null;
    ActorMaterializer materializer = null;
    Flow<Message, Message, NotUsed> clientFlow = null;
    //#websocket-client-ping-payload
    ClientConnectionSettings defaultSettings = ClientConnectionSettings.create(system);

    AtomicInteger pingCounter = new AtomicInteger();

    WebSocketSettings customWebsocketSettings = defaultSettings.getWebsocketSettings()
        .withPeriodicKeepAliveData(() ->
            ByteString.fromString(String.format("debug-%d", pingCounter.incrementAndGet()))
        );

    ClientConnectionSettings customSettings =
        defaultSettings.withWebsocketSettings(customWebsocketSettings);

    Http http = Http.get(system);
    http.singleWebSocketRequest(
        WebSocketRequest.create("ws://127.0.0.1"),
        clientFlow,
        ConnectionContext.noEncryption(),
        Optional.empty(),
        customSettings,
        system.log(),
        materializer
    );
    //#websocket-client-ping-payload
  }
}
//#websocket-example-using-core