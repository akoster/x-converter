package xcon.queens;

import org.apache.log4j.Logger;

public class QueenBoard {

    private static final Logger LOG = Logger.getLogger(QueenBoard.class);
    private int[][] board;
    private static final int SIZE = 4;

    // 0 is een leeg veld
    // >0 is een aangevallen veld, de waarde geeft aan hoe vaak het veld
    // wordt aangevallen (dus door hoeveel queens)
    private static final int FIELD_QUEEN = -1;
    private static final int FIELD_EMPTY = 0;

    public QueenBoard() {
        board = new int[SIZE][SIZE];
    }

    public void init() {
        System.out.println(" in init methode");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = FIELD_EMPTY;
            }
        }
    }

    public boolean isQueenAllowed(int row, int column) {

        // je hoeft alleen maar te kijken of het veld FIELD_EMPTY is!

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == FIELD_QUEEN) {
                    if (column == j) {
                        LOG.warn("not allowed");
                        return false;
                    }
                    if (row == i) {
                        LOG.warn("not allowed");
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
        }
        LOG.debug("allowed");
        return true;
    }

    public void setQueen(int row, int column) throws IllegalArgumentException {
        LOG.debug("row=" + row + " column=" + column);
        // als fieldwaarde == FIELD_QUEEN dan exceptie want er staat al een queen
        if (board[row][column] == FIELD_QUEEN) {
            throw new IllegalArgumentException("Field already occupied");
        }
        else {
            board[row][column] = FIELD_QUEEN;
        }
        // 1: als fieldwaarde > FIELD_EMPTY dan moet er ook een exceptie worden
        // gegooid want dan is het veld aangevallen
        //
        // 2: als FIELD_EMPTY dan moeten alle velden
        // op dezelfde rij, kolom en diagonalen worden opgehoogd
        // en wordt de queen geplaatst
    }

    public void removeQueen(int row) throws IllegalArgumentException {
        // gooit een exceptie als er geen queen op deze rij staat
        //
        // verwijdert de queen, en vermindert de aangevallen velden met 1        
    }
    
    public void showBoard() {
        System.out.println("showingBoard");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
