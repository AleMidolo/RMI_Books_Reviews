package org.amazon;
import java.util.HashMap;
import java.util.Optional;

import org.Author;
import org.Book;
import org.User;

public interface AmazonInterface{

    HashMap<String, Author> getAuthors();

    Optional<Book> getMostReviewedBook();

    Optional<Book> getLeastReviewedBook();

    Optional<Book> getAverageReviewedBook();

    HashMap<String, User> getUserForAuthor();
}