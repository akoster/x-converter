package xcon.pilot;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NumericalStringSort {

    public static void main(String[] args) {
        List<String> input = Arrays.asList(new String[] {"17:21", "22:11", "5:34", "5:38"});
        Collections.sort(input, new NumericalStringComparator());
        System.out.println(input);
    }

    public static class NumericalStringComparator implements Comparator<String> {
        public int compare(String object1, String object2) {
            return pad(object1).compareTo(pad(object2));
        }

        private String pad(String input) {
            return input.indexOf(":") == 1 ? "0" + input : input;
        }
    }
}
