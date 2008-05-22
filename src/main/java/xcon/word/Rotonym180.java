package xcon.word;

import java.util.HashMap;
import java.util.Map;

// Rotonym180 implements Rotonym
//

// lowercase character mappings, rotation 180:
//
// d p
// a e
// b q
// h y
// m w
// n u
// Note that this mapping works in two directions, i.e. letters on the
// right also count as inputs.
//
// self-mapping: o,x
public class Rotonym180 extends RotonymStrategy {

    Map<Character, Character> charMap;

    public Rotonym180() {
        charMap = new HashMap<Character, Character>();
        charMap.put('d', 'p');
        charMap.put('a', 'e');
        charMap.put('b', 'q');
        charMap.put('h', 'y');
        charMap.put('m', 'w');
        charMap.put('n', 'u');
        charMap.put('o', 'o');
        charMap.put('x', 'x');
        charMap.put('p', 'd');
        charMap.put('e', 'a');
        charMap.put('q', 'b');
        charMap.put('y', 'h');
        charMap.put('w', 'm');
        charMap.put('u', 'n');
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
