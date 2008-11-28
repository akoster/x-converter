package xcon.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        String host = args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) registry.lookup("hello"); 
            String response = stub.sayHello();
            System.out.println("response" + response );
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
