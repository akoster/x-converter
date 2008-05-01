package xcon.pilot.storage.command;

import java.io.File;
import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class RemoveCommand implements Command {

    Storage storage;

    public RemoveCommand(Storage storage) {
        this.storage = storage;
    }

    public void execute(Scanner s) {
        handleRemove(s);
    }

    private void handleRemove(Scanner s) {

        if (s.hasNext()) {

            String key = s.next();
            storage.delete(key);
        }

        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    @Override
    public String showHelp() {
        return "<key> - removes the value for the given key";
    }
}
