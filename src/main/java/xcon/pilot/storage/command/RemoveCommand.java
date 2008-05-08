package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class RemoveCommand extends Command {

    public void execute(Scanner s) {
        handleRemove(s);
    }

    private void handleRemove(Scanner s) {

        if (s.hasNext()) {

            String key = s.next();
            Storage.getImplementation().delete(key);
            System.out.println("Deleted: " + key);
        }

        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<key> - removes the value for the given key";
    }
}
