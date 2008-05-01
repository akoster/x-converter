package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class StoreCommand implements Command {

    Storage storage;

    public StoreCommand(Storage storage) {
        this.storage = storage;

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
            System.out.println("syntax: " + showHelp());
        }
    }

    @Override
    public String showHelp() {
        return "<key> <value> - stores the value under the key";
    }
}
