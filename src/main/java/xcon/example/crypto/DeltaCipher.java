package xcon.example.crypto;

import static xcon.example.crypto.Tools.checkMinSize;
import static xcon.example.crypto.Tools.getChars;

public class DeltaCipher {

    private static final char OFFSET = 96;
    private static final char WRAP = 26;

    public static void main(String[] args) {
        String input = "thequickbrownfoxleapedoverthelazydog";
        String encoded = encode(input);
        System.out.printf("%s -> %s%n", input, encoded);
        String decoded = decode(encoded);
        System.out.printf("%s -> %s%n", encoded, decoded);
    }

    public static String encode(String input) {
        char[] chars = checkMinSize(getChars(input), 2);
        char[] encodedInput = new char[chars.length];
        int previous = fromAscii(chars[chars.length - 1]);
        for (int i = 0; i < chars.length; i++) {
            int letterValue = fromAscii(chars[i]);
            int encodedLetterValue = wrap(letterValue + previous);
            encodedInput[i] = toAscii(encodedLetterValue);
            previous = encodedLetterValue;
        }
        return new String(encodedInput);
    }

    public static String decode(String input) {
        char[] chars = checkMinSize(getChars(input), 2);
        char[] decodedInput = new char[chars.length];
        int previous = fromAscii(chars[chars.length - 1]);
        for (int i = chars.length - 2; i >= 0; i--) {
            int letterValue = fromAscii(chars[i]);
            int decodedLetterValue = wrap(previous - letterValue);
            decodedInput[i + 1] = toAscii(decodedLetterValue);
            previous = letterValue;
        }
        decodedInput[0] = toAscii(wrap(chars[0] - decodedInput[decodedInput.length - 1]));
        return new String(decodedInput);
    }

    private static int fromAscii(char ascii) {
        return ascii - OFFSET;
    }

    private static char toAscii(int value) {
        return (char) ((char) value + OFFSET);
    }

    private static int wrap(int value) {
        if (value <= 0) {
            value += WRAP;
        } else if (value > WRAP) {
            value -= WRAP;
        }
        return value;
    }

}
