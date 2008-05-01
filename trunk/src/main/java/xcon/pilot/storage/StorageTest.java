package xcon.pilot.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;

import xcon.config.GuiceModule;

// import com.google.inject.Guice;
// import com.google.inject.Inject;

public class StorageTest {

    Map<String, Command> map = new HashMap<String, Command>();
    private static final Logger LOG = Logger.getLogger(StorageTest.class);
    private Storage storage;

    // dependency injection
    // @Inject
    public void setStorage(Storage storage) {
        System.out.println("Using Storage implementation: "
            + storage.getClass().getName());
        this.storage = storage;
    }

    public void init() {
        HelpClass help = new HelpClass(storage);
        ImplClass impl = new ImplClass(storage);
        CapacityClass capacity = new CapacityClass(storage);
        DumpClass dump = new DumpClass(storage);
        StoreClass store = new StoreClass(storage);
        ReadClass read = new ReadClass(storage);

        map.put("help", help);
        map.put("cap", capacity);
        map.put("impl", impl);
        map.put("dump", dump);
        map.put("store", store);
        map.put("read", read);

    }

    public void run() {
        Scanner s;
        String command_value = null;

        do {
            String commandLine = readCommand();
            s = new Scanner(commandLine);
            if (s.hasNext()) {
                command_value = s.next();
            }
            if (map.containsKey(command_value)) {
                Command command = (Command) map.get(command_value);
                command.execute(s);
            }
            else {
                System.out.println("verkeerde commando getypt");
                Command command = map.get("help");
                command.execute(s);
            }
        }
        while (!"quit".equals(command_value));
        System.out.println("bye");
    }

    public static void main(String[] args) {

        System.out.println("Testing file storage implementation");
        StorageTest testObject = new StorageTest();
        testObject.setStorage(new FileStorage());
        /*
         * Guice.createInjector(new GuiceModule()).getInstance(
         * StorageTest.class);
         */

        testObject.init();
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
