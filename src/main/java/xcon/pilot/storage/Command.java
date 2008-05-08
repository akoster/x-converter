package xcon.pilot.storage;

import java.util.Scanner;

public abstract class Command {
    
    public Command() {}

    public abstract void execute(Scanner s);

    public abstract String showHelp();
}
