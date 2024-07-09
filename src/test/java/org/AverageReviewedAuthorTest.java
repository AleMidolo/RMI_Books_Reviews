package org;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AverageReviewedAuthorTest {
	
	public static HashMap<String, Book> books = new HashMap<>();
	public static MultiValuedMap<String, Review> reviews = new ArrayListValuedHashMap<>();
	public static Optional<Author> authorSeq;
	public static Optional<Author> authorPar;
	
	@BeforeClass
	public static void setUp() throws InterruptedException, ExecutionException {
		System.out.println("\nSetUp");
		ExtractDataset ed = new ExtractDataset();
		ed.extractFromDatasetParallel();
		books = ed.getBooks();
		reviews = ed.getReviews();
		authorSeq = MainClass.extractAverageReviewedAuthor(books, reviews);
		authorPar = MainClass.extractAverageReviewedAuthorParallel(books, reviews);
	}
	
	@Test
	public void averageAuthorTest() {
		System.out.println("\nAverageAuthorTest");
		if(authorSeq.isPresent() && authorPar.isPresent()) {
			System.out.println("Average Author names: " + authorSeq.get().getFullName() + "---" + authorPar.get().getFullName());
			Assert.assertEquals(authorSeq.get().getFullName(), authorPar.get().getFullName());
		}
		else
			Assert.assertTrue("Authors not present", false);
	}
	
	@Test
	public void booksOfAverageAuthorTest() {
		System.out.println("\nbooksOfAverageAuthorTest");
		
		if(authorSeq.isPresent() && authorPar.isPresent()) {
			System.out.println("Number of Books: " + authorSeq.get().getBooks().size() + "---" + authorPar.get().getBooks().size());
			Assert.assertEquals(authorSeq.get().getBooks().size(), authorPar.get().getBooks().size());
		}
		else
			Assert.assertTrue("Authors not present", false);
	}
	
	@Test
	public void averageValueTest() {
		System.out.println("\naverageValueTest");
		
		if(authorSeq.isPresent() && authorPar.isPresent()) {

			Book bSeq = authorSeq.get().getBooks().get(0);
			Book bPar = authorPar.get().getBooks().get(0);
			
			System.out.println("Average Number of reviews: " + bSeq.getReviews().size() + "---" + bPar.getReviews().size());
			Assert.assertEquals(bSeq.getReviews().size(), bPar.getReviews().size());
		}
		else
			Assert.assertTrue("Authors not present", false);
	}
	
	@AfterClass
	public static void cleanUp() {
		System.out.println("\ncleanUp");
		books = new HashMap<>();
		reviews = new ArrayListValuedHashMap<>();
	}
}
