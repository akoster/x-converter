package xcon.pilot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FindDuplicates {

    public static Set<Integer> findDuplicates(List<Integer> input) {
        List<Integer> copy = new ArrayList<Integer>(input);
        for (Integer value : new HashSet<Integer>(input)) {
            copy.remove(value);
        }
        return new HashSet<Integer>(copy);
    }

    public static Set<Integer> findDuplicates2(List<Integer> listContainingDuplicates) {
        final Set<Integer> setToReturn = new HashSet<Integer>();
        final Set<Integer> set1 = new HashSet<Integer>();
        for (int yourInt : listContainingDuplicates) {
            if (!set1.add(yourInt)) {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public static void main(String[] args) {

        List<Integer> input = new ArrayList<Integer>();

        Random r = new Random(234637846);
        for (int i = 0; i < 100000; i++) {
            input.add(r.nextInt(50000));
        }
        long start, end;

        start = System.currentTimeMillis();
        Set<Integer> findDuplicates = findDuplicates(input);
        end = System.currentTimeMillis();
        System.out.println(findDuplicates.size() + " findDuplicates in " + (end - start) + " msecs.");

        start = System.currentTimeMillis();
        Set<Integer> findDuplicates2 = findDuplicates2(input);
        end = System.currentTimeMillis();
        System.out.println(findDuplicates2.size() + " findDuplicates2 in " + (end - start) + " msecs.");
    }
}
