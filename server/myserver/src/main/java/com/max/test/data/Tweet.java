package com.max.test.data;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Tweet {
	  public final Author author;
	  public final long timestamp;
	  public final String body;

	  public Tweet(Author author, long timestamp, String body) {
	    this.author = author;
	    this.timestamp = timestamp;
	    this.body = body;
	  }

	  public Set<String> hashtags() {
		  Set<String> s = Arrays.asList(body.split(" "))
			        .stream()
			        .filter(a -> a.startsWith("#"))
			        .map(a -> new String(a))
			        .collect(Collectors.toSet());
		  
	    return s;
	  }

	  // ...
	}
