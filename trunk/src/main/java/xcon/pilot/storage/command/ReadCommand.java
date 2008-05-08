package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;

public class ReadCommand extends Command {
  
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

    public String showHelp() {
        return "<key> - reads the value for the key";
    }     
}
