package xcon.pilot.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.apache.log4j.Logger;
import xcon.config.GuiceModule;
//import com.google.inject.Guice;
//import com.google.inject.Inject;

public class StorageTest {
	
	HelpClass helpClass = new HelpClass(); 
	private static final Logger LOG = Logger.getLogger(StorageTest.class);

    private enum Command {

        READ("read"),
        STORE("store"),
        DUMP("dump"),
        QUIT("quit"),
        HELP("help"),
        CAP("cap"),
        IMPL("impl");

        public String name;

        private Command(String name) {
            this.name = name;
        }

        public boolean is(String command) {
            return this.name.equals(command);
        }

    }

    private Storage storage;

    // dependency injection
   // @Inject
    public void setStorage(Storage storage) {
        System.out.println("Using Storage implementation: "
            + storage.getClass().getName());
        this.storage = storage;
    }

    public void run() {

        helpClass.run(""); 

        Scanner s;
        String command = null;
        do {

            System.out.print("command:");
            String commandLine = readCommand();
            s = new Scanner(commandLine);
            if (s.hasNext()) {
                command = s.next();
            }
            if (Command.READ.is(command)) {
                handleRead(s);
            }
            if (Command.STORE.is(command)) {
                StartInterface storeClass = new StoreClass(); 
            	storeClass.run(command);
                
            }
            if (Command.DUMP.is(command)) {
                System.out.println(storage.dumpContents());
            }
            if (Command.HELP.is(command)) {
               HelpClass helpClass = new HelpClass(); 
               helpClass.run(""); 
            }
            if (Command.CAP.is(command)) {
                handleCap(s);
            }
            if (Command.IMPL.is(command)) {
                handleImpl(s);
            }
        }
        while (!Command.QUIT.is(command));

        System.out.println("Bye...");
    }

/*    private void handleHelp() {
        System.out.println("Commands:");
        System.out.println("set <key>: setCapacity");
        System.out.println("read <key>: reads the value for the key");
        System.out.println("store <key <value> : stores the value under the key");
        System.out.println("dump: dumps the contents of the storage");
        System.out.println("impl <implementation>: change the storage implementation");
        System.out.println("quit: exits the application");
        System.out.println("help: shows this help sheet");

    }*/

    private void handleRead(Scanner s) {
        if (s.hasNext()) {
            String key = s.next();
            System.out.println(key + "=" + storage.read(key));
        }
        else {
            System.out.println("Syntax: read <key>");
        }
    }

    private void handleStore(Scanner s) {
        if (s.hasNext()) {
            String key = s.next();
            String value = "";
            while (s.hasNext()) {
                value += s.next();
            }
            storage.store(key, value);
            System.out.println("Stored: " + key + "=" + value);
        }
        else {
            System.out.println("Syntax: store <key> <value>");
        }
    }

    private void handleCap(Scanner s) {
        if (s.hasNext()) {
            int capacity = Integer.parseInt(s.next());
            storage.setCapacity(capacity);
            System.out.println("Capacity =" + capacity);
        }
        else {
            System.out.println("Syntax: cap <capacity>");
        }
    }

    private void handleImpl(Scanner s) {
        if (s.hasNext()) {
            String impl = s.next();
            if ("file".equals(impl)) {
                setStorage(new FileStorage());
            }
            else if ("map".equals(impl)) {
                setStorage(new HashMapStorage());
            }
            else {
                System.out.println("impl: possible implementations 'file', 'map'");
            }
        }
        else {
            System.out.println("Syntax: impl <file|map>");
        }
    }

    public static void main(String[] args) {

        System.out.println("Testing file storage implementation");
        StorageTest testObject = new StorageTest(); 
        testObject.setStorage(new FileStorage()); 
          /*  Guice.createInjector(new GuiceModule()).getInstance(
                    StorageTest.class);*/
        testObject.run();
    }

    public String readCommand() {

        // open up standard input
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        String command = null;
        try {
            command = br.readLine();
        }
        catch (IOException ioe) {
            System.out.println("IO error trying to read your name!");
            System.exit(1);
        }
        return command;
    }

}
