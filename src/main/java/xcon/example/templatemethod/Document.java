package xcon.example.templatemethod;

public class Document {
    private final String path;
    private final String content;

    public Document(String path, String content) {
        this.path = path;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }
}
