package accountmanager;

import java.util.*;
import java.rmi.*; 
import java.rmi.server.*;

public class AccountManager extends UnicastRemoteObject implements AccountManagerPublic {
    private ArrayList<Account> accounts;
    
    public AccountManager() throws RemoteException {
        super();
        accounts = new ArrayList<Account>();
    }
    
    public synchronized int register(String password) throws RemoteException{
        int accountId = accounts.size() + 1;
        accounts.add(new Account(accountId, password));
        System.out.format("New account with accoutntId %d and password %s \n", accounts.size(), password);
        return accountId;
    }
    
    public synchronized AccountPublic login(int accountId, String password) {
        if (accounts.get(accountId - 1).getPassword().equals(password)) {
            return (AccountPublic) accounts.get(accountId - 1);
        } else {
            return null;
        }
    }
}
