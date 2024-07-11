package org.client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.util.Optional;
import java.util.HashMap;

import org.Author;
import org.Book;
import org.User;
import org.amazon.AmazonInterface;
import org.amazon.AmazonService;

public class ServiceProxy implements AmazonInterface {

    private final String serviceNameRemote = "rmi://127.0.0.1:1099/Server";
    private AmazonService service;

    public ServiceProxy(){
        System.out.println("PROXY CONSTUCTOR");
        try{
            TimeUnit.SECONDS.sleep(5);
            service = (AmazonService) Naming.lookup(serviceNameRemote);
        }
        catch (RemoteException | MalformedURLException | NotBoundException | InterruptedException e){
            System.err.println(e);
        }
    }

    @Override
    public HashMap<String, Author> getAuthors(){
        try{
            return service.getAuthors();
        }
        catch(Exception e){
            e.printStackTrace();
            return new HashMap<String, Author>();
        }
    }

    @Override
    public Optional<Book> getMostReviewedBook(){
        try{
            return service.getMostReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getLeastReviewedBook(){
        try{
            return service.getLeastReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Book> getAverageReviewedBook(){
        try{
            return service.getAverageReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public HashMap<String, User> getUserForAuthor(){
        try{
            return service.getUserForAuthor();
        }
        catch(Exception e){
            e.printStackTrace();
            return new HashMap<String, User>();
        }
    }
}
