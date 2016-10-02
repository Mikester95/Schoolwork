package server;

import java.io.*;
import java.net.*;
import user.*;

public class Server {
    
    private static Server instance = null;
    private ClientFetcher clientFetcher;
    
    private final int portNumber = 6066;
    private Server() {
        clientFetcher = new ClientFetcher("clientFetcherThread", portNumber);
    }
    
    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
    
    public static boolean isConnected(ServerUser user) {
        try {
            user.getOutputStream().writeObject("PING");
        } catch(IOException e) {
            return false;
        }
        return true;
    }
    
    public void start() {
        clientFetcher.start();
    }
}
