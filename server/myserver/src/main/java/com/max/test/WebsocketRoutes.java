package com.max.test;

import java.util.concurrent.TimeUnit;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.japi.JavaPartialFunction;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.duration.FiniteDuration;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.model.ws.WebSocket;

public class WebsocketRoutes extends AllDirectives {

		private final ActorSystem actorSystem;
		private final ActorRef connectionManager;

		public WebsocketRoutes(final ActorSystem actorSystem, final ActorRef connectionManager) {
		    this.actorSystem = actorSystem;
		    this.connectionManager = connectionManager;
		}

//		public Route handleWebsocket() {
//		    return path(PathMatchers.segment(compile("router_v\\d+")).slash(PathMatchers.segment("websocket")).slash(PathMatchers.segment(compile("[^\\\\/\\s]+"))), (version, routerId) ->
//		            handleWebSocketMessages(createWebsocketFlow(routerId))
//		    );
//		}
//
//		private Flow<Message, Message, NotUsed> createWebsocketFlow(final String routerId) {
//
//		    final ActorRef connection = actorSystem.actorOf(WebsocketConnectionActor.props(connectionManager));
//
//		    final Source<Message, NotUsed> source = Source.<RouterWireMessage.Outbound>actorRef(5, OverflowStrategy.fail())
//		            .map((outbound) -> (Message) TextMessage.create(new String(outbound.message, "utf-8")))
//		            .throttle(5, FiniteDuration.create(1, TimeUnit.SECONDS), 10, ThrottleMode.shaping())
//		            .mapMaterializedValue(destinationRef -> {
//		                connection.tell(new RouterConnected(routerId, destinationRef), ActorRef.noSender());
//		                return NotUsed.getInstance();
//		            });
//
//		    final Sink<Message, NotUsed> sink = Flow.<Message>create()
//		            .map((inbound) -> new RouterWireMessage.Inbound(inbound.asTextMessage().getStrictText().getBytes()))
//		            .throttle(5, FiniteDuration.create(1, TimeUnit.SECONDS), 10, ThrottleMode.shaping())
//		            .to(Sink.actorRef(connection, PoisonPill.getInstance()));
//
//		    return Flow.fromSinkAndSource(sink, source);
//		}
		}