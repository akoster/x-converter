package xcon.pilot.storage;

import java.util.Scanner;

public class CapacityClass implements Command {

    Storage storage;

    public CapacityClass(Storage storage) {
        this.storage = storage;
    }

    public CapacityClass() {
        
    }

    public void execute(Scanner s) {
        handleCap (s); 
    }
    private void handleCap(Scanner s) {
        if (s.hasNext()) {
            int capacity = Integer.parseInt(s.next());
            storage.setCapacity(capacity);
            System.out.println("Capacity =" + capacity);
        }
        else {
            System.out.println("Syntax: cap <capacity>");
        }
    }

}
