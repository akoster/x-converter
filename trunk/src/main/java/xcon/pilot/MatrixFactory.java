package xcon.pilot;

import java.util.Arrays;

public class MatrixFactory {

    /**
     * Creates a diagonal matrix
     * 
     * @param diagonal the diagonal values
     * @return
     */
    public static MainMatrix diagonalMatrix(final double[] diagonal) {
        return new MainMatrix() {
            @Override
            public void doMatrixyStuff() {
                System.out.println(String.format("Diagonal=%s", Arrays.toString(diagonal)));
            }
        };
    }

    /**
     * Creates a full matrix
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     * @return
     */
    public static MainMatrix fullMatrix(final int rows, final int cols) {
        return new MainMatrix() {
            @Override
            public void doMatrixyStuff() {
                System.out.println(String.format("Rows=%s, cols=%s", rows, cols));
            }
        };
    }

    public static void main(String[] args) {
        MainMatrix diagonalMatrix = MatrixFactory.diagonalMatrix(new double[] {1.0, 2.0, 3.0});
        diagonalMatrix.doMatrixyStuff();

        MainMatrix fullMatrix = MatrixFactory.fullMatrix(4, 4);
        fullMatrix.doMatrixyStuff();
    }
}