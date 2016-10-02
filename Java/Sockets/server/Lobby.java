package server;

import java.net.*;
import java.util.*;
import utility.*;
import user.*;

class Lobby {
    private LinkedList<ServerUser> userList;
    
    private Lobby() {
        userList = new LinkedList<ServerUser>();
    }
    private static Lobby instance = null;
    public static Lobby getInstance() {
        if (instance == null) {
            instance = new Lobby();
        }
        return instance;
    }
    
    public synchronized void add(ServerUser newUser) {
        userList.add(newUser);
    }
    
    public synchronized ServerUser findMatch(ServerUser candidate) {
        for (int i = 0; i < userList.size(); ++i) {
            if (!Server.isConnected(userList.get(i))) {
                userList.remove(i);
                --i;
            } else {
                if (User.areCompatible(candidate, userList.get(i))) {
                    ServerUser match = userList.get(i);
                    userList.remove(i);
                    --i;
                    return match;
                }
            }
        }
        
        return null;
    }
}
