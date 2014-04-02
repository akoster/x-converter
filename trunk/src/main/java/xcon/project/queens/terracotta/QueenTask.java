package xcon.project.queens.terracotta;

public class QueenTask {

    int queenColumn;
    int boardSize;

    public QueenTask(int firstColumn, int boardSize) {
        this.queenColumn = firstColumn;
        this.boardSize = boardSize;
    }

}
