package thread_test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import remote.IfaceRemoteServer;

public class MultiThreadClientTest {
	
	class WriterTask implements Callable<Boolean> {
		private String fileName = null;
		private byte[] data = null;
		private IfaceRemoteServer remote = null;
		
		public WriterTask(IfaceRemoteServer remote, String fileName, String text) {
			this.fileName = fileName;
			this.data = text.getBytes();
			this.remote = remote;
		}
		
		public Boolean call() {
			try {
				remote.write(this.fileName, this.data, this.data.length );
			} catch (Exception e) {
				return false;
			}
			
			return true;
		}
	}
    
	public static void runThreads(IfaceRemoteServer remote) {
		MultiThreadClientTest test = new MultiThreadClientTest();
		ArrayList<WriterTask> tasks = new ArrayList<WriterTask>();
		for (int i = 0; i< 100; i++) {
			String str = new String(new char[300]).replaceAll("\0", String.format(" %d ", i));
			tasks.add (test.new WriterTask(remote, "test.txt", str));
		}
		ExecutorService executorPool = Executors.newFixedThreadPool(100);
		try {
			executorPool.invokeAll(tasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
        String hostname = "localhost";
        try {
            String rname = "//" + hostname + ":" + Registry.REGISTRY_PORT
                    + "/remote";
            IfaceRemoteServer remote = (IfaceRemoteServer) Naming.lookup(rname);
            runThreads(remote);
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
