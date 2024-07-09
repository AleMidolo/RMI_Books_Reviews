package org;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MultiValuedMap;


public class MainClass {
		
	public static final String COMMA_DELIMITER = ",";
	
	public static Optional<Author> extractMostReviewedAuthor(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		ExtractData extractor = new ExtractData(books, reviews);

		HashMap<String, Author> authors = extractor.getAuthors();
		Optional<Book> book = extractor.getMostReviewedBook();
		
		if(book.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(book.get().getTitle()))).
					findFirst();
		
		return Optional.empty();
	}
	
	public static Optional<Author> extractMostReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			ExtractData extractor = new ExtractData(books, reviews);
			CompletableFuture<HashMap<String, Author>> f = CompletableFuture.supplyAsync(() -> extractor.getAuthors());
			Optional<Book> book = extractor.getMostReviewedBook();
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
		ExtractData extractor = new ExtractData(books, reviews);
		HashMap<String, Author> authors = extractor.getAuthors();
		Optional<Book> leastBook = extractor.getLeastReviewedBook();
		
		if(leastBook.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(leastBook.get().getTitle()))).
					findFirst();
		else
			return Optional.empty();
	}
	
	public static Optional<Author> extractLeastReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			ExtractData extractor = new ExtractData(books, reviews);
			CompletableFuture<HashMap<String, Author> > f = CompletableFuture.supplyAsync(() -> extractor.getAuthors());
			Optional<Book> leastBook = extractor.getLeastReviewedBook();
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
		ExtractData extractor = new ExtractData(books, reviews);
		HashMap<String, Author>  authors = extractor.getAuthors();
		Optional<Book> averageBook = extractor.getAverageReviewedBook();
		
		if(averageBook.isPresent())
			return authors.values().stream().
					filter(auth -> auth.getBooks().stream().anyMatch(b -> b.getTitle().equals(averageBook.get().getTitle()))).
					findFirst();
		else
			return Optional.empty();
	}
	
	public static Optional<Author> extractAverageReviewedAuthorParallel(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		try {
			ExtractData extractor = new ExtractData(books, reviews);
			CompletableFuture<HashMap<String, Author> > f = CompletableFuture.supplyAsync(() -> extractor.getAuthors());
			Optional<Book> averageBook = extractor.getAverageReviewedBook();
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
		ExtractData extractor = new ExtractData(books, reviews);
		HashMap<String, Author>  authors = extractor.getAuthors();
		HashMap<String, User> users = extractor.getUserForAuthor();
		
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
			ExtractData extractor = new ExtractData(books, reviews);
			CompletableFuture<HashMap<String, Author>>  f = CompletableFuture.supplyAsync(() -> extractor.getAuthors());
			HashMap<String, User> users = extractor.getUserForAuthor();
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
