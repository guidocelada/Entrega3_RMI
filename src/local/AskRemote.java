package local;


import java.rmi.Naming;
import java.rmi.registry.Registry;

import remote.IfaceRemoteServer;

public class AskRemote {
    public static void main(String[] args) {
        /* Look for hostname and msg length in the command line */
        if (args.length != 1) {
            System.out.println("1 argument needed: (remote) hostname");
            System.exit(1);
        }
        try {
            String rname = "//" + args[0] + ":" + Registry.REGISTRY_PORT
                    + "/remote";
            IfaceRemoteServer remote = (IfaceRemoteServer) Naming.lookup(rname);
            byte[] buffer = remote.read("/Users/guidojosecelada/test.txt",1,1);
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
