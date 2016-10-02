package server;

import java.io.*;
import user.*;

public class MatchMaker extends Thread {
    
    ServerUser user;
    
    public MatchMaker(String threadName, ServerUser user) {
        super(threadName);
        this.user = user;
    }
    
    private static boolean started = false;
    
    public void run() {
        ServerUser match = Lobby.getInstance().findMatch(user);
        System.out.println(match);
        if (match == null) {
            Lobby.getInstance().add(user);
        } else {
            
            try {
                user.getOutputStream().writeObject("SER");
                user.getOutputStream().writeObject(match);
            } catch(IOException e) {
                System.out.println("IO Error");
                return;
            }
            
            try {
                match.getOutputStream().writeObject("CLI");
                match.getOutputStream().writeObject(user);
                match.getOutputStream().writeObject(user.getSocket().getInetAddress());
            } catch(IOException e) {
                System.out.println("IO Error");
                return;
            }
            
            try {
                user.getSocket().close();
                match.getSocket().close();
            } catch (IOException e) {
                return;
            }
        }
    }
    
    public void start() {
        run();
    }
}
