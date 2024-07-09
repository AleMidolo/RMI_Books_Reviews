package org;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String userId;
	private String nickName;
	private List<Review> reviews;
	
	public User(String userId, String nickName) {
		this.userId = userId;
		this.nickName = nickName;
		reviews = new ArrayList<>();
	}
	
	public String getId() {
		return userId;
	}
	
	public String getNickname() {
		return nickName;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void addReview(Review r) {
		reviews.add(r);
	}
}
