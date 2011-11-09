package xcon.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    private Square[][] values = new Square[9][9];
    List<Square> squares = new ArrayList<Square>();

    public Board(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        if (state.length() < 81) {
            throw new IllegalArgumentException("State length should be at least 81 characters");
        }
        init(state);
    }

    private void init(String state) {
        int x = 0;
        int y = 0;
        for (String str : state.split("")) {
            try {
                int value = Integer.parseInt(str);
                Square square = new Square(x, y, value, this);
                values[x][y] = square;
                squares.add(square);
                x++;
                if (x > 8) {

                    x = 0;
                    y++;
                    if (y > 8) {
                        break;
                    }
                }
            }
            catch (NumberFormatException e) {
                // skip other characters
            }
        }
        updateCandidates(squares);
    }

    public void updateCandidatesAfterUpdate(Square square) {
        Set<Square> area = new HashSet<Square>();
        area.addAll(getBlock(square.getX(), square.getY()));
        area.addAll(getRow(square.getY()));
        area.addAll(getColumn(square.getX()));
        updateCandidates(new ArrayList<Square>(area));
    }

    public void updateCandidates(List<Square> area) {
        for (Square square : area) {
            Set<Integer> candidates = square.getCandidates();
            candidates.clear();
            if (square.isEmpty()) {
                candidates.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                removeValues(candidates, getRow(square.getY()));
                removeValues(candidates, getColumn(square.getX()));
                removeValues(candidates, getBlock(square.getX(), square.getY()));
            }
        }
    }

    private void removeValues(Set<Integer> result, List<Square> area) {
        for (Square square : area) {
            result.remove(square.getValue());
        }
    }

    public boolean isSolved() {
        int filled = 0;
        for (Square square : squares) {
            if (!square.isEmpty()) {
                filled++;
            }
        }
        return filled == 81;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public List<Square> getBlock(int x, int y) {
        List<Square> result = new ArrayList<Square>();
        int sx = 3 * (x / 3);
        int sy = 3 * (y / 3);
        for (int by = 0; by < 3; by++) {
            for (int bx = 0; bx < 3; bx++) {
                result.add(values[sx + bx][sy + by]);
            }
        }
        return result;
    }

    public List<List<Square>> getBlocks() {
        List<List<Square>> result = new ArrayList<List<Square>>();
        for (int by = 0; by < 3; by++) {
            for (int bx = 0; bx < 3; bx++) {
                result.add(getBlock(bx * 3, by * 3));
            }
        }
        return result;
    }

    public List<Square> getRow(int y) {
        List<Square> result = new ArrayList<Square>();
        for (int rx = 0; rx < 9; rx++) {
            result.add(values[rx][y]);
        }
        return result;
    }

    public List<List<Square>> getRows() {
        List<List<Square>> result = new ArrayList<List<Square>>();
        for (int ry = 0; ry < 9; ry++) {
            result.add(getRow(ry));
        }
        return result;
    }

    public List<Square> getColumn(int x) {
        List<Square> result = new ArrayList<Square>();
        for (int cy = 0; cy < 9; cy++) {
            result.add(values[x][cy]);
        }
        return result;
    }

    public List<List<Square>> getColumns() {
        List<List<Square>> result = new ArrayList<List<Square>>();
        for (int cx = 0; cx < 9; cx++) {
            result.add(getColumn(cx));
        }
        return result;
    }

    public List<List<Square>> getAreas() {
        List<List<Square>> result = new ArrayList<List<Square>>();
        result.addAll(getBlocks());
        result.addAll(getRows());
        result.addAll(getColumns());
        return result;
    }

    public String toString2() {

        StringBuilder b = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            if (y == 0 || y == 3 || y == 6) {
                b.append("-------------\n");
            }
            for (int x = 0; x < 9; x++) {
                if (x == 0 || x == 3 || x == 6) {
                    b.append("|");
                }
                int value = values[x][y].getValue();
                if (value != 0) {
                    b.append(value);
                }
                else {
                    b.append(".");
                }
            }
            b.append("|\n");
        }
        b.append("-------------\n");
        return b.toString();
    }

    public String toString() {

        String[][] b = new String[31][31];

        for (int xy = 0; xy < 31; xy++) {
            b[xy][0] = "#";
            b[xy][10] = "#";
            b[xy][20] = "#";
            b[xy][30] = "#";
            b[0][xy] = "#";
            b[10][xy] = "#";
            b[20][xy] = "#";
            b[30][xy] = "#";
        }

        int ylines = 1;
        for (int y = 0; y < 9; y++) {

            if (y == 3) {
                ylines = 2;
            }
            if (y == 6) {
                ylines = 3;
            }

            int xlines = 1;
            for (int x = 0; x < 9; x++) {
                if (x == 3) {
                    xlines = 2;
                }
                if (x == 6) {
                    xlines = 3;
                }
                Square square = values[x][y];
                if (square.getValue() > 0) {
                    for (int cy = 0; cy < 3; cy++) {
                        for (int cx = 0; cx < 3; cx++) {
                            String cell = " ";
                            if (cy == 1 && cx == 1) {
                                cell = String.valueOf(square.getValue());
                            }
                            b[xlines + cx + (3 * x)][ylines + cy + (3 * y)] = cell;
                        }
                    }
                    b[xlines + 1 + (3 * x)][ylines + 1 + (3 * y)] = String.valueOf(square.getValue());
                }
                else {
                    Iterator<Integer> it = square.getCandidates().iterator();
                    for (int cy = 0; cy < 3; cy++) {
                        for (int cx = 0; cx < 3; cx++) {
                            String cell = ".";
                            if (it.hasNext()) {
                                cell = String.valueOf(it.next());
                            }
                            b[xlines + cx + (3 * x)][ylines + cy + (3 * y)] = cell;
                        }
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < 31; y++) {
            for (int x = 0; x < 31; x++) {
                builder.append(b[x][y]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
