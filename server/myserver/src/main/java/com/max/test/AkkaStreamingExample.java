package com.max.test;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Pair;
import akka.stream.ActorMaterializer;
import akka.stream.ClosedShape;
import akka.stream.FlowShape;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.SinkShape;
import akka.stream.UniformFanOutShape;
import akka.stream.impl.Timers.Completion;
import akka.stream.javadsl.Broadcast;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.GraphDSL;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import akka.Done;
import akka.util.ByteString;
import java.nio.file.Paths;
import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import com.max.test.data.Author;
import com.max.test.data.Hashtag;
import com.max.test.data.Tweet;

//import jdocs.AbstractJavaTest;

public class AkkaStreamingExample {
	 public static void main(String[] args) {
		 final ActorSystem system = ActorSystem.create("QuickStart");
		//Materializer is a factory for stream execution engines, it is the thing that makes streams run—you don’t need to worry about any of the details right now apart from that you need one for calling any of the run methods on a Source.
		 final Materializer materializer = ActorMaterializer.create(system);
		 
		
		 //example1(materializer,system);
		 //example2(materializer,system);
		 //example3(materializer,system);
		 //example4(materializer,system);
		 //example5(materializer,system);
		 //example7(materializer,system);
		 //exampleGraph1(materializer,system);
		 //exampleGraph2(materializer,system);
		 example10(materializer,system);
	 }
	 
	 
	 public static void example2(Materializer materializer,ActorSystem system) {
		 
		 final Source<Integer, NotUsed> source = Source.range(1, 100);
		 final Source<BigInteger, NotUsed> factorials =
				    source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

		 final CompletionStage<IOResult> result =
		    factorials
		        .map(num -> ByteString.fromString(num.toString() + "\n"))
		        .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer);

		 result.thenAccept((a)-> {
			 System.out.println("Count=" + a.count());
			 system.terminate();
		 });
		 
