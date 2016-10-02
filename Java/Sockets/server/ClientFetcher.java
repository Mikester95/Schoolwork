package server;

import java.net.*;
import java.io.*;
import user.*;

class ClientFetcher extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    ClientFetcher(String threadName, int port) {
        super(threadName);
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            System.err.println("Cannot create ServerSocket!");
        }
    }
    
    private boolean started = false;
    public boolean isStarted() {
        return started;
    }
    
    public void run() {
      while(true)
      {
         try {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            socket = serverSocket.accept();
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Just connected to "
                  + socket.getRemoteSocketAddress());
            
         } catch(SocketTimeoutException s) {
            System.err.println("Socket timed out!");
            break;
         } catch(IOException e) {
            System.err.println("Could not connect to client!");
            break;
         }
         
         ServerUser user = null;
         try {
             user = new ServerUser((User) in.readObject(), socket, in, out);
         } catch(IOException e) {
             System.err.println("Couldn't read from the socket");
         } catch (ClassNotFoundException e) {
             System.err.println("Couldn't find class user");
         }
         
         if (user != null) {
             (new MatchMaker("matchMakingThread" + user.getName(), user)).start();
         }
      }
    }
    
    public void start() {
        if (!started) {
            started = true;
            this.run();
        }
    }
}
