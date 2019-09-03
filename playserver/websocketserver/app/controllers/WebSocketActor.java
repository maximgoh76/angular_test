package controllers;

import java.time.LocalDateTime;

import com.google.inject.Inject;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class WebSocketActor extends AbstractActor {

    private final ActorRef out;

    @Inject
    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message ->
                        out.tell("Sending message at " + LocalDateTime.now().toString(), self())
                )
                .build();
    }

//    public static Props props(final ActorRef out) {
//    	 return Props.create(WebSocketActor.class);
//    	 // return Props.create(WebSocketActor.class, out);
//    }
}