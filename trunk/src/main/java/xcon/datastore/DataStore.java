package xcon.datastore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class DataStore {

    private static final Logger LOG = Logger.getLogger(DataStore.class);

    Map<String, Command> commandMap;

    private static final String CMD_QUIT = "quit";
    private static final String CMD_HELP = "help";
    private static final String CMD_IMPL = "impl";

    public static void main(String[] args) {

        System.out.println("DataStore application");
        DataStore testObject = new DataStore();
        testObject.run();
    }

    public DataStore() {
        init();
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
            if (CMD_QUIT.equalsIgnoreCase(cmd)) {
                quit = handleQuit();
            }
            else if (CMD_HELP.equalsIgnoreCase(cmd)) {
                handleHelp();
            }
            else if (CMD_IMPL.equalsIgnoreCase(cmd)) {
                handleImpl(s);
            }
            else {
                Command command = commandMap.get(cmd);
                if (command == null) {
                    showHelpHelp();
                }
                else {
                    command.execute(s);
                }
            }
        }
        while (!quit);
        System.out.println("bye");
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

    private void handleHelp() {

        System.out.println("Help sheet");
        for (String cmd : commandMap.keySet()) {
            Command command = commandMap.get(cmd);
            System.out.println(cmd + ": " + command.showHelp());
        }
        showHelpImpl();
        showHelpHelp();
        showHelpQuit();
    }

    private void showHelpHelp() {
        System.out.println("help: displays a help sheet");
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

    private void showHelpQuit() {
        System.out.println("quit: exits the application");
    }

    private void handleImpl(Scanner s) {

        if (s.hasNext()) {
            String impl = s.next();
            Storage.setImplementation(impl);
        }
        else {
            showHelpImpl();
        }
    }

    private void showHelpImpl() {
        System.out.println("impl: " + Storage.getImplKeys()
            + " - sets the Storage implementation");
    }
}
