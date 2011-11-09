package xcon.pilot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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