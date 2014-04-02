package xcon.example.bridge.textchecker;

import java.util.Scanner;

import xcon.example.bridge.dictionary.Dictionary;

public class WordOccurrenceCounter implements TextChecker {

    public WordOccurrenceCounter(Dictionary dictionary) {
        // we don't use a dictionary
    }

    public WordOptions check(String text) {
        
        WordOptions result = new WordOptions();
        Scanner sc = new Scanner(text).useDelimiter("\\W+");
        while (sc.hasNext()) {
            String input = sc.next();
            int count = 0;
            if (result.containsWord(input)) {
                String option = result.getOptions(input).get(0);
                result.removeOption(input, option);
                count = Integer.parseInt(option);
            }
            result.addOption(input, String.valueOf(count + 1));
        }
        return result;
    }
}
