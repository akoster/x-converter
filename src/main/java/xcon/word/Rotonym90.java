package xcon.word;

import java.util.HashMap;
import java.util.Map;

//Rotonym90
//upper case character mappings, character rotation 90 clockwise:
//
//E M
//H I
//I H
//N Z
//U C
//W E
//Z N
//
//This mapping only works from left to right.
//
//self-mapping: X, O
public class Rotonym90 extends RotonymStrategy {

    /*
     * E M H I I H N Z U C W E Z N
     */

    Map<Character, Character> charMap;

    public Rotonym90() {
        charMap = new HashMap<Character, Character>();
        charMap.put('E', 'M');
        charMap.put('H', 'I');
        charMap.put('I', 'H');
        charMap.put('U', 'C');
        charMap.put('W', 'E');
        charMap.put('Z', 'N');
        charMap.put('o', 'o');
        charMap.put('x', 'x');
        charMap.put('O', 'O');
        charMap.put('X', 'X');

    }

    @Override
    public boolean isAllowed(char teken) {
        return charMap.containsKey(teken);
    }

    @Override
    public char returnChar(char ch) {

        return charMap.get(ch);
    }

}
