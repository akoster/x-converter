package xcon.pilot.storage;

import java.util.Scanner;

public class DumpClass implements Command {

    Storage storage;
    public DumpClass(Storage storage) {
      this.storage = storage ; 
    }
    public DumpClass() {
        
    }

    public void execute(Scanner s) {
        System.out.println("dump class ");
        System.out.println(storage.dumpContents());
    }

}
