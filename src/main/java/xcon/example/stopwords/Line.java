package xcon.example.stopwords;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Line {

    private final List<Word> words;

    public Line(String input) {
        String[] wordInputs = input.split("\\s+");
        words = Arrays.stream(wordInputs)
                // filter out empty Strings
                .filter(v -> !v.isEmpty())
                // map String to a Word object
                .map(Word::new)
                // collect into a List
                .collect(Collectors.toList());
    }

    public List<Word> words() {
        return words;
    }

}
