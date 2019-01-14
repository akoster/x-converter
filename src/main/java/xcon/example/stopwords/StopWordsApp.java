package xcon.example.stopwords;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class StopWordsApp {

    private static final String EOL = String.format("%n");

    private final Set<String> stopWords = new HashSet<>(Arrays.asList(readLines("stopwords.txt")));

    public static void main(String[] args) {
        StopWordsApp stopWordsApp = new StopWordsApp();
        String[] lines = readLines("cleandata.txt");
        printLines(stopWordsApp.removeStopWords(lines));
    }

    private String[] removeStopWords(String[] inputLines) {
        return Arrays.stream(inputLines)
                // map the String array to a Line object
                .map(Line::new)
                // map the Line to a String without stop words
                .map(this::removeStopWords)
                // convert the stream to an array
                .toArray(String[]::new);
    }

    private String removeStopWords(Line line) {
        return line.words().stream()
                // map the word to its normalized version
                .map(Word::normalized)
                // filter out stop words
                .filter(o -> !stopWords.contains(o))
                // join into a String separated by spaces
                .collect(Collectors.joining(" "));
    }

    private static String[] readLines(String fileName) {
        return readFile(fileName).split(EOL);
    }

    private static String readFile(String fileName) {
        return new Scanner(StopWordsApp.class.getResourceAsStream(fileName), "UTF-8").useDelimiter("\\A").next();
    }

    private static void printLines(String[] lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
