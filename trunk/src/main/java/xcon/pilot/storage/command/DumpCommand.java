package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class DumpCommand extends Command {

    public void execute(Scanner s) {
        System.out.println("Dump:");
        System.out.println(Storage.getImplementation().dumpContents());
    }

    public String showHelp() {
        return "dumps the contents of the storage";
    }
}
