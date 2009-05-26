package xcon.teracottaQueens;

public class QueenResult {

    public enum Type {
        PROGRESS, RESULT
    };

    Type type;
    int firstQueenColumn;
    int value;

    public QueenResult(Type type, int firstQueenColumn, int value) {
        this.type = type;
        this.firstQueenColumn = firstQueenColumn;
        this.value = value;
    }

}
