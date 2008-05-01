package xcon.pilot.storage;

import java.util.Scanner;

public class StoreClass implements Command {

    Storage storage;

    public StoreClass(Storage storage) {
        this.storage = storage;

    }

    public StoreClass() {

    }

    public void execute(Scanner s) {
        handleStore(s);
    }
    
    private void handleStore(Scanner s) {
        if (s.hasNext()) {
            String key = s.next();
            String value = "";
            while (s.hasNext()) {
                value += s.next();
            }
            storage.store(key, value);
            System.out.println("Stored: " + key + "=" + value);
        }
        else {
            System.out.println("Syntax: store <key> <value>");
        }
    }
}
