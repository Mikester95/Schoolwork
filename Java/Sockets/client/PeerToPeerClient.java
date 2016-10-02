package client;

import user.*;
import java.io.*;
import java.net.*;

public class PeerToPeerClient extends Player{
    
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    InetAddress target;
    private static final int portNumber = 8000;
        
    PeerToPeerClient(User user, InetAddress target) throws IOException {
        super(user);
        try {
            System.out.println("Client");
            this.target = target;
            connectToHost();
        } catch(IOException e) {
            if (socket != null) {
                socket.close();
            }
            throw new IOException();
        }
    }
    
    private boolean connectTo(InetAddress addr, int portNumber) {
        try {
           socket = new Socket(addr, portNumber);
           return true;
        } catch (IOException e) {
            System.out.println("Failed to connect to host");
            return false;
        }
    }
    
    private void connectToHost() throws IOException {
        boolean connected = false;
        while(!connected) {
            connected = connectTo(target, portNumber);
        }
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }
    
    protected int makeMove(char choice) throws IOException {
        try {
            out.writeChar(choice);
            char othersChoice = in.readChar();
            return evaluate(choice, othersChoice);
        } catch (IOException e) {
            System.out.println("Opponent surrendered!");
            socket.close();
            return 1;
        }
    }
    
    public void play() throws IOException {
        try {
            super.play();
        } catch(IOException e) {
            System.out.println("Something went horribly wrong");
        } finally {
            System.out.println("Executed");
            socket.close();
        }
    }
}
