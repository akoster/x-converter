package xcon.example.crypto;

import static xcon.example.crypto.Tools.checkMinSize;
import static xcon.example.crypto.Tools.getChars;

public class DeltaKeyCipher {

    private static final char OFFSET = 96;
    private static final char WRAP = 26;

    public static void main(String[] args) {
        String input = "thequickbrownfoxleapedoverthelazydog";
        String key = "lucy";
        String encoded = encode(input, key);
        System.out.printf("%s -> %s%n", input, encoded);
        String decoded = decode(encoded, key);
        System.out.printf("%s -> %s%n", encoded, decoded);
    }

    public static String encode(String plainText, String keyText) {
        char[] plainChars = checkMinSize(getChars(plainText), 2);
        char[] keyChars = checkMinSize(getChars(keyText), 2);
        char[] encryptedChars = new char[plainChars.length];
        int previous = fromAscii(plainChars[plainChars.length - 1]);
        int keyIndex = 0;
        for (int i = 0; i < plainChars.length; i++) {
            int plain = fromAscii(plainChars[i]);
            int delta = wrap(plain + previous);
            int key = fromAscii(keyChars[keyIndex]);
            int encrypted = wrap(delta + key);
            encryptedChars[i] = toAscii(encrypted);
            previous = encrypted;
            keyIndex++;
            if (keyIndex >= keyChars.length) {
                keyIndex = 0;
            }
        }
        return new String(encryptedChars);
    }

    public static String decode(String encryptedText, String keyText) {
        char[] encryptedChars = checkMinSize(getChars(encryptedText), 2);
        char[] keyChars = checkMinSize(getChars(keyText), 2);
        char[] plainChars = new char[encryptedChars.length];
        int keyIndex = (encryptedChars.length - 1) % keyChars.length;
        int previous = fromAscii(encryptedChars[encryptedChars.length - 1]);
        for (int i = encryptedChars.length - 2; i >= 0; i--) {
            int key = fromAscii(keyChars[keyIndex]);
            int delta = wrap(previous - key);
            int encrypted = fromAscii(encryptedChars[i]);
            int plain = wrap(delta - encrypted);
            plainChars[i + 1] = toAscii(plain);
            previous = encrypted;
            keyIndex--;
            if (keyIndex < 0) {
                keyIndex = keyChars.length - 1;
            }
        }
        int lastPlain = fromAscii(plainChars[plainChars.length - 1]);
        int key = fromAscii(keyChars[keyIndex]);
        int plain = wrap(previous - lastPlain - key);
        plainChars[0] = toAscii(plain);
        return new String(plainChars);
    }

    private static int fromAscii(char ascii) {
        return ascii - OFFSET;
    }

    private static char toAscii(int value) {
        return (char) ((char) value + OFFSET);
    }

    private static int wrap(int value) {
        int result = value;
        if (result <= 0) {
            result += WRAP;
        } else if (result > WRAP) {
            result -= WRAP;
        }
        return result;
    }

}
