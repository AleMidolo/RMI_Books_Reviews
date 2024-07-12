package org.amazon;
import java.rmi.*;
import java.util.HashMap;

import org.Author;
import org.Book;
import org.User;

public interface AmazonService extends Remote{

    HashMap<String, Author> getAuthors() throws RemoteException;

    Book getMostReviewedBook() throws RemoteException;

    Book getLeastReviewedBook() throws RemoteException;

    Book getAverageReviewedBook() throws RemoteException;

    HashMap<String, User> getUserForAuthor() throws RemoteException;
}