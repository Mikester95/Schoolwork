package user;

import java.io.*;
import java.net.*;

public class ServerUser extends User {
    private transient Socket socket;
    private transient ObjectOutputStream outputStream;
    private transient ObjectInputStream inputStream;
    
    public ServerUser(User user, Socket hisSocket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        super(user);
        this.socket = hisSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
    
    public ObjectInputStream getInputStream() {
        return inputStream;
    }
}
