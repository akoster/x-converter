package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;

public class StoreCommand extends Command {
    
    public void execute(Scanner s) {
        handleStore(s);
    }

    private void handleStore(Scanner s) {
        
        if (s.hasNext()) {
            String key = s.next();
            String value = s.nextLine();
            storage.store(key, value);
        }
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<key> <value> - stores the value under the key";
    }
}
