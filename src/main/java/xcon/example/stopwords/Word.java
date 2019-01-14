package xcon.example.stopwords;

public class Word {

    private final String clean;

    public Word(String input) {
        clean = input
                // convert to lower case
                .toLowerCase()
                // remove everything that's not a lower case letter or a quote
                // (the stopwords file only contains lower case letters and quotes)
                .replaceAll("[^a-z']", "")
                // replace consecutive white space with a single space
                .replaceAll("\\s+", " ")
                // trim any white space at the edges
                .trim();
    }

    public String normalized() {
        return clean;
    }

}
