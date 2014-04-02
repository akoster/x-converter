package xcon.example.bridge;

import xcon.example.bridge.dictionary.Dictionary;
import xcon.example.bridge.dictionary.FileDictionary;
import xcon.example.bridge.dictionary.Language;
import xcon.example.bridge.textchecker.TextChecker;
import xcon.example.bridge.textchecker.WordOccurrenceCounter;
import xcon.example.bridge.textchecker.WordOptions;

public class Client {

    public static void main(String[] args) {
        Dictionary dictionary = new FileDictionary(Language.DUTCH);
        TextChecker textChecker = new WordOccurrenceCounter(dictionary);
        WordOptions result = textChecker.check("Als het regend vleigen de swaluwen laag. Het regent. ");
        System.out.println(result);
    }
}
