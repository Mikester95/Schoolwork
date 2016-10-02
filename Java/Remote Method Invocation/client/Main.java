package client;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import accountmanager.AccountManagerPublic;
import accountmanager.AccountPublic;

public class Main {
    
    public static Scanner in;
    
    public static void operate(AccountPublic account) throws Exception {
        while (true) {
            boolean ok = true;
            System.out.println("Type 1 to deposit money");
            System.out.println("Type 2 to withdraw money");
            System.out.println("Type 3 to get account total");
            System.out.println("Type 4 to stop");
            String reply = in.next();
            if (reply.equals("1")) {
                System.out.println("What amount would you like to deposit?");
                int amount = Integer.parseInt(in.next());
                account.deposit(amount);
            } else if (reply.equals("2")) {
                System.out.println("What amount would you like to withdraw?");
                int amount = Integer.parseInt(in.next());
                ok = account.withdraw(amount);
                if (!ok) {
                    System.out.println("You do not have that much money in your account!");
                }
            } else if (reply.equals("3")) {
                int amount = account.inquiry();
                System.out.format("You have a total of %d units of currency int this account.\n", amount);
            } else if (reply.equals("4")) {
                break;
            } else {
                System.out.println("Invalid operation");
                ok = false;
            }
            
            if (ok) {
                System.out.println("Operation successful");
            }
        }
    }
    
    private static void authenticate(AccountManagerPublic accountManager) throws Exception {
        while(true) {
            System.out.println("Type 1 to register a new account.");
            System.out.println("Type 2 to log in to an existing account.");
            String reply = in.next();
            if (reply.equals("1")) {
                System.out.println("Enter a password. Use anything except whitespaces.");
                String password = in.next();
                int accountId = accountManager.register(password);
                System.out.format("Your new account id is %d. Remember it!\n", accountId);
            } else if (reply.equals("2")) {
                System.out.println("Enter your accountID.");
                int accountId = Integer.parseInt(in.next());
                System.out.println("Enter your password.");
                String password = in.next();
                AccountPublic account = accountManager.login(accountId, password);
                
                if (account == null) {
                    System.out.println("Wrong Account Id and Password combination");
                } else {
                    operate(account);
                }
            }
        }
    }
    
    public static void main(String args[]) {
        try {
            in = new Scanner(System.in);
            
            final String name = "AccountManagerPublic";
            final String IP = "localhost";
            final int port = 63000;
            Registry registry = LocateRegistry.getRegistry(IP, port);
            AccountManagerPublic accountManager = (AccountManagerPublic) registry.lookup(name);
            
            authenticate(accountManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}