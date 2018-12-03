package xcon.stackoverflow.commonwords;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        List<String> file1 = Arrays.asList("aap", "noot", "aap", "wim", "vuur", "noot", "wim");
        List<String> file2 = Arrays.asList("aap", "noot", "mies", "aap", "zus", "jet", "aap", "wim", "vuur");
        List<String> file3 = Arrays.asList("noot", "mies", "wim", "vuur");


        System.out.println(getCommonWords(file1, file2, file3));
    }

    @SafeVarargs
    private final List<String> getCommonWords(List<String>... files) {
        List<String> words = Arrays.stream(files)
                .flatMap(file -> file.stream().distinct())
                .collect(Collectors.toList());
        return words.stream()
                .filter(e -> Collections.frequency(words, e) == files.length)
                .distinct()
                .collect(Collectors.toList());
    }
}
