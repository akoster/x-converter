package xcon.word;

/**
 * A strategy for converting words
 * @author loudiyimo
 */
public abstract class RotonymStrategy {

    /**
     * Determines the rotonym character
     * @param ch
     * @return
     */
    public abstract char returnChar(char ch) throws RotonymException;

    /**
     * Converts the given word to its rotonym according to the concrete Strategy
     * implementation of this abstract class
     * @param word
     * @return The rotonym of the given word
     * @throws RotonymException if the word is not a rotonym
     */
    public String determineRotonym(String word) throws RotonymException {

        String result;
        char ch;
        boolean rotonymWord = true;
        StringBuffer bufferWord = new StringBuffer();
        for (int i = 0; i < word.length(); i++) {

            try {
                ch = this.returnChar(word.charAt(i));
                bufferWord.append(ch);
            }
            catch (RotonymException e) {
                rotonymWord = false;
                break;
            }
        }
        if (rotonymWord) {
            result = bufferWord.toString();
        }
        else {
            throw new RotonymException(word + " is geen rotonym");
        }

        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
