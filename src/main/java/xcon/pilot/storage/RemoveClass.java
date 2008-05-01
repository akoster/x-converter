package xcon.pilot.storage;

import java.io.File;
import java.util.Scanner;

public class RemoveClass implements Command {

    private static final String EXTENSION = ".ser";
    Storage storage;

    public RemoveClass(Storage storage) {
        this.storage = storage;
    }

    public void execute(Scanner s) {
        handleRemove(s);

    }

    private void handleRemove(Scanner s) {

        if (s.hasNext()) {
            String key = s.next();
            String fileName = key + EXTENSION;
            File f = new File(fileName);
            if (!f.exists()) {
                throw new IllegalArgumentException(
                    "Delete: no such file or directory: " + fileName);
            }
            if (!f.canWrite()) {
                throw new IllegalArgumentException("Delete: write protected: "
                    + fileName);
            }

            boolean success = f.delete();

            if (!success)
                throw new IllegalArgumentException("Delete: deletion failed");
        }

        else {
            System.out.println("Syntax: read <key>");
        }
    }
}
