package org.client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import org.Author;
import org.Book;
import org.User;
import org.data.DataInterface;
import org.data.DataService;

public class ServiceProxy implements DataInterface {

    private final String serviceNameRemote = "rmi://127.0.0.1:1099/Server";
    private DataService service;

    public ServiceProxy(){
        System.out.println("PROXY CONSTUCTOR");
        try{
            TimeUnit.SECONDS.sleep(5);
            service = (DataService) Naming.lookup(serviceNameRemote);
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
    public Book getMostReviewedBook(){
        try{
            return service.getMostReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return new Book();
        }
    }

    @Override
    public Book getLeastReviewedBook(){
        try{
            return service.getLeastReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return new Book();
        }
    }


    @Override
    public Book getAverageReviewedBook(){
        try{
            return service.getAverageReviewedBook();
        }
        catch(Exception e){
            e.printStackTrace();
            return new Book();
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
