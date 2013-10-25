package local;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import remote.IfaceRemoteServer;

public class AskRemote {
	
	public static void copyFile(IfaceRemoteServer remote, String inputFile,
			String outputFile, int position, int length) throws RemoteException
	{
		// Getting File Data
		System.out.print("Getting File Data... ");
		byte[] buffer = remote.read(inputFile, position, length);
		System.out.println(String.format("%d bytes read", buffer.length));
		
		// Putting Data into File
		System.out.print("Writting data into File... ");
		int bytesWritten = remote.write(outputFile, buffer, buffer.length);
		System.out.println(String.format("%d bytes written", bytesWritten));
		
		// Info
		if (bytesWritten == buffer.length) 
			System.out.println("The file has been copied succesfully :)");
		else 
			System.out.println("Error Found! The written bytes mismatches the read bytes :(");
		
	}
    
	public static void main(String[] args) {
        /* Look for hostname and msg length in the command line */
        if (args.length < 5) {
            System.out.println("3 argument needed: (remote) hostname inputFile outputFile position length");
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
            System.out.println("Done");
        }
        catch (RemoteException e) {
        	System.out.println("ERROR: There was a problem in the remote method call: ");
        	System.out.println(e.getMessage());
        	System.exit(1);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
