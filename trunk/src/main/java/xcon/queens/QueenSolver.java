package xcon.queens;

import org.apache.log4j.Logger;

public class QueenSolver {

    private static final Logger LOG = Logger.getLogger(QueenSolver.class);
    private int[][] board;
    private static final int X_MIN = 0;
    private static final int X_MAX = 4;
    private static final int Y_MIN = 0;
    private static final int Y_MAX = 4;

    private static final int FIELD_EMPTY = 0;
    private static final int FIELD_QUEEN = 1;

    public QueenSolver() {

        board = new int[4][4];

    }

    public void init() {
        System.out.println(" in intit methode");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                board[i][j] = FIELD_EMPTY;
            }
        }
    }

    public boolean isQueenAllowed(int row, int column) {

        // TODO: implement

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 1) {
                    if (column == j) {
                        System.err.println("not allowed");
                        return false;
                    }
                    if (row == i) {
                        System.err.println("not allowed");
                        return false;
                    }
                    if ((row - i) == (column - j)) {
                        return false;
                    }
                    if (((row - i) + (column - j) == 0)) {
                        return false;
                    }
                }

            }
            // System.out.println();
        }
        System.out.println("allowed");

        return true;
    }

    public void setQueen(int row, int column) {
        System.out.println("in set queen methode");
        board[row][column] = FIELD_QUEEN;
    }

    public void showBoard() {
        System.out.println("showingBoard");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
