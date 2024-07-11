package org.amazon;
import java.rmi.*;
import java.util.HashMap;
import java.util.Optional;

import org.Author;
import org.Book;
import org.User;

public interface AmazonService extends Remote{

    HashMap<String, Author> getAuthors() throws RemoteException;

    Optional<Book> getMostReviewedBook() throws RemoteException;

    Optional<Book> getLeastReviewedBook() throws RemoteException;

    Optional<Book> getAverageReviewedBook() throws RemoteException;

    HashMap<String, User> getUserForAuthor() throws RemoteException;
}