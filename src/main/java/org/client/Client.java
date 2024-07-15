package org.client;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.Author;
import org.Book;
import org.User;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;

import org.amazon.AmazonInterface;


public class Client {
	
    private static AmazonInterface service = new ServiceProxy();

    private static RetryPolicy<Object> retryPolicy = RetryPolicy.builder().
    	    handle(Exception.class).
    	    withBackoff(1, 30, ChronoUnit.SECONDS).
    	    withMaxRetries(3).
    	    onRetriesExceeded(e -> 
    	        System.out.println("Failed to connect. Max retries exceeded.")).
    	    build();
    
	public static Optional<Author> extractMostReviewedAuthor() {
		
		HashMap<String, Author> authors = service.getAuthors();
		Book book = service.getMostReviewedBook();
		
		return authors.values().stream().
				filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(book.getTitle()))).
				findFirst();
	}
	
	public static Optional<Author> extractMostReviewedAuthorParallel() {
		try {
			CompletableFuture<HashMap<String, Author>> f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Book book = service.getMostReviewedBook();
			HashMap<String, Author> authors = f.get();
			
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(book.getTitle()))).
					findFirst();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static Optional<Author> extractLeastReviewedAuthor() {
		
		HashMap<String, Author> authors = service.getAuthors();
		Book leastBook = service.getLeastReviewedBook();
		
		return authors.values().stream().
				filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(leastBook.getTitle()))).
				findFirst();
	}
	
	public static Optional<Author> extractLeastReviewedAuthorParallel() {
		try {
			CompletableFuture<HashMap<String, Author> > f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Book leastBook = service.getLeastReviewedBook();
			HashMap<String, Author>  authors = f.get();
			
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(leastBook.getTitle()))).
					findFirst();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static Optional<Author> extractAverageReviewedAuthor() {
		HashMap<String, Author>  authors = service.getAuthors();
		Book averageBook = service.getAverageReviewedBook();
		
		return authors.values().stream().
				filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(averageBook.getTitle()))).
				findFirst();
	}
	
	public static Optional<Author> extractAverageReviewedAuthorParallel() {
		try {
			CompletableFuture<HashMap<String, Author> > f = Failsafe.with(retryPolicy).getAsync(() -> service.getAuthors());
			Book averageBook = service.getAverageReviewedBook();
			HashMap<String, Author>  authors = f.get();
			
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(averageBook.getTitle()))).
					findFirst();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static HashMap<String, Author> getUserForAuthor() {
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
	
	public static HashMap<String, Author> getUserForAuthorParallel() {
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
