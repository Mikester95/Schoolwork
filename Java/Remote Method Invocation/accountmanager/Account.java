package accountmanager;

import java.util.*;
import java.rmi.*; 
import java.rmi.server.*;

public class Account extends UnicastRemoteObject implements AccountPublic {
    private int accountId;
    private String password;
    private int amount;
    
    Account(int accountId, String password) throws RemoteException {
        this.accountId = accountId;
        this.password = password;
        this.amount = 0;
    }
    
    String getPassword() {
        return this.password;
    }
    
    public synchronized void deposit(int amount) {
        this.amount += amount;
    }
    
    public synchronized boolean withdraw(int amount) {
        if (this.amount < amount) {
            return false;
        } else {
            this.amount -= amount;
            return true;
        }
    }
    
    public int inquiry() {
        return amount;
    }
}
