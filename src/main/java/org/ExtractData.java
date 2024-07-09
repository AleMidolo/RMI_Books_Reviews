package org;

import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.apache.commons.collections4.MultiValuedMap;


public class ExtractData {
	
	private HashMap<String, Book> books;
	private MultiValuedMap<String, Review> reviews;
	
	public ExtractData(HashMap<String, Book> books, MultiValuedMap<String, Review> reviews) {
		this.books = books;
		this.reviews = reviews;
	}

	public HashMap<String, Book> getBooks() {
		return books;
	}
	
	public MultiValuedMap<String, Review> getReviews() {
		return reviews;
	}
	
	public HashMap<String, Author> getAuthors() {
		System.out.println("getAuthors");
		HashMap<String, Author> authors = new HashMap<>();
		
		books.values().forEach(b -> {
			Author author = authors.get(b.getAuthors());
			if(author != null)
				author.addBook(b);
			else {
				Author ath = new Author(b.getAuthors());
				ath.addBook(b);
				authors.put(b.getAuthors(), ath);
			}
		});
		
		return authors;
	}
	
	public HashMap<String, User> getUserForAuthor() {
		System.out.println("getUserForAuthor");
		HashMap<String, User> users = new HashMap<>();
		
		reviews.values().forEach(r -> {
			User user = users.get(r.getUserID());
			
			if(user != null)
				user.addReview(r);
			else {
				User usr = new User(r.getUserID(), r.getProfileName());
				usr.addReview(r);
				users.put(r.getUserID(), usr);
			}
		});
		
		updateBooksReviews();
		return users;
	}
	
	public void updateBooksReviews() {
		System.out.println("getReviewsForBook");
		books.values().forEach(b -> b.resetReviews());
		reviews.values().forEach(r -> {
			Book b = books.get(r.getTitle());
			if(b != null)
				b.addReview(r);
		});
		books.values().forEach(b -> b.updateMediumScore());
	}
	
	public Optional<Book> getMostReviewedBook() {
		System.out.println("getMostReviewedBook");
		updateBooksReviews();
		
		OptionalInt maximum = books.values().stream().
			mapToInt(b -> b.getReviews().size()).
			max();
		
		if(maximum.isPresent()) {
			Optional<Book> book = books.values().stream().
					filter(e -> e.getReviews().size() == maximum.getAsInt()).
					findFirst();
			return book;
		}
		return Optional.empty();
	}
	
	
	public Optional<Book> getLeastReviewedBook() {
		System.out.println("getLeastReviewedBooks");
		updateBooksReviews();
		OptionalInt minimum = books.values().stream().
				mapToInt(b -> b.getReviews().size()).
				min();
		
		if(minimum.isPresent()) {
			Optional<Book> book = books.values().stream().
					filter(b -> b.getReviews().size() == minimum.getAsInt()).
					findFirst();
			return book;
		}
		return Optional.empty();
	}
	
	public Optional<Book> getAverageReviewedBook() {
		System.out.println("getAverageReviewedBook");
		updateBooksReviews();
		OptionalDouble average = books.values().stream().
				filter(b -> b.getReviews().size() > 0).
				mapToInt(b -> b.getReviews().size()).
				average();

		if(average.isPresent()) {
			int val = books.values().stream().
					filter(b -> b.getReviews().size() > 0).
					map(b -> b.getReviews().size()).
		            min(Comparator.comparingInt(i -> Math.abs(i - (int)average.getAsDouble()))).
		            orElseThrow(() -> new NoSuchElementException("No value present"));
			
			Optional<Book> book = books.values().stream().
					filter(b -> b.getReviews().size() == val).
					findFirst();
			return book;
		}
		
		return Optional.empty();
	}
}
