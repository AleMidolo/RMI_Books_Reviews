package org.benchmark;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.Book;
import org.ExtractDataset;
import org.MainClass;
import org.Review;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

public class LeastReviewedBenchmark {
	
	@State(Scope.Benchmark)
    public static class MyState {

		HashMap<String, Book> books = new HashMap<>();
		MultiValuedMap<String, Review> reviews = new ArrayListValuedHashMap<>();

		@Setup(Level.Trial)
		public void setUp() {
			System.out.println("\nSetUp");
			ExtractDataset ed = new ExtractDataset();
			ed.extractFromDatasetParallel();
			books = ed.getBooks();
			reviews = ed.getReviews();
		}
    }

	
	@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(value=1, warmups=1)
	@Measurement(time=1, timeUnit = TimeUnit.MILLISECONDS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Benchmark
    public static void leastReviewedAuthorSequential(MyState myState) {
		long startTime = System.nanoTime();
		MainClass.extractLeastReviewedAuthor(myState.books, myState.reviews);
		long stopTime = System.nanoTime();
		System.out.println("Total Time: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
	}
	
	@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(value=1, warmups=1)
	@Measurement(time=1, timeUnit = TimeUnit.MILLISECONDS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Benchmark
    public static void leastReviewedAuthorParallel(MyState myState) {
		long startTime = System.nanoTime();
		MainClass.extractLeastReviewedAuthorParallel(myState.books, myState.reviews);
		long stopTime = System.nanoTime();
		System.out.println("Total Time: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
	}
}
