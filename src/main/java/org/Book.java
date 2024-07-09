package org;

import java.util.ArrayList;
import java.util.List;

public class Book {

	private String title;
	private String description;
	private String authors;
	private String image;
	private String previewLink;
	private String publisher;
	private String publishedDate;
	private String infoLink;
	private String categories;
	private String ratingCount;
	private List<Review> reviews;
	private double mediumScore;
	
	public Book(String title, String description, String authors, String image, String previewLink, String publisher, String publishedDate, String infoLink, String categories, String ratingCount) {
		this.title = title;
		this.description = description;
		this.authors = authors;
		this.image = image;
		this.previewLink = previewLink;
		this.publisher = publisher;
		this.publishedDate = publishedDate;
		this.infoLink = infoLink;
		this.categories = categories;
		this.ratingCount = ratingCount;
		reviews = new ArrayList<>();
		mediumScore = 0.0;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthors() {
		return authors;
	}

	public String getImage() {
		return image;
	}

	public String getPreviewLink() {
		return previewLink;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public String getCategories() {
		return categories;
	}

	public String getRatingCount() {
		return ratingCount;
	}
	
	public List<Review> getReviews() {
		return reviews;
		
	}
	
	public void addReview(Review r) {
		reviews.add(r);
	}
	
	public void updateMediumScore() {
		Double total = reviews.stream().
			map(r -> Double.valueOf(r.getScore())).
			reduce(0.0, (acc, v) -> acc + v);
		
		mediumScore = total / reviews.size();
	}
	
	public double getMediumScore() {
		return mediumScore;
	}
	
	public void resetReviews() {
		reviews = new ArrayList<>();
	}
}
