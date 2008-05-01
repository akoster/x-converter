package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class ReadCommand implements Command {

    Storage storage;

    public ReadCommand(Storage storage) {
        this.storage = storage;

    }

    public void execute(Scanner s) {
        handleRead(s);
    }

    private void handleRead(Scanner s) {
        if (s.hasNext()) {
            String key = s.next();
            Object value = storage.read(key);
            System.out.println("Stored: " + key + "=" + value);
        }
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    @Override
    public String showHelp() {
        return "<key> - reads the value for the key";
    }
    
    
}
