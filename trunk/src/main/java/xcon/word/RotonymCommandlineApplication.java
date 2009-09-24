package xcon.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RotonymCommandlineApplication {

    public static void main(String args[]) throws RotonymException {

        RotonymCommandlineApplication rotonymApplication =
            new RotonymCommandlineApplication();
        rotonymApplication.run();
    }

    public void run() throws RotonymException {

        Scanner s = null;       
        String cmd = null;
        boolean quit = false;
        RotonymStrategy strategy = null;
        do {
            System.out.println(" enter quit, 180, 90, both");
            String commandLine = readCommandLine();
            s = new Scanner(commandLine);
            if (s.hasNext()) {
                cmd = s.next();
            }
            if ("quit".equalsIgnoreCase(cmd)) {
                quit = true;
                break;
            }
            else if ("180".equals(cmd)) {
                System.out.println("the rotonyms 180 ");
                strategy = new Rotonym180();
            }
            else if ("90".equals(cmd)) {
                strategy = new Rotonym90();
                System.out.println("the rotonyms 90 ");
            }
            else if ("both".equals(cmd)) {
                System.out.println(" handle both");
            }
            if (strategy != null) {
                System.out.println(strategy.determineRotonym(s.next()));
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
}
