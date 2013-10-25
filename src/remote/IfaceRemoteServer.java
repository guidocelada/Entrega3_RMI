package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Implementors of this interface can be 
 * referenced and invoked by a remote machine.
 * 
 * @author Celada, Soria
 *
 */
public interface IfaceRemoteServer extends Remote {
    
    public byte[] read(String fileName, int position, int bytesToRead) throws RemoteException;
    
    public int write(String fileName, byte[] buffer, int bytesLength) throws RemoteException;
}
