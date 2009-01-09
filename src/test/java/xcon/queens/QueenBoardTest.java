package xcon.queens;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueenBoardTest {

    private static final Logger LOG = Logger.getLogger(QueenBoardTest.class);

    private QueenBoard queenSolver;

    @Before
    public void setUp() throws Exception {
        queenSolver = new QueenBoard();
        queenSolver.init();
        queenSolver.showBoard();
    }

    @Test
    public void testIsQueenAllowed() throws Exception {
        LOG.debug("started");
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

    @Test
    public void testSetQueen() {
        // two queens on different empty fields is allowed
        try {
            queenSolver.setQueen(1, 2);
            queenSolver.setQueen(2, 2);
        }
        catch (Exception e) {
            Assert.fail();
        }
        // two queens on same field is not allowed
        try {
            queenSolver.setQueen(2, 3);
            queenSolver.setQueen(2, 3);
            // jumps to catch block
            Assert.fail();
        }
        catch (Exception e) {
            // expected
            LOG.debug("expected Exception", e);
        }
        // a queen on an empty field is still allowed
        try {
            queenSolver.setQueen(3, 1);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
