package xcon.example.crypto;

import static xcon.example.crypto.Tools.checkMinSize;
import static xcon.example.crypto.Tools.getChars;

public class PointerCipher {

    private static final char OFFSET = 96;
    private static final char WRAP = 26;
    private static final char EMPTY = 0;

    public static void main(String[] args) {
        String input = "thequickbrownfoxleapedoverthelazydog";

        String encoded = encode(input);
        System.out.printf("%s -> %s%n", input, encoded);

        String decoded = decode(encoded);
        System.out.printf("%s -> %s%n", encoded, decoded);
    }

    public static String encode(String input) {
        char[] chars = checkMinSize(getChars(input), 1);
        char[] encodedInput = new char[chars.length];
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            char letter = chars[pos];
            chars[pos] = EMPTY;
            encodedInput[i] = letter;
            if (i < chars.length - 1) {
                pos = walk(pos, letter, chars, true);
            }
        }
        return new String(encodedInput);
    }

    public static String decode(String input) {
        char[] chars = checkMinSize(getChars(input), 1);
        char[] decodedInput = new char[chars.length];
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            char letter = chars[i];
            chars[i] = EMPTY;
            decodedInput[pos] = letter;
            if (i < chars.length - 1) {
                pos = walk(pos, letter, decodedInput, false);
            }
        }
        return new String(decodedInput);
    }

    private static int walk(int pos, char letter, char[] chars, boolean skipEmpty) {
        int letterValue = fromAscii(letter);
        while (letterValue > 0) {
            pos = nextPos(pos, chars);
            if (skipEmpty) {
                pos = skipEmpty(pos, chars);
            } else {
                pos = skipNonEmpty(pos, chars);
            }
            letterValue--;
        }
        return pos;
    }

    private static int skipEmpty(int pos, char[] chars) {
        while (chars[pos] == EMPTY) {
            pos = nextPos(pos, chars);
        }
        return pos;
    }

    private static int skipNonEmpty(int pos, char[] chars) {
        while (chars[pos] != EMPTY) {
            pos = nextPos(pos, chars);
        }
        return pos;
    }

    private static int nextPos(int pos, char[] chars) {
        pos++;
        if (pos >= chars.length) {
            pos = 0;
        }
        return pos;
    }

    private static int fromAscii(char ascii) {
        return ascii - OFFSET;
    }

}
