package xcon.project.queens;

import org.apache.log4j.Logger;

public class QueenBoard {

	private static final Logger LOG = Logger.getLogger(QueenBoard.class);
	private final int[][] board;
	private final int size;

	// statistics
	private int isCount = 0;
	private int setCount = 0;
	private int removeCount = 0;

	// value of a field with a queen
	private static final int FIELD_QUEEN = -1;
	// value of an empty field which is not attacked
	private static final int FIELD_EMPTY = 0;

	public QueenBoard(int size) {

		this.size = size;
		board = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = FIELD_EMPTY;
			}
		}
	}

	public int getSize() {
		return size;
	}

	public boolean setQueen(int row, int column) {

		setCount++;
		if (!isQueenAllowed(row, column)) {
			return false;
		}
		updateBoard(row, column, true);
		board[row][column] = FIELD_QUEEN;
		return true;
	}

	/*
	 * Updates the board so that field values reflect the number of times they
	 * are attacked by a queen.
	 */
	private void updateBoard(int row, int column, boolean addQueen) {

		int diff;
		if (addQueen) {
			// update counts for adding a queen
			diff = 1;
		} else {
			// update counts for removing a queen
			diff = -1;
		}
		LOG.debug("row=" + row + " column=" + column);

		for (int k = 0; k < size; k++) {

			// hoog rij op
			board[row][k] += diff;

			// hoog kolom op
			board[k][column] += diff;

			// hoog LB-RO diag op
			int i = row - column + k;
			if (i >= 0 && i < size) {
				board[i][k] += diff;
			}
			// hoog LO-RB diag op
			i = row + column - k;
			if (i >= 0 && i < size) {
				board[i][k] += diff;
			}
		}
	}

	public boolean isQueenAllowed(int row, int column) {

		// update statistics
		isCount++;
		return board[row][column] == FIELD_EMPTY;
	}

	public void removeQueen(int row, int column) {

		// update statistics
		removeCount++;
		updateBoard(row, column, false);
		// note that we assume a Queen is never attacked
		// this is enforced by a check in setQueen
		board[row][column] = FIELD_EMPTY;
	}

	public synchronized void showBoard() {

		System.out.println("==========================================");
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < size; i++) {

			for (int j = 0; j < size; j++) {

				String field;
				if (board[i][j] == FIELD_QUEEN) {
					field = "Q";
				} else {
					field = ".";
				}
				System.out.print(field);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * Returns the column of the queen in the given row
	 * 
	 * @param i
	 * @return
	 */
	public int getColumn(int i) {

		for (int j = 0; j < size; j++) {
			if (board[i][j] == FIELD_QUEEN) {
				return j;
			}
		}
		throw new RuntimeException("No queen found in column " + i);
	}

	/**
	 * Returns the row of the queen in the given column
	 * 
	 * @param i
	 * @return
	 */
	public int getRow(int j) {

		for (int i = 0; i < size; i++) {
			if (board[i][j] == FIELD_QUEEN) {
				return i;
			}
		}
		throw new RuntimeException("No queen found in row " + j);
	}

	public int getIsCount() {
		return isCount;
	}

	public int getSetCount() {
		return setCount;
	}

	public int getRemoveCount() {
		return removeCount;
	}
}
