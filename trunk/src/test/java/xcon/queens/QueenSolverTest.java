package xcon.queens;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueenSolverTest {

    private QueenSolver queenSolver;

    @Before
    public void setUp() throws Exception {
        queenSolver = new QueenSolver();
        queenSolver.init();
        queenSolver.showBoard();
    }

    @Test
    public void testIsQueenAllowed() {
        System.out.println("in testIsQueenAllowed");
        boolean result;
        // empty board
        result = queenSolver.isQueenAllowed(0, 0);
        Assert.assertTrue(result);
        // add a queen
        queenSolver.setQueen(1, 1);
        queenSolver.showBoard();
        // test same row
        result = queenSolver.isQueenAllowed(1, 2);
        Assert.assertFalse(result);
        // test same column
        result = queenSolver.isQueenAllowed(2, 1);
        Assert.assertFalse(result);
        // test same diagonal
        result = queenSolver.isQueenAllowed(2, 2);
        Assert.assertFalse(result);
        // test safe spot
        result = queenSolver.isQueenAllowed(3, 2);
        Assert.assertTrue(result);
    }
}
