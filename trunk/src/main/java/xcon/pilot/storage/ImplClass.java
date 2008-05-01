package xcon.pilot.storage;

import java.util.Scanner;

public class ImplClass implements Command {

    Storage storage;

    public ImplClass(Storage storage) {
        this.storage = storage;
    }

    public ImplClass() {

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
                StorageTest storageTest= new StorageTest();  
                storageTest.setStorage( new FileStorage());
            }
            else if ("map".equals(impl)) {
            StorageTest storageTest= new StorageTest();  
            storageTest.setStorage( new HashMapStorage());
            } 
            else {
                System.out.println("impl: possible implementations 'file', 'map'");
            }
        } 
        else {
            System.out.println("Syntax: impl <file|map>");
        }
    }

}
