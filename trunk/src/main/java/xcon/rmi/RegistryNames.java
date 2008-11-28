package xcon.rmi;


import java.rmi.registry.*;
import java.rmi.*;

public class RegistryNames
{
    public static void main(String[] args)
    {
        if(args.length > 1)
        {
            System.out.println("USAGE: java RegistryNames [port number]");
            return;
        }
        String[] names;
        int portNumber = (args.length == 1) ? 
            Integer.parseInt(args[0]) : Registry.REGISTRY_PORT;
            
        try
        {
            names = LocateRegistry.getRegistry(portNumber).list();
        }
        catch(RemoteException e)
        {
            System.out.println("Cannot connect to RMI registry on port " + 
                               portNumber);
            return;
        }
        System.out.println("RMI registry is running on port " + portNumber +
                           ((portNumber == Registry.REGISTRY_PORT) ?
                            " (the default port number)" : 
                            " (a user defined port number)"));
        System.out.println((names.length == 0) ? "NO names are in use." :
                           "The following name" + ((names.length == 1) ? 
                           " is" : "s are") + " in use:");
        for(int i = 0; i < names.length; i++)
        {
            System.out.println(names[i]);
        }
    }
}
