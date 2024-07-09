package org;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UsersForAuthorsTest {
	
	public static HashMap<String, Book> books = new HashMap<>();
	public static MultiValuedMap<String, Review> reviews = new ArrayListValuedHashMap<>();
	public static HashMap<String, Author> authorsSeq;
	public static HashMap<String, Author> authorsPar;
	
	@BeforeClass
	public static void setUp() throws InterruptedException, ExecutionException {
		System.out.println("\nSetUp");
		ExtractDataset ed = new ExtractDataset();
		ed.extractFromDatasetParallel();
		books = ed.getBooks();
		reviews = ed.getReviews();
		authorsSeq = MainClass.getUserForAuthor(books, reviews);
		authorsPar = MainClass.getUserForAuthorParallel(books, reviews);
	}
	
	@Test
	public void authorsSizeTest() {
		System.out.println("\nauthorsSizeTest");
		System.out.println("Number of authors: " + authorsSeq.size() + "---" + authorsPar.size());
		Assert.assertEquals(authorsSeq.size(), authorsPar.size());
	}
	
	@Test
	public void authorsMapTest() {
		System.out.println("\nauthorsMapTest");
		
		Predicate<Map.Entry<String, Author>> checkAllFields = v -> {
			Author aSeq = v.getValue();
			Author aPar = authorsPar.get(v.getKey());
			
			if(aPar == null)
				return false;
			
			if(!aSeq.getFullName().equals(aPar.getFullName()))
				return false;
			
			if(aSeq.getBooks().size() != aPar.getBooks().size())
				return false;
			
			if(aSeq.getUsers().size() != aPar.getUsers().size())
				return false;
			
			return true;
		};
		
		Assert.assertTrue(authorsSeq.entrySet().stream().allMatch(checkAllFields));
	}
	
	@Test
	public void getAllUsersTest() {
		
		Predicate<Map.Entry<String, Author>> checkAllUsers = v -> {
			Author aSeq = v.getValue();
			Author aPar = authorsPar.get(v.getKey());
			
			if(aPar == null)
				return false;
			
			for(User uSeq : aSeq.getUsers()) {
				if(uSeq == null)
					continue;
				Optional<User> uPar = aPar.getUsers().stream().
					filter(usr -> usr != null).
					filter(usr -> usr.getId().equals(uSeq.getId())).
					findFirst();
				if(uPar.isEmpty())
					return false;
				
				if(!uSeq.getNickname().equals(uPar.get().getNickname()))
					return false;
				
				if(uSeq.getReviews().size() != uPar.get().getReviews().size())
					return false;
			}
			return true;
		};
		
		System.out.println("\ngetAllUsersTest");
		Assert.assertTrue(authorsSeq.entrySet().stream().allMatch(checkAllUsers));
	}
	
	@AfterClass
	public static void cleanUp() {
		System.out.println("\ncleanUp");
		books = new HashMap<>();
		reviews = new ArrayListValuedHashMap<>();
		authorsSeq = new HashMap<>();
		authorsPar = new HashMap<>();
	}
}
