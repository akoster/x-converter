package xcon.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class RotonymApplication {
    
    public static void main (String args []) throws RotonymException{
        
        RotonymApplication rotonymApplication = new RotonymApplication();
        rotonymApplication.run();
    }
    
    public void run () throws RotonymException {
        
        Scanner s, input = null;
        try {
             input = new Scanner(new File("english.txt"));
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1); 
        }
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
                quit = true ;
                break ; 
            }
            else if ("180".equals(cmd)){
                System.out.println("the rotonyms 180 ");
                strategy = new Rotonym180();
                
            }
            else if ("90".equals(cmd)){
                strategy = new Rotonym90();
                System.out.println("the rotonyms 90 ");
            }
            else if ("both".equals(cmd)){
                System.out.println(" handle both");
            }
             if (strategy != null ){
                // strategy.determineRotonym(input);
   
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
    
    
   /* private void readFile(){
        
        String word = "";
        List<String> wordList = new ArrayList<String>();
        try {
            while (input.hasNext()) {

                word = input.next();
                // System.out.println(word + ": ");
                handleword(word);

            }
        }
        catch (NoSuchElementException exception) {
            System.err.println("improperly file");
            System.exit(1);
        }
        catch (IllegalStateException exception) {
            System.err.println("error reading from file");
            System.exit(1);
        }

        for (String rotonymWord : wordList) {
            System.out.println(" ::" + rotonymWord);

        }
    }
*/
}
