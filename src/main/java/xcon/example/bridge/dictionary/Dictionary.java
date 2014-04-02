package xcon.example.bridge.dictionary;

import java.util.List;

/**
 * A Dictionary
 */
public interface Dictionary {
    
    /**
     * Indicates if the word is spelled correctly
     * 
     * @param word
     * @return
     */
    boolean isSpelledCorrectly(String word);

    /**
     * Returns similar words
     * 
     * @param word
     * @return
     */
    List<String> findSimilar(String word);
}
