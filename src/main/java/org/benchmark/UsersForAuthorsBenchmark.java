package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.client.MainClass;

public class UsersForAuthorsBenchmark {
	
	public static void main(String[] args) {

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.getUserForAuthor();
			long stopTime = System.nanoTime();
			System.out.println("getUserForAuthor: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
		System.out.println();

		for(int i=0; i<10; i++) {
			long startTime = System.nanoTime();
			MainClass.getUserForAuthorParallel();
			long stopTime = System.nanoTime();
			System.out.println("getUserForAuthorParallel: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
		}
	}
}
