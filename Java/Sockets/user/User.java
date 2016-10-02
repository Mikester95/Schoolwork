package user;

import java.io.*;

public class User implements Serializable{
    
    private String name;
    private int rating;
    
    public User(String name) {
        this.name = name;
        this.rating = 1500;
    }
    
    public User(User user) {
        this(user.name);
    }
    
    public String getName() {
        return name;
    }
    
    public static boolean areEqual(User a, User b) {
        return a.getName().equals(b.getName());
    }
    
    public static boolean areCompatible(User a, User b) {
        return true;
    }
    
    public void modifyRating(int value) {
        rating += value;
    }
    
    public int getRating() {
        return rating;
    }
}
