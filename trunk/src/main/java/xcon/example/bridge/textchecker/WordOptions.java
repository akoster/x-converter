package xcon.example.bridge.textchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A set of words with options.
 */
public class WordOptions {

    Map<Word, List<String>> wordOptions = new HashMap<Word, List<String>>();

    public void addOption(String word, String option) {
        List<String> options = wordOptions.get(word);
        if (options == null) {
            options = new ArrayList<String>();
            wordOptions.put(new Word(word), options);
        }
        options.add(option);
    }

    public void addOptions(String word, List<String> options) {
        List<String> existingOptions = wordOptions.get(word);
        if (existingOptions == null) {
            existingOptions = new ArrayList<String>();
            wordOptions.put(new Word(word), existingOptions);
        }
        existingOptions.addAll(options);
    }

    public List<String> getOptions(String word) {
        return wordOptions.get(new Word(word));
    }

    public List<String> listWords() {
        List<String> words = new ArrayList<String>();
        for (Word word : wordOptions.keySet()) {
            words.add(word.getValue());
        }
        return words;
    }

    public boolean containsWord(String word) {
        return wordOptions.containsKey(new Word(word));
    }

    public void removeOption(String word, String option) {
        List<String> options = wordOptions.get(new Word(word));
        if (options != null) {
            options.remove(option);
        }
    }

    @Override
    public String toString() {
        final String EOL = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        for (Word word : wordOptions.keySet()) {
            sb.append(word.getValue());
            sb.append(" : ");
            sb.append(Arrays.toString(wordOptions.get(word).toArray()));
            sb.append(EOL);
        }
        return sb.toString();
    }
}
