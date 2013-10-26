package remote;

import java.rmi.Naming;
import java.rmi.registry.Registry;

public class StartRemoteObject {
    public static void main(String args[]) {
        try {
            /* Create ("start") the object which has the remote method */
            RemoteServer robject = new RemoteServer();
            /* Register the object using Naming.rebind(...) */
            String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
            Naming.rebind(rname, robject);
            System.out.println("\u001B[32mServer started\u001B[0m");
        } catch (Exception e) {
            System.out
                    .println("\u001B[31mHey, an error occurred at Naming.rebind");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("\u001B[0m");
        }
    }
}
