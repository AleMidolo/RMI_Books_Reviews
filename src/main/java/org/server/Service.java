package org.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.Author;
import org.Book;
import org.ExtractDataset;
import org.Review;
import org.User;
import org.amazon.AmazonService;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class Service extends UnicastRemoteObject implements AmazonService {
	
	HashMap<String, Book> books = new HashMap<>();
	MultiValuedMap<String, Review> reviews = new ArrayListValuedHashMap<>();
    
	protected Service() throws RemoteException {
		super();
		ExtractDataset extractor = new ExtractDataset();
		extractor.extractFromDatasetParallel();
		books = extractor.getBooks();
		reviews = extractor.getReviews();
	}
	
	public HashMap<String, Book> getBooks() {
		return books;
	}
	
	public MultiValuedMap<String, Review> getReviews() {
		return reviews;
	}

	@Override
	public HashMap<String, Author> getAuthors() throws RemoteException {
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

	@Override
	public Optional<Book> getMostReviewedBook() throws RemoteException {
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

	@Override
	public Optional<Book> getLeastReviewedBook() throws RemoteException {
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

	@Override
	public Optional<Book> getAverageReviewedBook() throws RemoteException {
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

	@Override
	public HashMap<String, User> getUserForAuthor() throws RemoteException {
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

}
