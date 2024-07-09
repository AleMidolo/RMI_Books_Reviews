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

public class MostReviewedAuthorTest {
	
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
		authorSeq = MainClass.extractMostReviewedAuthor(books, reviews);
		authorPar = MainClass.extractMostReviewedAuthorParallel(books, reviews);
	}
	
	@Test
	public void mostAuthorTest() {
		System.out.println("\nmostAuthorTest");
		if(authorSeq.isPresent() && authorPar.isPresent()) {
			System.out.println("Most Author names: " + authorSeq.get().getFullName() + "---" + authorPar.get().getFullName());
			Assert.assertEquals(authorSeq.get().getFullName(), authorPar.get().getFullName());
		}
		else
			Assert.assertTrue("Authors not present", false);
	}
	
	@Test
	public void booksOfMostAuthorTest() {
		System.out.println("\nbooksOfMostAuthorTest");
		
		if(authorSeq.isPresent() && authorPar.isPresent()) {
			System.out.println("Number of Books: " + authorSeq.get().getBooks().size() + "---" + authorPar.get().getBooks().size());
			Assert.assertEquals(authorSeq.get().getBooks().size(), authorPar.get().getBooks().size());
		}
		else
			Assert.assertTrue("Authors not present", false);
	}
	
	@Test
	public void highestValueTest() {
		System.out.println("\nhighestValueTest");
		
		if(authorSeq.isPresent() && authorPar.isPresent()) {

			Book bookSeq = authorSeq.get().getBooks().stream().max((val1, val2) -> val1.getReviews().size() > val2.getReviews().size() ? 1 : -1).get();
			Book bookpar = authorPar.get().getBooks().stream().max((val1, val2) -> val1.getReviews().size() > val2.getReviews().size() ? 1 : -1).get();
			
			System.out.println("Highest Number of reviews: " + bookSeq.getReviews().size() + "---" + bookpar.getReviews().size());
			Assert.assertEquals(bookSeq.getReviews().size(), bookpar.getReviews().size());
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
