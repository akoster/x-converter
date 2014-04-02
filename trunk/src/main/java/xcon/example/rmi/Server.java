package xcon.example.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

    public static void main(String[] args) {

        try {

            Server server = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(server, 0);
            // bind the remote object to a registery
            //LocateRegistry.createRegistry(2009);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sayHello() {
        return "hello world";
    }
}
