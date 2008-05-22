package xcon.word;

/**
 * A strategy for converting words
 * @author loudiyimo
 */
public abstract class RotonymStrategy {

    /**
     * Determines if the given character is allowed by this strategy
     * @param ch
     * @return
     */
    public abstract boolean isAllowed(char ch);

    public abstract char returnChar(char ch);

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
        char r_ch;
        boolean rotonymWord = false;
        StringBuffer bufferWord = new StringBuffer();
        for (int i = 0; i < word.length(); i++) {

            System.out.println("Char " + i + " is " + word.charAt(i));
            ch = word.charAt(i);
            if (this.isAllowed(ch)) {
                r_ch = this.returnChar(ch);
                System.out.println(r_ch);
                bufferWord.append(r_ch);
                rotonymWord = true;
            }
            else {
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
