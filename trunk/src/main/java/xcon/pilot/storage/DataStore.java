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
import xcon.pilot.storage.impl.FileStorage;
import xcon.pilot.storage.impl.HashMapStorage;
import xcon.pilot.storage.impl.NullStorage;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DataStore {

    private static final Logger LOG = Logger.getLogger(DataStore.class);

    Map<String, Command> commandMap;

    public static void main(String[] args) {

        System.out.println("DataStore application");
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
            // 'special' commands
            if ("quit".equalsIgnoreCase(cmd)) {
                quit = handleQuit();
            }
            else if ("help".equalsIgnoreCase(cmd)) {
                displayHelpSheet();
            }
            else if ("impl".equalsIgnoreCase(cmd)) {
                handleImpl(s);
            }
            else {
                Command command = getCommand(cmd);
                if (command == null) {
                    System.out.println("Type 'help' for a help sheet");
                }
                else {
                    command.execute(s);
                }
            }
        }
        while (!quit);
        System.out.println("bye");
    }

    private Command getCommand(String key) {
        if (commandMap == null) {
            init();
        }
        return commandMap.get(key);
    }

    private void init() {

        commandMap = new HashMap<String, Command>();
        ResourceBundle commands = ResourceBundle.getBundle("commands");
        Enumeration<String> commandKeys = commands.getKeys();
        while (commandKeys.hasMoreElements()) {

            String key = commandKeys.nextElement();
            Command cmd;
            try {
                String commandClass = commands.getString(key);
                cmd = (Command) Class.forName(commandClass).newInstance();
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

        System.out.println("Help sheet");
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

    private void handleImpl(Scanner s) {

        handling: {
            if (s.hasNext()) {
                String impl = s.next();
                if ("file".equals(impl)) {
                    this.setStorage(new FileStorage());
                    break handling;
                }
                else if ("map".equals(impl)) {
                    this.setStorage(new HashMapStorage());
                    break handling;
                }
                else if ("null".equals(impl)) {
                    this.setStorage(new NullStorage());
                    break handling;
                }
            }
            System.out.println("syntax: <file|map|null> - sets the implementation");
        }
    }

    // dependency injection
    @Inject
    public void setStorage(Storage storage) {

        System.out.println("Using Storage implementation: "
            + storage.getClass().getName());
        Command.setStorage(storage);
    }
}
