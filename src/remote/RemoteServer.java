package remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Remote server that can read and write data from the filesystem
 * 
 * @author Celada, Soria
 * 
 */
public class RemoteServer extends UnicastRemoteObject implements
        IfaceRemoteServer {
    private static final long serialVersionUID = 1L;

    public RemoteServer() throws RemoteException {
        super();
    }

    /*
     * Given a name of a file, an offset, and a number of bytes to read returns
     * the data in the file starting from that offset, and the number of bytes
     * that effectively read.
     */
    public byte[] read(String fileName, int position, int bytesLength)
            throws RemoteException, FileNotFoundException {
        FileInputStream inputFile = null;
        byte[] buffer = new byte[bytesLength];

        // Try to open the file
        try {
            inputFile = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            StringBuffer errorString = new StringBuffer();
            errorString.append("\u001B[31mA client asked for the file \"");
            errorString.append(fileName);
            errorString
                    .append("\" wich doesn't exists in the filesystem.\u001B[0m");
            System.out.println(errorString.toString());
            throw new FileNotFoundException();
        }
        // Read the file
        try {
        	if (inputFile.skip(position) != position)
        		return null;
            int readBytes = inputFile.read(buffer, 0, bytesLength);
            // Truncate the buffer to have the correct size
            if (readBytes != bytesLength)
                buffer = Arrays.copyOf(buffer, readBytes);
        } catch (IndexOutOfBoundsException e) {
            System.out
                    .println("\u001B[31mPosition pointer it's out of bounds\u001B[0m");
            throw new IndexOutOfBoundsException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    return null;
                }
            }
        }
        System.out.println("\u001B[32mRead request: done \u001B[0m");
        return buffer;
    }

    /*
     * Given a name of a file, a number of bytes to write and a buffer with the
     * data, writes the data into a file. if the file exists, it appends the
     * data to the final, if not, it creates the file with the data. Returns the
     * number of bytes that effectively write.
     */
    public int write(String fileName, byte[] buffer, int bytesLength)
            throws RemoteException {
        FileOutputStream outputFile = null;
        int bytesWritten = 0;
        try {
            outputFile = new FileOutputStream(fileName, true);
            for (byte aByte : buffer) {
                outputFile.write(aByte);
                bytesWritten++;
            }

        } catch (IOException e) {
            System.out.println("\u001B[31mUnhandled Input/Output Exception:");
            System.out.println(e.getMessage());
            System.out.println("\u001B[0m");
        } finally {
            if (outputFile != null) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                    return bytesWritten;
                }
            }
        }
        System.out.println("\u001B[32mWrite request: done \u001B[0m");
        return bytesWritten;
    }
}
