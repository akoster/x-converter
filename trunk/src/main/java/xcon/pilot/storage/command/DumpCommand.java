package xcon.pilot.storage.command;

import java.util.Scanner;
import xcon.pilot.storage.Command;
import xcon.pilot.storage.Storage;

public class DumpCommand implements Command {

    Storage storage;
    public DumpCommand(Storage storage) {
      this.storage = storage ; 
    }

    public void execute(Scanner s) {
        System.out.println("dump class ");
        System.out.println(storage.dumpContents());
    }

    @Override
    public String showHelp() {
        return "dumps the contents of the storage";
    }
}
