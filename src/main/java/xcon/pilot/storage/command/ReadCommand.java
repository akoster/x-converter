package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class ReadCommand extends Command {
  
    public void execute(Scanner s) {
        handleRead(s);
    }

    private void handleRead(Scanner s) {
        if (s.hasNext()) {
            String key = s.next();
            Object value = Storage.getImplementation().read(key);
            System.out.println("Read: " + key + "=" + value);
        }
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<key> - reads the value for the key";
    }     
}