		 //result.thenRun(() -> system.terminate());
	 }

	 public static void example1(Materializer materializer,ActorSystem system) {
		 //1
		 final Source<Integer, NotUsed> source = Source.range(1, 100);
		 //source.runForeach(i -> System.out.println(i), materializer);
		 
		 final CompletionStage<Done> done = source.runForeach(i -> System.out.println(i), materializer);

		 done.thenRun(() -> system.terminate());
	 }
	 
	 public static void example3(Materializer materializer,ActorSystem system) {
		 
		 final Source<Integer, NotUsed> source = Source.range(1, 100);
		 final Source<BigInteger, NotUsed> factorials =
				    source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

		 //final CompletionStage<IOResult> result =
		 //   factorials
		 //       .map(num -> ByteString.fromString(num.toString() + "AAAA\n"))
		 //       .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer);

		 final CompletionStage<IOResult> result = 
				 factorials.map(BigInteger::toString).runWith(lineSink("factorial2.txt"), materializer);

		 
		 result.thenAccept((a)-> {
			 System.out.println("Count=" + a.count());
			 system.terminate();
		 });

		 
		
	 }
	 
		public static Sink<String, CompletionStage<IOResult>> lineSink(String filename) {
			  return Flow.of(String.class)
			      .map(s -> ByteString.fromString(s.toString() + "\n"))
			      .toMat(FileIO.toPath(Paths.get(filename)), Keep.right());
		}
		
		
		 public static void example4(Materializer materializer,ActorSystem system) {
			 
			 final Source<Integer, NotUsed> source = Source.range(1, 100);
			 final Source<BigInteger, NotUsed> factorials =
					    source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

			 //final CompletionStage<IOResult> result =
			 //   factorials
			 //       .map(num -> ByteString.fromString(num.toString() + "AAAA\n"))
			 //       .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer);

			 final CompletionStage<Done> result = factorials
			    .zipWith(Source.range(5, 20), (num, idx) -> String.format("%d! = %s", idx, num))
			    .throttle(1, Duration.ofSeconds(1))
			    .runForeach(s -> System.out.println(s), materializer);
			 
			 result.thenRun(()-> {
				 System.out.println("Count=");
				 system.terminate();
			 });

			 
			
		 }
		
	 public static void example5(Materializer materializer,ActorSystem system) {
			 
		   final Hashtag AKKA = new Hashtag("#akka");
		   
		   Tweet t1 = new Tweet(new Author("Max"), 1212, "fds #akka sfsd fds");
		   Tweet t2 = new Tweet(new Author("Dan"), 2342, "fds #skka sfsd fds");
		   Tweet t3 = new Tweet(new Author("Max"), 234234, "fds #akka  fds");

				   
		   //Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		   Source<Tweet, NotUsed> tweets = Source.from(Arrays.asList(t1,t2,t3));
		   
		   final Source<Author, NotUsed> authors =
				    tweets.filter(t -> {
				    	boolean a= t.hashtags().contains("#akka");
				    	return a;
				    }).map(t -> t.author);
				    
		   
		   final CompletionStage<Done> result = authors.runForeach(a -> System.out.println(a.handle), materializer);
			 result.thenRun(()-> {
				 System.out.println("DONE");
				 system.terminate();
			 });
	 }
	 public static void example6(Materializer materializer,ActorSystem system) {
		 
	   Tweet t1 = new Tweet(new Author("Max"), 1212, "fds #akka sfsd fds");
	   Tweet t2 = new Tweet(new Author("Dan"), 2342, "fds #skka sfsd fds");
	   Tweet t3 = new Tweet(new Author("Max"), 234234, "fds #akka  fds");

			   
	   //Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	   Source<Tweet, NotUsed> tweets = Source.from(Arrays.asList(t1,t2,t3));

		 Sink<Author, NotUsed> writeAuthors =  null;
		 Sink<String, NotUsed> writeHashtags = null;
		 RunnableGraph.fromGraph(
		         GraphDSL.create(
		             b -> {
		               final UniformFanOutShape<Tweet, Tweet> bcast = b.add(Broadcast.create(2));
		               final FlowShape<Tweet, Author> toAuthor =
		                   b.add(Flow.of(Tweet.class).map(t -> t.author));
		               final FlowShape<Tweet, String> toTags =
		                   b.add(
		                       Flow.of(Tweet.class)
		                           .mapConcat(t -> new ArrayList<String>(t.hashtags())));
		               final SinkShape<Author> authors = b.add(writeAuthors);
		               final SinkShape<String> hashtags = b.add(writeHashtags);

		               b.from(b.add(tweets)).viaFanOut(bcast).via(toAuthor).to(authors);
		               b.from(bcast).via(toTags).to(hashtags);
		               return ClosedShape.getInstance();
		             }))
		     .run(materializer);
	 }
	 
	 public static void example7(Materializer materializer,ActorSystem system) {
		 Tweet t1 = new Tweet(new Author("Max"), 1212, "fds #akka sfsd fds");
		 Tweet t2 = new Tweet(new Author("Dan"), 2342, "fds #skka sfsd fds");
		 Tweet t3 = new Tweet(new Author("Max"), 234234, "fds #akka  fds");

				   
		  //Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		 Source<Tweet, NotUsed> tweets = Source.from(Arrays.asList(t1,t2,t3));
		 
		 final Sink<Integer, CompletionStage<Integer>> sumSink =
				    Sink.<Integer, Integer>fold(0, (acc, elem) -> acc + elem);
		 
		 final RunnableGraph<CompletionStage<Integer>> counter =
				    tweets.map(t -> 1).toMat(sumSink, Keep.right());

		final CompletionStage<Integer> sum = counter.run(materializer);

		sum.thenAcceptAsync(
				    c -> System.out.println("Total tweets processed: " + c), system.dispatcher());
	
	 }
	 public static void exampleGraph1(Materializer materializer,ActorSystem system) {
		final Source<Integer, NotUsed> source =
				    Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		// note that the Future is scala.concurrent.Future
		final Sink<Integer, CompletionStage<Integer>> sink =
		    Sink.<Integer, Integer>fold(0, (aggr, next) -> {
		    	System.out.println(aggr + next);
		    	return (aggr + next);
		    });

		// connect the Source to the Sink, obtaining a RunnableFlow
		final RunnableGraph<CompletionStage<Integer>> runnable = source.toMat(sink, Keep.right());

		// materialize the flow
		final CompletionStage<Integer> sum = runnable.run(materializer);
		
		sum.thenRun(()-> {
			 System.out.println("DONE");
			 system.terminate();
		 });
	 }
	 public static void exampleGraph2(Materializer materializer,ActorSystem system) {
		 Source<String, ActorRef> matValuePoweredSource = Source.actorRef(100, OverflowStrategy.fail());

		 Pair<ActorRef, Source<String, NotUsed>> actorRefSourcePair =
		     matValuePoweredSource.preMaterialize(materializer);

		 actorRefSourcePair.first().tell("Hello!", ActorRef.noSender());

		 // pass source around for materialization
		 final CompletionStage<Done> comp= actorRefSourcePair.second().runWith(Sink.foreach(System.out::println), materializer);
		 
		 actorRefSourcePair.first().tell("MAX!", ActorRef.noSender());
		 comp.thenRun(()-> {
			 System.out.println("DONE");
			 system.terminate();
		 });
		 
		 comp.thenAccept(a->System.out.println("DONE: " + a) );
	 }
	
	 
	 
	 
	 private static Flow<Integer, Double, NotUsed> computeAverage() {
	        return Flow.of(Integer.class).grouped(2).mapAsyncUnordered(8, integers ->
	                CompletableFuture.supplyAsync(() -> integers
	                        .stream()
	                        .mapToDouble(v -> v)
	                        .average()
	                        .orElse(-1.0)));
	   }
	 
	 private static CompletionStage<Tweet> getTweet(Tweet t){
		
		  CompletableFuture<Tweet> a =  new CompletableFuture<Tweet>();
		  System.out.println("HI:" + t.body);
		  a.complete((t)); //Optional.of
		  return a;
	 }
	 
	 public static void example10(Materializer materializer,ActorSystem system) {
		 
		   final Hashtag AKKA = new Hashtag("#akka");
		   
		   Tweet t1 = new Tweet(new Author("Max"), 1212, "fds #akka sfsd fds");
		   Tweet t2 = new Tweet(new Author("Dan"), 2342, "fds #skka sfsd MAX");
		   Tweet t3 = new Tweet(new Author("Max"), 234234, "MAX #akka  fds");

		  // CompletionStage<Optional<Tweet>> a = new 
				   
		   //Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		   Source<Tweet, NotUsed> tweets = 
				   Source.from(Arrays.asList(t1,t2,t3))
				   .mapAsyncUnordered(5, (t) -> {
					   CompletableFuture<Tweet> a =  new CompletableFuture<Tweet>();
						  a.complete((t)); //Optional.of
						  return a;
				   });
				     
		   final CompletionStage<Done> result = tweets.runForeach(a -> System.out.println(a.body), materializer);
			 result.thenRun(()-> {
				 System.out.println("DONE");
				 system.terminate();
			 });
	 }
}
