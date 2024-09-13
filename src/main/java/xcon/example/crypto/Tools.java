package xcon.example.crypto;

import java.lang.reflect.Array;

public class Tools {

    public static char[] getChars(String input) {
        return input.toLowerCase().replaceAll("[^a-z]", "").toCharArray();
    }

    public static <T> T checkMinSize(T input, int min) {
        if (Array.getLength(input) < min) {
            throw new IllegalArgumentException("Cannot handle input shorter than " + min);
        }
        return input;
    }

    public static <T> T checkExactSize(T input, int size) {
        if (Array.getLength(input) != size) {
            throw new IllegalArgumentException("Input must have size " + size);
        }
        return input;
    }
}
