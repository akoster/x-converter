package xcon.pilot.storage;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class HelpClass implements Command {

    private static final Logger LOG = Logger.getLogger(HelpClass.class);
    Storage storage;

    public HelpClass() {
        
    }
    public HelpClass(Storage storage) {
        this.storage = storage;
    }

    private void handleHelp() {
        System.out.println("Commands:");
        System.out.println("set <key>: setCapacity");
        System.out.println("read <key>: reads the value for the key");
        System.out.println("store <key <value> : stores the value under the key");
        System.out.println("dump: dumps the contents of the storage");
        System.out.println("impl <implementation>: change the storage implementation");
        System.out.println("quit: exits the application");
        System.out.println("help: shows this help sheet");

    }

    public void execute(Scanner s) {
        handleHelp();

    }

}
