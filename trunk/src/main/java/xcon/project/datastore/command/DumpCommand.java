package xcon.project.datastore.command;

import java.util.Scanner;

import xcon.project.datastore.Command;
import xcon.project.datastore.Storage;

public class DumpCommand extends Command {

    public void execute(Scanner s) {
        System.out.println("Dump:");
        System.out.println(Storage.getImplementation().dumpContents());
    }

    public String showHelp() {
        return "dumps the contents of the storage";
    }
}
