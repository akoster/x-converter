package xcon.example.crypto;

import java.util.Arrays;

public class LetterNumberCipher {

    public static void main(String[] args) {
        String input = "wortel";
        int[] encoded = encode(input);
        System.out.printf("%s -> %s%n", input, Arrays.toString(encoded));

        String decoded = decode(encoded);
        System.out.printf("%s -> %s%n", Arrays.toString(encoded), decoded);
    }

    public static int[] encode(String input) {
        char[] letters = getChars(input);
        int[] numbers = new int[letters.length];
        int i = 0;
        for (int letter : letters) {
            numbers[i++] = letter - 'a' + 1;
        }
        return numbers;
    }

    public static String decode(int[] numbers) {
        char[] letters = new char[numbers.length];
        int i = 0;
        for (int number : numbers) {
            letters[i++] = (char) (number + 'a' - 1);
        }
        return new String(letters);
    }

    private static char[] getChars(String input) {
        return input.toLowerCase().replaceAll("[^a-z]", "").toCharArray();
    }
}
