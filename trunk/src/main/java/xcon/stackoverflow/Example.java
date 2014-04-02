package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * http://stackoverflow.com/questions/7660262/what-is-the-difference-between-creating-instance-by-extending-superclass-and-by/7661571#7661571
 */
public class Example {

    private Collection<String> greetings = new HashSet<String>();

    public void addGreeting(String greeting) {

        ArrayList<String> greetingList = (ArrayList<String>) greetings;
        greetingList.ensureCapacity(42);
        greetings.add(greeting);
    }

    public static void main(String[] args) {
        new Example().addGreeting("hello");
    }
}