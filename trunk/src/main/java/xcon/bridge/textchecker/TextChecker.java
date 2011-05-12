package xcon.bridge.textchecker;


/**
 * A text checker service
 */
public interface TextChecker {
    
    /**
     * Checks the text
     * 
     * @param text
     * @return
     */
    WordOptions check(String text);
}
