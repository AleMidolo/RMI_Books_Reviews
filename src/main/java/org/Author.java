package org;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Author {

	private String fullName;
	private List<Book> books;
	private HashSet<User> users;
	
	public Author(String fullName) {
		this.fullName = fullName;
		books = new ArrayList<>();
		users = new HashSet<>();
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void addBook(Book b) {
		if(!books.contains(b))
			books.add(b);
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public HashSet<User> getUsers() {
		return users;
	}
}