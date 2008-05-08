package xcon.pilot.storage;

import java.util.Scanner;

public abstract class Command {

    protected static Storage storage;

    public Command() {}

    public static void setStorage(Storage storage) {
        Command.storage = storage;
    }

    public abstract void execute(Scanner s);

    public abstract String showHelp();
}
