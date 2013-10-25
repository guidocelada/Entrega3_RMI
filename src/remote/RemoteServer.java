package remote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServer extends UnicastRemoteObject implements IfaceRemoteServer {
    
    public RemoteServer() throws RemoteException {
        super();
    }
    
    /*
    * Given a name of a file, an offset, and a number of bytes to read
    * returns the data in the file starting from that offset,
    * and the number of bytes that effectively read.
    */
    //TODO: return number of bytes???
    public byte[] read(String fileName, int position, int bytesToRead)
            throws RemoteException {
        FileInputStream in = null;
        
        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.print("Sorry, " + fileName + "does not exist in our filesystem");
        }
        byte[] buffer = new byte[1024];
        try {
            in.read(buffer, position, bytesToRead);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        return null;
    }

    /*
    * Given a name of a file, a number of bytes to write
    * and a buffer with the data, writes the data into a file.
    * if the file exists, it appends the data to the final,
    * if not, it creates the file with the data.
    * Returns the number of bytes that effectively write.
    */
    public void write(String fileName, int position, byte[] buffer)
            throws RemoteException {
        
    }

}
