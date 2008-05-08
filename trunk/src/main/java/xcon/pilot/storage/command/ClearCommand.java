package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;

public class ClearCommand extends Command {

    public void execute(Scanner s) {
        handle(s);
    }

    private void handle(Scanner s) {

        if (s.hasNext()) {

            String type = s.next();
            if ("values".equals(type)) {
                storage.clearValues();
            }
            else if ("all".equals(type)) {
                storage.clearAll();
            }
            else {
                System.out.println("syntax: " + showHelp());
            }
        }
        else {
            System.out.println("syntax: " + showHelp());
        }
    }

    public String showHelp() {
        return "<all|values> all=removes all data, values=removes all values";
    }
}
