package xcon.bridge.textchecker;

import java.util.Scanner;

import xcon.bridge.dictionary.Dictionary;

public class SpellingChecker implements TextChecker {

    private Dictionary dictionary;

    public SpellingChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public WordOptions check(String text) {
        WordOptions result = new WordOptions();
        Scanner sc = new Scanner(text).useDelimiter("\\W+");
        while (sc.hasNext()) {
            String input = sc.next();
            if (dictionary.isSpelledCorrectly(input)) {
                // skip correctly spelled words
                continue;
            }
            if (result.containsWord(input)) {
                // skip already encountered words
                continue;
            }
            result.addOptions(input, dictionary.findSimilar(input));
        }
        return result;
    }
}
