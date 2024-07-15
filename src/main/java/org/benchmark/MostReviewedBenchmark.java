package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.Client;

public class MostReviewedBenchmark {
	
	public static void main(String[] args) {

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractMostReviewedAuthor();
			long stopTime = System.nanoTime();
			System.out.println("extractMostReviewedAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			Client.extractMostReviewedAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("extractMostReviewedAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}