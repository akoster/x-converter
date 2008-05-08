package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class ClearCommand extends Command {

    public void execute(Scanner s) {
        handle(s);
    }

    private void handle(Scanner s) {

        handling: {
            if (s.hasNext()) {

                String type = s.next();
                if ("values".equals(type)) {
                    Storage.getImplementation().clearValues();
                    System.out.println("Cleared values");
                    break handling;
                }
                else if ("all".equals(type)) {
                    Storage.getImplementation().clearAll();
                    System.out.println("Cleared all");
                    break handling;
                }
            }
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<all|values> all=removes all data, values=removes all values";
    }
}
