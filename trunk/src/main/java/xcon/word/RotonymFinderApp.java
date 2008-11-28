package xcon.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Searches a dictionary for words of which the rotonym also occurs in the
 * dictionary
 * @author loudiyimo
 * 
 * <pre>
 * voorbeeld commandline om app te starten:
 * java xwords.palindrome.RotonymFinder dictionary.txt 180
 * 
 * waarbij:
 * 1e arg is filenaam van woordenboek
 * 2e arg is aanduiding van RotonymStrategy:
 *   180 = xwords.palindrome.Rotonym180
 *   90 = xwords.palindrome.Rotonym90
 * 
 * bepaal de rotonym strategie
 * woordenboek helemaal inlezen in een Map<String, Boolean>
 * woord voor woord over de keyset van de Map itereren
 * bepaal voor elk woord of het een rotonym is
 * ja:
 *   zoek in het woordenboek naar deze rotonym
 *   gevonden?
 *   ja: uitprinten
 *   nee: ga verder
 * nee:
 *   ga verder met volgende woorden  
 *   
 *   
 * extra 1:
 * doe dit altijd voor alle RotonymStrategien die er zijn   
 * 
 * extra 2:
 * Maak plugins van de RotonymStragy objecten (zie DataStore)
 * extra 3:
 * voeg een of meer nieuwe RotonymStrategy implementaties toe
 * </pre>
 */
public class RotonymFinderApp {

    private Map<String, Boolean> words;
    private RotonymStrategy strategy;

    public RotonymFinderApp(String fileName, String cmd) {

        strategy = determineStrategy(cmd);
        if (strategy == null) {
            System.err.println("wrong command");
            return;
        }

        words = determineWords(fileName);
        if (words == null) {
            System.err.println("could not read file");
            return;
        }
    }

    private Map<String, Boolean> determineWords(String fileName) {
        Map<String, Boolean> words = new HashMap<String, Boolean>();
        Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            words = null;
        }

        while (input.hasNext()) {
            String word = input.next();
            if (word.length() > 1) {
                words.put(word.toLowerCase(), true);
            }
        }
        return words;
    }

    private RotonymStrategy determineStrategy(String cmd) {
        RotonymStrategy strategy = null;
        if ("180".equals(cmd)) {
            System.out.println("the rotonyms 180 ");
            strategy = new Rotonym180();

        }
        else if ("90".equals(cmd)) {
            strategy = new Rotonym90();
            System.out.println("the rotonyms 90 ");
        }
        return strategy;
    }

    public static void main(String[] args) {

        // args = null
        // args = new String[] {};
        if (args == null || args.length < 2) {
            printUsage();
            return;
        }

        String fileName = args[0];
        String strategy = args[1];

        RotonymFinderApp finder = new RotonymFinderApp(fileName, strategy);
        finder.run();
        System.out.println("Done.");
    }

    /**
     * <pre>
     * woord voor woord over de keyset van de Map itereren
     * bepaal voor elk woord of het een rotonym is
     * ja:
     *   zoek in het woordenboek naar deze rotonym
     *   gevonden?
     *   ja: uitprinten
     *   nee: ga verder
     * nee:
     *   ga verder met volgende woorden
     *   </pre>
     */
    private void run() {
        for (String word : words.keySet()) {
            try {
                String rotonym = strategy.determineRotonym(word);
                // word is een rotonym
                if (words.get(rotonym.toLowerCase()) != null) {
                    System.out.println(word + " <-> " + rotonym);
                }
            }
            catch (RotonymException e) {
                // word is geen rotonym
                continue;
            }
        }
    }

    private static void printUsage() {
        System.err.println("verkeerde argumenten");
        System.err.println("gebruik: java xwords.palindrome.RotonymFinder dictionary.txt 180");

    }

}
