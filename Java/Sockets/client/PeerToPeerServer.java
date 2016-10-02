package client;

import java.io.*;
import java.net.*;
import user.*;

public class PeerToPeerServer extends Player {
    Socket socket;
    ServerSocket serverSocket;
    DataInputStream in;
    DataOutputStream out;
    private static final int portNumber = 8000;
        
    PeerToPeerServer(User user) throws IOException {
        super(user);
        try {
            System.out.println("Server");
            acceptConnection();
        } catch (IOException e) {
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            throw new IOException();
        }
    }
    
    private void acceptConnection() throws IOException {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            System.out.println("Culp!");
        }
        socket = serverSocket.accept();
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            System.out.println("Here you are!");
        }
    }
    
    protected int makeMove(char choice) throws IOException {
        try {
            char othersChoice = in.readChar();
            out.writeChar(choice);
            return evaluate(choice, othersChoice);
        } catch (IOException e) {
            System.out.println("Opponent surrendered!");
            socket.close();
            serverSocket.close();
            return 1;
        }
    }
    
    public void play() throws IOException {
        try {
            super.play();
        } catch(IOException e) {
            System.out.println("Something went horribly wrong");
        } finally {
            socket.close();
            serverSocket.close();
        }
    }
}
