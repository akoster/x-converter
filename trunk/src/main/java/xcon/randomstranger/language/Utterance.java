package xcon.randomstranger.language;

public class Utterance {

    private String name;
    private String message;
    private long timeMillis;

    public Utterance(String name, String message) {
        this.name = name;
        this.message = message;
        this.timeMillis = System.currentTimeMillis();
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int complexity() {
        int messageLength = message.length();
        int wordCount = message.split("\\s+").length;
        return (int) Math.max(wordCount, messageLength / 5.1);
    }
}
