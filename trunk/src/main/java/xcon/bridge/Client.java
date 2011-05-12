package xcon.bridge;

import xcon.bridge.dictionary.Dictionary;
import xcon.bridge.dictionary.FileDictionary;
import xcon.bridge.dictionary.Language;
import xcon.bridge.textchecker.TextChecker;
import xcon.bridge.textchecker.WordOccurrenceCounter;
import xcon.bridge.textchecker.WordOptions;

public class Client {

    public static void main(String[] args) {
        Dictionary dictionary = new FileDictionary(Language.DUTCH);
        TextChecker textChecker = new WordOccurrenceCounter(dictionary);
        WordOptions result = textChecker.check("Als het regend vleigen de swaluwen laag. Het regent. ");
        System.out.println(result);
    }
}
