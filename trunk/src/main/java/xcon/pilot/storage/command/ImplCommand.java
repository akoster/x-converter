package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;
import xcon.pilot.storage.DataStore;
import xcon.pilot.storage.impl.FileStorage;
import xcon.pilot.storage.impl.HashMapStorage;

public class ImplCommand implements Command {

    Storage storage;

    public ImplCommand(Storage storage) {
        this.storage = storage;
    }

    public void execute(Scanner s) {
        System.out.println("in impl class");
        handleImpl(s);
    }

    private void handleImpl(Scanner s) {
        
        if (s.hasNext()) {
            String impl = s.next();
            if ("file".equals(impl)) {
                System.out.print("in methode handle imple");
                DataStore storageTest= new DataStore();  
                storageTest.setStorage( new FileStorage());
            }
            else if ("map".equals(impl)) {
            DataStore storageTest= new DataStore();  
            storageTest.setStorage( new HashMapStorage());
            } 
            else {
                System.out.println("syntax: " + showHelp());
            }
        } 
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    @Override
    public String showHelp() {
        return "<file|map> - sets the implementation to file or hashmap based storage";
    }
}
