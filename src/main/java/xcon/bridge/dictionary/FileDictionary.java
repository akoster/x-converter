package xcon.bridge.dictionary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class FileDictionary implements Dictionary {

    private List<String> words;
    private final static Map<Language, String> dictionaries = new HashMap<Language, String>();

    static {
        dictionaries.put(Language.DUTCH, "/dictionary_nl.txt");
        dictionaries.put(Language.ENGLISH, "/dictionary_en.txt");
    }

    public FileDictionary(Language language) {

        words = new ArrayList<String>();
        String dictionary = getDictionary(language);
		InputStream ras = FileDictionary.class.getResourceAsStream(dictionary);
		Scanner scanner = new Scanner(ras);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().trim();
            if (word != null && word.length() > 0) {
                words.add(word);
            }
        }
        scanner.close();
    }

    private static String getDictionary(Language language) {
        String dictionary = dictionaries.get(language);
        if (dictionary == null) {
            throw new IllegalArgumentException(String.format("Language %s not known", language));
        }
        return dictionary;
    }

    public List<String> findSimilar(String input) {
        if (words == null) {
            throw new IllegalStateException("No language set");
        }
        List<String> similar = new ArrayList<String>();
        for (String word : words) {
            if (matches(input, word)) {
                similar.add(word);
            }
        }
        return similar;
    }

    private boolean matches(String input, String word) {
        int points = 0;
        for (int i = 0; i < Math.min(input.length(), word.length()); i++) {
            if (input.charAt(i) == word.charAt(i)) {
                points = points + 3;
            }
            else if (input.indexOf(word.charAt(i)) >= 0 || word.indexOf(input.charAt(i)) >= 0) {
                points = points + 1;
            }
            else {
                points = points - 5;
            }
        }
        points = points - (2 * Math.abs(input.length() - word.length()));
        return points > 0;
    }

    public boolean isSpelledCorrectly(String input) {
        for (String word : words) {
            if (word.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
