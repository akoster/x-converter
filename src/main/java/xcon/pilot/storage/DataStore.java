package xcon.pilot.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.apache.log4j.Logger;
import xcon.config.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DataStore {

    private static final Logger LOG = Logger.getLogger(DataStore.class);

    Map<String, Command> commandMap;
    private Storage storage;

    public static void main(String[] args) {

        System.out.println("Testing file storage implementation");
        DataStore testObject = new DataStore();
        Injector injector = Guice.createInjector(new GuiceModule());
        injector.injectMembers(testObject);
        testObject.run();
    }

    public void run() {

        Scanner s;
        String cmd = null;
        boolean quit = false;
        do {
            String commandLine = readCommandLine();
            s = new Scanner(commandLine);
            if (s.hasNext()) {
                cmd = s.next();
            }
            if ("quit".equalsIgnoreCase(cmd)) {
                quit = handleQuit();
            }
            else {
                Command command = getCommand(cmd);
                if (command == null) {
                    displayHelpSheet();
                }
                else {
                    command.execute(s);
                }
            }
        }
        while (!quit);
        System.out.println("bye");
    }

    private Command getCommand(String cmd) {
        if (commandMap == null) {
            init();
        }
        return commandMap.get(cmd);
    }

    public void init() {

        commandMap = new HashMap<String, Command>();
        ResourceBundle commands = ResourceBundle.getBundle("commands");
        Enumeration<String> commandKeys = commands.getKeys();
        while (commandKeys.hasMoreElements()) {

            String key = commandKeys.nextElement();
            Command cmd;
            try {
                String commandClass = commands.getString(key);
                cmd =
                    (Command) Class.forName(commandClass).getDeclaredConstructor(
                            Storage.class).newInstance(storage);
                commandMap.put(key, cmd);
            }
            catch (Exception e) {
                LOG.error("Could not instantiate", e);
            }
        }
    }

    public String readCommandLine() {

        // open up standard input
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        String command = null;
        try {
            command = br.readLine();
        }
        catch (IOException ioe) {
            System.out.println("IO error trying to read command line");
            System.exit(1);
        }
        return command;
    }

    private void displayHelpSheet() {

        System.out.println("verkeerde commando getypt");
        for (String cmd : commandMap.keySet()) {
            Command command = commandMap.get(cmd);
            System.out.println(cmd + ": " + command.showHelp());
        }
    }

    private boolean handleQuit() {

        System.out.println("Are you sure you want to quit? Y/N");
        String commandLine = readCommandLine();
        Scanner s = new Scanner(commandLine);
        if (s.hasNext()) {
            String cmd = s.next();
            if ("y".equalsIgnoreCase(cmd)) {
                return true;
            }
        }
        return false;
    }

    // dependency injection
    @Inject
    public void setStorage(Storage storage) {
        System.out.println("Using Storage implementation: "
            + storage.getClass().getName());
        this.storage = storage;
    }

}
