package org.client;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.Author;
import org.Book;
import org.Review;
import org.User;
import org.apache.commons.collections4.MultiValuedMap;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;

import org.amazon.AmazonInterface;


public class MainClass {
		
	public static final String COMMA_DELIMITER = ",";
	
    static AmazonInterface service = new ServiceProxy();
    private static RetryPolicy<Object> retryPolicy = RetryPolicy.builder().
    	    handle(Exception.class).
    	    withBackoff(1, 30, ChronoUnit.SECONDS).
    	    withMaxRetries(3).
    	    onRetriesExceeded(e -> 
    	        System.out.println("Failed to connect. Max retries exceeded.")).
    	    build();
    
	public static Optional<Author> extractMostReviewedAuthor(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		
		HashMap<String, Author> authors = service.getAuthors();
		Optional<Book> book = service.getMostReviewedBook();
		
		if(book.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(book.get().getTitle()))).
					findFirst();
		
		return Optional.empty();
	}
	
	public static Optional<Author> extractMostReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			CompletableFuture<HashMap<String, Author>> f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Optional<Book> book = service.getMostReviewedBook();
			HashMap<String, Author> authors = f.get();
			
			if(book.isPresent())
				return authors.values().stream().
						filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(book.get().getTitle()))).
						findFirst();
			return Optional.empty();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static Optional<Author> extractLeastReviewedAuthor(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		
		HashMap<String, Author> authors = service.getAuthors();
		Optional<Book> leastBook = service.getLeastReviewedBook();
		
		if(leastBook.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(leastBook.get().getTitle()))).
					findFirst();
		else
			return Optional.empty();
	}
	
	public static Optional<Author> extractLeastReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			CompletableFuture<HashMap<String, Author> > f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Optional<Book> leastBook = service.getLeastReviewedBook();
			HashMap<String, Author>  authors = f.get();
			
			if(leastBook.isPresent())
				return authors.values().stream().
						filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(leastBook.get().getTitle()))).
						findFirst();
			else
				return Optional.empty();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static Optional<Author> extractAverageReviewedAuthor(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		HashMap<String, Author>  authors = service.getAuthors();
		Optional<Book> averageBook = service.getAverageReviewedBook();
		
		if(averageBook.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(averageBook.get().getTitle()))).
					findFirst();
		else
			return Optional.empty();
	}
	
	public static Optional<Author> extractAverageReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			CompletableFuture<HashMap<String, Author> > f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Optional<Book> averageBook = service.getAverageReviewedBook();
			HashMap<String, Author>  authors = f.get();
			
			if(averageBook.isPresent())
				return authors.values().stream().
						filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(averageBook.get().getTitle()))).
						findFirst();
			else
				return Optional.empty();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static HashMap<String, Author> getUserForAuthor(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		HashMap<String, Author>  authors = service.getAuthors();
		HashMap<String, User> users = service.getUserForAuthor();
		
		authors.values().forEach(author -> {
			List<String> usersId = author.getBooks().stream().
				flatMap(b -> b.getReviews().stream().map(r -> r.getUserID())).
				collect(Collectors.toList());
			
			usersId.forEach(u -> {
				User user = users.get(u);
				if(u != null)
					author.addUser(user);
			});
		});
		return authors;
	}
	
	public static HashMap<String, Author> getUserForAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			CompletableFuture<HashMap<String, Author>>  f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			HashMap<String, User> users = service.getUserForAuthor();
			HashMap<String, Author>  authors = f.get();
			
			authors.values().forEach(author -> {
				List<String> usersId = author.getBooks().stream().
					filter(b -> b.getReviews().size() > 0).
					flatMap(b -> b.getReviews().stream().map(r -> r.getUserID())).
					collect(Collectors.toList());
				
				usersId.forEach(u -> {
					User user = users.get(u);
					if(u != null)
						author.addUser(user);
				});
			});
			
			return authors;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new HashMap<String, Author>();
		}
	}
}
