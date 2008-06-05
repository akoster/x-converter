package xcon.word;

import java.util.HashMap;
import java.util.Map;

// Rotonym90
// upper case character mappings, character rotation 90 clockwise:
//
// E M
// H I
// I H
// N Z
// U C
// W E
// Z N
//
// This mapping only works from left to right.
//
// self-mapping: X, O
public class Rotonym90 extends RotonymStrategy {

    Map<Character, Character> charMap;

    public Rotonym90() {
        charMap = new HashMap<Character, Character>();
        charMap.put('E', 'M');
        charMap.put('H', 'I');
        charMap.put('I', 'H');
        charMap.put('U', 'C');
        charMap.put('W', 'E');
        charMap.put('Z', 'N');
        charMap.put('O', 'O');
        charMap.put('X', 'X');
    }    

    public char returnChar(char ch) throws RotonymException {

        Character rotonymChar = charMap.get(Character.toUpperCase(ch));
        if (rotonymChar == null) {
            throw new RotonymException("Character " + ch
                + " is not a rotonym character");
        }
        return rotonymChar;
    }
}
