package com.max.test;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.stream.actor.AbstractActorPublisher;
import akka.stream.actor.ActorPublisherMessage;
import akka.stream.javadsl.Source;

public  class AbstractActorPublisherExample extends AbstractActorPublisher<String> {

	  public static Props props() { return Props.create(AbstractActorPublisherExample.class); }

	  private final int MAX_BUFFER_SIZE = 100;
	  private final List<String> buf = new ArrayList<String>();

	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	      .match(String.class, job -> buf.size() == MAX_BUFFER_SIZE, job -> {
	        getSender().tell("Denied", getSelf());
	        onNext(job);
	      })
	      .match(String.class, job -> {
	        getSender().tell("Accepted", getSelf());
	        
	        if (buf.isEmpty() && totalDemand() > 0)
	          onNext(job);
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
	        final List<String> took =
	          buf.subList(0, Math.min(buf.size(), (int) totalDemand()));
	        took.forEach(this::onNext);
	        buf.removeAll(took);
	        break;
	      } else {
	        final List<String> took =
	          buf.subList(0, Math.min(buf.size(), Integer.MAX_VALUE));
	        took.forEach(this::onNext);
	        buf.removeAll(took);
	      }
	    }
	  }
	}
//{
//	
//	
//	
//	Thread t = new Thread() {
//		@Override
//		public void run() {
//			try {
//				Thread.sleep(1000);
//				ref.tell("{\"file7\":\"file7\"}", ActorRef.noSender());
//				ref.tell("{\"file8\":\"file8\"}", ActorRef.noSender());
//				ref.tell("{\"file9\":\"file9\"}", ActorRef.noSender());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		}
//	};
//	t.start();	
//
//	
//	 Source<String, ActorRef> tweets =
//			  Source.actorPublisher(JobManager.props());
//
//}