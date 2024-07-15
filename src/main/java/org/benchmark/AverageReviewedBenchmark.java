package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.Client;

public class AverageReviewedBenchmark {
	
	public static void main(String[] args) {

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractAverageReviewedAuthor();
			long stopTime = System.nanoTime();
			System.out.println("extractAverageReviewedAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractAverageReviewedAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("extractAverageReviewedAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}
