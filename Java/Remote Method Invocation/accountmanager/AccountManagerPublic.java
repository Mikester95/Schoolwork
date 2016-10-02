package accountmanager;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountManagerPublic extends Remote {
    public abstract int register(String password) throws RemoteException;
    
    public abstract AccountPublic login(int accountId, String password) throws RemoteException;
}
