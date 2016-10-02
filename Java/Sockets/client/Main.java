package client;

import java.io.*;

public class Main { 
    public static void main(String args[]) throws IOException {
        Client client = Client.getInstance();
        client.registerUser();
        client.continuousPlay();
    }
}
