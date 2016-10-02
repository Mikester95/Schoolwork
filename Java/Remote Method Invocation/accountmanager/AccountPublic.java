package accountmanager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountPublic extends Remote {
    public abstract void deposit(int amount) throws RemoteException;
    
    public abstract boolean withdraw(int amount) throws RemoteException;
    
    public abstract int inquiry() throws RemoteException;
}
