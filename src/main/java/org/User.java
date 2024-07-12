package org;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
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
