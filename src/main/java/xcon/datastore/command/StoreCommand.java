package xcon.datastore.command;

import java.util.Scanner;

import xcon.datastore.Command;
import xcon.datastore.Storage;

public class StoreCommand extends Command {

    public void execute(Scanner s) {
        handleStore(s);
    }

    private void handleStore(Scanner s) {

        handling: {
            if (s.hasNext()) {
                String key = s.next();
                if (s.hasNext()) {

                    String value = s.nextLine();
                    if (value != null) {

                        value = value.trim();
                        boolean success =
                            Storage.getImplementation().store(key, value);
                        if (success) {
                            System.out.println("Stored: " + key + "=" + value);
                        }
                        break handling;
                    }
                }
            }
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<key> <value> - stores the value under the key";
    }
}
