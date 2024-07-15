package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.Client;

public class LeastReviewedBenchmark {
	
	public static void main(String[] args) {

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractLeastReviewedAuthor();
			long stopTime = System.nanoTime();
			System.out.println("extractLeastReviewedAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractLeastReviewedAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("extractLeastReviewedAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}