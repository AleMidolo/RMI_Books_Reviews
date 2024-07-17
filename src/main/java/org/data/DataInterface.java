package org.data;
import java.util.HashMap;

import org.Author;
import org.Book;
import org.User;

public interface DataInterface{

    HashMap<String, Author> getAuthors();

    Book getMostReviewedBook();

    Book getLeastReviewedBook();

    Book getAverageReviewedBook();

    HashMap<String, User> getUserForAuthor();
}