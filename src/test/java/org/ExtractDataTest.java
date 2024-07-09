package org;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtractDataTest {
	
	public static HashMap<String, Book> booksSeq;
	public static HashMap<String, Book> booksPar;
	public static MultiValuedMap<String, Review> reviewsSeq;
	public static MultiValuedMap<String, Review> reviewsPar;
	
	
	@BeforeClass
	public static void setUp() {
		ExtractDataset ed = new ExtractDataset();
		ed.extractFromDatasetParallel();
		booksPar = ed.getBooks();
		reviewsPar = ed.getReviews();
		ed = new ExtractDataset();
		ed.extractFromDataset();
		booksSeq = ed.getBooks();
		reviewsSeq = ed.getReviews();
	}
	
	@Test
	public void extractBooksSizeTest() {
		System.out.println("\nextractBooksSizeTest");
		System.out.println("Books extracted sequential: " + booksSeq.size() + "\nBooks extracted parallel: " + booksPar.size());
		Assert.assertEquals(booksSeq.size(), booksPar.size());
	}
	
	@Test
	public void extractReviewsSizeTest() {
		System.out.println("\nextractReviewsSizeTest");
		System.out.println("Reviews extracted sequential: " + reviewsSeq.size() + "\nReviews extracted parallel: " + reviewsPar.size());
		Assert.assertEquals(reviewsSeq.size(), reviewsPar.size());
	}
	
	@Test
	public void allBooksTest() {
		Predicate<Map.Entry<String, Book>> checkAllBooks = v -> {
			Book bSeq = v.getValue();
			Book bPar = booksPar.get(v.getKey());
			
			if(bPar == null)
				return false;
			
			if(!bSeq.getTitle().equals(bPar.getTitle()))
				return false;
			
			if(!bSeq.getDescription().equals(bPar.getDescription()))
				return false;
			
			if(!bSeq.getAuthors().equals(bPar.getAuthors()))
				return false;
			
			if(!bSeq.getImage().equals(bPar.getImage()))
				return false;
			
			if(!bSeq.getPreviewLink().equals(bPar.getPreviewLink()))
				return false;
			
			if(!bSeq.getPublisher().equals(bPar.getPublisher()))
				return false;
			
			if(!bSeq.getPublishedDate().equals(bPar.getPublishedDate()))
				return false;
			
			if(!bSeq.getInfoLink().equals(bPar.getInfoLink()))
				return false;
			
			if(!bSeq.getCategories().equals(bPar.getCategories()))
				return false;
			
			if(!bSeq.getRatingCount().equals(bPar.getRatingCount()))
				return false;
			
			return true;
		};
		
		Assert.assertTrue(booksSeq.entrySet().stream().allMatch(checkAllBooks));
	}
	
	@Test
	public void allReviewsTest() {
		Predicate<Map.Entry<String, Review>> checkAllReviews = r -> {
			Review rSeq = r.getValue();
			Collection<Review> collection = reviewsPar.get(r.getKey());
			
			Optional<Review> opt = collection.stream().
					filter(rv -> rv.getTitle().equals(rSeq.getTitle()) && rv.getPrice().equals(rSeq.getPrice()) && rv.getUserID().equals(rSeq.getUserID()) && 
							rv.getProfileName().equals(rSeq.getProfileName()) && rv.getHelpfulness().equals(rSeq.getHelpfulness()) && rv.getScore().equals(rSeq.getScore()) && 
							rv.getTime().equals(rSeq.getTime()) && rv.getSummary().equals(rSeq.getSummary()) && rv.getText().equals(rSeq.getText())).
					findFirst();
			
			if(opt.isEmpty())
				return false;
			
			return true;
		};
		
		Assert.assertTrue(reviewsSeq.entries().stream().allMatch(checkAllReviews));
	}
	
	@AfterClass
	public static void cleanUp() {
		System.out.println("\ncleanUp");
		booksSeq = new HashMap<>();
		reviewsSeq = new ArrayListValuedHashMap<>();
		booksPar = new HashMap<>();
		reviewsPar = new ArrayListValuedHashMap<>();
	}
}
