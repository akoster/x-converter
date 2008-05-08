package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;

public class DumpCommand extends Command {

    public void execute(Scanner s) {
        System.out.println("dump class ");
        System.out.println(storage.dumpContents());
    }

    public String showHelp() {
        return "dumps the contents of the storage";
    }
}
