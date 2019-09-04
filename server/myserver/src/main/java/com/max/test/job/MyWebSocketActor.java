package com.max.test.job;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.http.javadsl.model.ws.Message;

public class MyWebSocketActor extends AbstractActor {

	  public static Props props(ActorRef out) {
	    return Props.create(MyWebSocketActor.class, out);
	  }

	  private final ActorRef out;

	  public MyWebSocketActor(ActorRef out) {
	    this.out = out;
	  }

	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	        .match(String.class, message -> out.tell("I String  your message: " + message, self()))
	        .match(Message.class, message -> out.tell("I Message your message: " + message, self()))
	        .build();
	  }
	}