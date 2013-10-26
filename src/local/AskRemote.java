package local;

import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import remote.IfaceRemoteServer;

public class AskRemote {

    public static void copyFile(IfaceRemoteServer remote, String inputFile,
            String outputFile, int position, int length)
            throws RemoteException, FileNotFoundException {
        byte[] buffer;
        // Getting File Data
        System.out.println("Getting File Data... ");

        buffer = remote.read(inputFile, position, length);

        System.out.println(String.format("%d bytes read", buffer.length));

        // Putting Data into File
        System.out.println("Writting data into File... ");
        int bytesWritten = remote.write(outputFile, buffer, buffer.length);
        System.out.println(String.format("%d bytes written", bytesWritten));

        // Info
        if (bytesWritten == buffer.length)
            System.out
                    .println("\u001B[32mThe file has been copied succesfully :)\u001B[0m");
        else
            System.out
                    .println("\u001B[31mError Found! The written bytes mismatches the read bytes :(\u001B[0m");

    }

    public static void main(String[] args) {
        /* Look for hostname and msg length in the command line */
        if (args.length < 5) {
            System.out
                    .println("This program reads data from a file, and then copies the same data into another file.\n");
            System.out
                    .println("3 argument needed: remote_hostname path_of_file_to_read path_of_file_copy position length");
            System.exit(0);
        }
        String hostname = args[0];
        String inputFile = args[1];
        String outputFile = args[2];
        int position = Integer.parseInt(args[3]);
        int length = Integer.parseInt(args[4]);

        try {
            String rname = "//" + args[0] + ":" + Registry.REGISTRY_PORT
                    + "/remote";
            IfaceRemoteServer remote = (IfaceRemoteServer) Naming.lookup(rname);
            AskRemote.copyFile(remote, inputFile, outputFile, position, length);
        } catch (RemoteException e) {
            System.out
                    .println("\u001B[31mERROR: There was a problem in the remote method call: ");
            System.out.println(e.getMessage());
            System.out.println("\u001B[0m");
            System.exit(1);
        } catch (FileNotFoundException f) {
            System.out.println("\u001B[31mA Sorry, the file " + inputFile
                    + " does not exists :( \u001B[0m");
        } catch (IndexOutOfBoundsException e) {
            System.out
                    .println("\u001B[31mPosition pointer it's out of bounds\u001B[0m");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
