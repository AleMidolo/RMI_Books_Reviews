package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.MainClass;

public class MostReviewedBenchmark {
	
	public static void main(String[] args) {

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.extractMostReviewedAuthor();
			long stopTime = System.nanoTime();
			System.out.println("extractMostReviewedAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.extractMostReviewedAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("extractMostReviewedAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}