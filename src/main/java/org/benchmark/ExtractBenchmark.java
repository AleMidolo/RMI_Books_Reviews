package org.benchmark;

import java.util.concurrent.TimeUnit;

import org.ExtractDataset;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

public class ExtractBenchmark {
	
	@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(value=1, warmups=1)
	@Measurement(time=1, timeUnit = TimeUnit.MILLISECONDS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Benchmark
    public static void extractBooksAndReviewsSequential() {
		long startTime = System.nanoTime();
		ExtractDataset ed = new ExtractDataset();
		ed.extractFromDataset();
		long stopTime = System.nanoTime();
		System.out.println("Total Time: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
	}
	
	@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(value=1, warmups=1)
	@Measurement(time=1, timeUnit = TimeUnit.MILLISECONDS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Benchmark
    public static void extractBooksAndReviewsParallel() {
		long startTime = System.nanoTime();
		ExtractDataset ed = new ExtractDataset();
		ed.extractFromDatasetParallel();
		long stopTime = System.nanoTime();
		System.out.println("Total Time: " + TimeUnit.MILLISECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
	}
}