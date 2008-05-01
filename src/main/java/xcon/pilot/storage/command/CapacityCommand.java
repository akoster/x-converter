package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class CapacityCommand implements Command {

    Storage storage;

    public CapacityCommand(Storage storage) {
        this.storage = storage;
    }

    public void execute(Scanner s) {
        handleCap(s);
    }

    private void handleCap(Scanner s) {
        if (s.hasNext()) {
            int capacity = Integer.parseInt(s.next());
            storage.setCapacity(capacity);
            System.out.println("Capacity =" + capacity);
        }
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    @Override
    public String showHelp() {
        return "<key> - sets the storage capacity";
    }

}