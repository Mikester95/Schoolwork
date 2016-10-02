package server;

import java.rmi.*;
import java.util.*;
import java.net.*;
import java.rmi.registry.*;
import accountmanager.AccountManager;
import accountmanager.AccountManagerPublic;

public class Main {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
            final String name = "AccountManagerPublic";
            final int port = 63000;
            
            AccountManager accountManager = new AccountManager();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(name, accountManager);
            
            System.out.println("AccountManager bound");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
