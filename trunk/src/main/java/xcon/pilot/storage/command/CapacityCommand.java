package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;

public class CapacityCommand extends Command {

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

    public String showHelp() {
        return "<key> - sets the storage capacity";
    }

}
