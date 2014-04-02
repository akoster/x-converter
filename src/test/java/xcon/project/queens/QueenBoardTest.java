package xcon.project.queens;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueenBoardTest {

	private static final Logger LOG = Logger.getLogger(QueenBoardTest.class);

	private QueenBoard queenSolver;

	@Before
	public void setUp() throws Exception {
		queenSolver = new QueenBoard(4);
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
		queenSolver.setQueen(1, 2);
		queenSolver.setQueen(2, 2);
		// two queens on same field is not allowed
		queenSolver.setQueen(2, 3);
		Assert.assertFalse(queenSolver.setQueen(2, 3));

		// a queen on an empty field is still allowed
		queenSolver.setQueen(3, 1);
	}
}
