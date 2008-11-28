package xcon.pilot.storage.command;

import java.util.Scanner;
import org.apache.log4j.Logger;
import xcon.atm.swing.SlotPanel;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class CapacityCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CapacityCommand.class);
    
    public void execute(Scanner s) {
        handleCap(s);
    }

    private void handleCap(Scanner s) {
        if (s.hasNext()) {
            int capacity = Integer.parseInt(s.next());
            Storage.getImplementation().setCapacity(capacity);
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
