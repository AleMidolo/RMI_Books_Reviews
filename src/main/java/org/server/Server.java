package org.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.amazon.AmazonService;

public class Server {

	//changed
    private static AmazonService service;


    public static void main(String [] args) throws RemoteException {

        System.out.println("Starting server");
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        String url = "rmi://127.0.0.1:1099/Server";

        service = new Service();

        try{
        	LocateRegistry.createRegistry(1099);
            Naming.rebind(url,service);
            System.out.println("SERVER IS UP");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println("On URL: " + url);
            while(true){}
        }catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
