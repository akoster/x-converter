package xcon.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello{

    public static void main(String[] args) {
        
        try {
            Server server = new Server();
            Hello ref = (Hello) UnicastRemoteObject.exportObject(server, 5051);
            // bind the remote object to a registery 
            Registry registry = LocateRegistry.getRegistry(2001); 
            registry.bind("hello", ref);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String sayHello() throws RemoteException {
        return "hello world" ; 
    }
}
