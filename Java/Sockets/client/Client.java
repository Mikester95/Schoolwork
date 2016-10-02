package client;

import java.io.*;
import java.net.*;
import user.*;
import utility.*;

public class Client {
    
    private static final String serverName = "localhost";
    private static final int portNumber = 6066;
    private static Client instance = null;
    
    DataInputStream stdin;
    
    private Client() {
        stdin = new DataInputStream(System.in);
    }
    
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }
    
    private User user;
    public void registerUser() {
        System.out.println("Choose a username");
        try {
            String name = this.readString();
            user = new User(name);
        } catch (IOException e) {
            System.out.println("Input/Output exception for user registration");
        }
    }
    
    public void continuousPlay() {
        try {
            while (true) {
                connectToServer();
                requestMatch();
                arrangeMatch();
            }
        } catch (IOException e) {
            continuousPlay();
        }
    }
    
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private boolean connectTo(String serverName, int portNumber) {
      try
      {
         System.out.println("Connecting to " + serverName +
		 " on port " + portNumber);
         socket = new Socket(serverName, portNumber);
         System.out.println("Just connected to " 
		 + socket.getRemoteSocketAddress());
         return true;
      }catch(IOException e)
      {
          System.out.println("Failed to connect to server");
          return false;
      }
    }
    
    private void connectToServer() throws IOException {
        boolean connected = false;
        while(!connected) {
            connected = this.connectTo(serverName, portNumber);
        }
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    private void requestMatch() throws IOException {
        out.writeObject(user);
    }
    
    private void arrangeMatch() throws IOException {
        try {
            String verdict = "PING";
            do {
                verdict = (String) in.readObject();
            } while (verdict.equals("PING"));
            
            User opponent = (User) in.readObject();
            
            if (verdict.equals("SER")) {
                System.out.println("You will now be facing off against " + opponent.getName());
                PeerToPeerServer serv = new PeerToPeerServer(user);
                serv.play();
            } else {
                InetAddress inetAddress = (InetAddress) in.readObject();
                System.out.println("You will now be facing off against " + opponent.getName());
                PeerToPeerClient cl = new PeerToPeerClient(user, inetAddress);
                cl.play();
            }
            socket.close();
        }catch (ClassNotFoundException e) {
            System.out.println("Couldn't find string class");
        }
    }
    
    public String readString() throws IOException {
        return stdin.readLine();
    }
}
