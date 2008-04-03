package xcon.pilot.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StorageTest {

    private enum Command {

        READ("read"),
        STORE("store"),
        DUMP("dump"),
        QUIT("quit"),
        HELP("help"),
        CAP("cap");

        public String name;

        private Command(String name) {
            this.name = name;
        }

        public boolean is(String command) {
            return this.name.equals(command);
        }

    }

    private Storage storage;

    // dependency injection, for example with Spring of Google Guice
    @Inject
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void run() {

        handleHelp();

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
                handleStore(s);
            }
            if (Command.DUMP.is(command)) {
                System.out.println(storage.dumpContents());
            }
            if (Command.HELP.is(command)) {
                handleHelp();
            }
            if (Command.CAP.is(command)) {
                handleCap(s);
            }
        }
        while (!Command.QUIT.is(command));

        System.out.println("Bye...");
    }

    private void handleHelp() {
        System.out.println("Commands:");
        System.out.println("set <key>: setCapacity");
        System.out.println("read <key>: reads the value for the key");
        System.out.println("store <key <value> : stores the value under the key");
        System.out.println("dump: dumps the contents of the storage");
        System.out.println("quit: exits the application");
        System.out.println("help: shows this help sheet");

    }

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

    public static void main(String[] args) {

        System.out.println("Testing file storage implementation");
        StorageTest testObject = new StorageTest();
//       testObject.setStorage(new FileStorage());
        testObject.run();

        // manual 'injection'
        // testObject.setStorage(new FileStorage());
        // testObject.doTest();

        // System.out.println("Testing HashMap storage implementation");
        // manual 'injection'
        // testObject.setStorage(new HashMapStorage());
        // testObject.doTest();
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
