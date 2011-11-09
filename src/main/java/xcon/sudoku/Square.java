package xcon.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Square {
    private final int x;
    private final int y;
    private int value;
    private Set<Integer> candidates = new HashSet<Integer>();
    private Board board;

    public Square(int x, int y, int value, Board board) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.board = board;
    }

    public void setValue(int value) {
        if (this.value != 0) {
            throw new IllegalArgumentException(String.format("Cannot set %s,%s because this position is already set", x, y));
        }
        System.out.println(String.format("Setting %s,%s to %s", x, y, value));
        this.value = value;
        board.updateCandidates(board.getSquares());
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public Set<Integer> getCandidates() {
        return candidates;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]=%s(%s)", x, y, value, Arrays.toString(candidates.toArray()));
    }
}
