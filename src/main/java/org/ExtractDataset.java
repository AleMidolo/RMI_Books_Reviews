package org;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;


public class ExtractDataset {
	
	private HashMap<String, Book> books;
	private MultiValuedMap<String, Review> reviews;
	
	public ExtractDataset() {
		books = new HashMap<>();
		reviews = new ArrayListValuedHashMap<>();
	}

	private MultiValuedMap<String, Review> extractReviews() {
		System.out.println("Extract Reviews...");
		MultiValuedMap<String, Review> map = new ArrayListValuedHashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("books_rating.csv"))) {
		    String line = br.readLine();
		    while ((line = br.readLine()) != null) {
		    	 String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		    	 String title = tokens[1].replace("\"", "");
		    	 Review review = new Review(tokens[0] , title, tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9]);
		         map.put(review.getBookId() ,review);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private HashMap<String, Book> extractBooks(){
		System.out.println("Extract Books...");
		HashMap<String, Book> map = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("books_data.csv"))) {
		    String line = br.readLine();
		    while ((line = br.readLine()) != null) {
		    	 String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		    	 Book newBook = new Book(tokens[0] , tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9]);
		         map.put(newBook.getTitle(), newBook);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void extractFromDataset() {
		books = extractBooks();
		reviews = extractReviews();
	}
	
	public void extractFromDatasetParallel() {
		try {
			CompletableFuture<HashMap<String, Book>> future = CompletableFuture.supplyAsync(() -> extractBooks());
			reviews = extractReviews();
			books = future.get();
			System.out.println("Books: " + books.size() + "\nReviews: " + reviews.size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Book> getBooks() {
		return books;
	}
	
	public MultiValuedMap<String, Review> getReviews() {
		return reviews;
	}
}
