package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.MainClass;

public class AverageReviewedBenchmark {
	
	public static void main(String[] args) {
		
		for(int i=0; i<5; i++) {
			System.out.println("Warming up..");
			MainClass.extractAverageReviewedAuthor();
		}
		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.extractAverageReviewedAuthor();
			long stopTime = System.nanoTime();
			System.out.println("extractAverageReviewedAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();
		for(int i=0; i<5; i++) {
			System.out.println("Warming up..");
			MainClass.extractAverageReviewedAuthorParallel();
		}
		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.extractAverageReviewedAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("extractAverageReviewedAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}
