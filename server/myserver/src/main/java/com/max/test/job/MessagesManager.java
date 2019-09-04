package com.max.test.job;

import java.util.ArrayList;
import java.util.List;

import akka.actor.Props;
import akka.http.javadsl.model.ws.Message;
import akka.stream.actor.AbstractActorPublisher;
import akka.stream.actor.ActorPublisherMessage;

@SuppressWarnings("deprecation")
public class MessagesManager extends AbstractActorPublisher<Message> {

	  public static Props props() {
	    return Props.create(MessagesManager.class);
	  }

	  private final int MAX_BUFFER_SIZE = 100;
	  private final List<Message> buf = new ArrayList<>();

	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	        .match(
	        	Message.class,
	            job -> buf.size() == MAX_BUFFER_SIZE,
	            job -> {
	              getSender().tell("Denied", getSelf());
	            })
	        .match(
	        	Message.class,
	            job -> {
	              getSender().tell("Accepted", getSelf());

	              if (buf.isEmpty() && totalDemand() > 0) onNext(job);
	              else {
	                buf.add(job);
	                deliverBuf();
	              }
	            })
	        .match(ActorPublisherMessage.Request.class, request -> deliverBuf())
	        .match(ActorPublisherMessage.Cancel.class, cancel -> getContext().stop(getSelf()))
	        .build();
	  }
	  
	  void deliverBuf() {
	    while (totalDemand() > 0) {
	      /*
	       * totalDemand is a Long and could be larger than
	       * what buf.splitAt can accept
	       */
	      if (totalDemand() <= Integer.MAX_VALUE) {
	        final List<Message> took =
	            buf.subList(0, Math.min(buf.size(), (int) totalDemand()));
	        took.forEach(this::onNext);
	        buf.removeAll(took);
	        break;
	      } else {
	        final List<Message> took =
	            buf.subList(0, Math.min(buf.size(), Integer.MAX_VALUE));
	        took.forEach(this::onNext);
	        buf.removeAll(took);
	      }
	    }
	 }
}