package xcon.queens.terracotta;

public class QueenBoard {

	// private static final Logger LOG = Logger.getLogger(QueenBoard.class);
	private final int[][] board;
	private final int size;
	private int isCount = 0;
	private int setCount = 0;
	private int removeCount = 0;

	// 0 is een leeg veld
	// >0 is een aangevallen veld, de waarde geeft aan hoe vaak het veld
	// wordt aangevallen (dus door hoeveel queens)
	private static final int FIELD_QUEEN = -1;
	private static final int FIELD_EMPTY = 0;

	public QueenBoard(int size) {

		this.size = size;
		board = new int[size][size];
		init();
	}

	public void init() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = FIELD_EMPTY;
			}
		}
	}

	public int getSize() {
		return size;
	}

	/*
	 * public boolean isQueenAllowed(int row, int column) { // je hoeft alleen
	 * maar te kijken of het veld FIELD_EMPTY is! isCount++; for (int k = 0; k <
	 * size; k++) { // controleer rij if (board[row][k] == FIELD_QUEEN) { return
	 * false; } // controleer kolom if (board[k][column] == FIELD_QUEEN) {
	 * return false; } // controleer LB-RO diag int i = row - column + k; if (i
	 * >= 0 && i < size) { if (board[i][k] == FIELD_QUEEN) { return false; } }
	 * // controleer LO-RB diag i = row + column - k; if (i >= 0 && i < size) {
	 * if (board[i][k] == FIELD_QUEEN) { return false; } } }
	 * LOG.debug("allowed"); return true; }
	 */

	/*
	 * public void setQueen(int row, int column) throws IllegalArgumentException
	 * { LOG.debug("row=" + row + " column=" + column); // als fieldwaarde ==
	 * FIELD_QUEEN dan exceptie want er staat al een // queen setCount++; if
	 * (board[row][column] == FIELD_QUEEN) { throw new
	 * IllegalArgumentException("Field already occupied"); } else {
	 * board[row][column] = FIELD_QUEEN; } // 1: als fieldwaarde > FIELD_EMPTY
	 * dan moet er ook een exceptie worden // gegooid want dan is het veld
	 * aangevallen // // 2: als FIELD_EMPTY dan moeten alle velden // op
	 * dezelfde rij, kolom en diagonalen worden opgehoogd // en wordt de queen
	 * geplaatst }
	 */
	/*
	 * public void removeQueen(int row, int column) throws
	 * IllegalArgumentException { LOG.debug("row=" + row + " column=" + column);
	 * // als fieldwaarde == FIELD_EMPTY dan exceptie want veld al leeg
	 * removeCount++; if (board[row][column] == FIELD_EMPTY) { throw new
	 * IllegalArgumentException("Field already empty"); } else {
	 * board[row][column] = FIELD_EMPTY; } // verwijdert de queen, en vermindert
	 * de aangevallen velden met 1 }
	 */

	/**
	 * <pre>
	 * .... 
	 * .... 
	 * ---- 
	 * Q... row=0, col=0 
	 * .+.. k=0, 1, 2, 3 
	 * ..+. 
	 * ...+ 
	 * ---- i=0  1  2  3          row - (column - k)
	 * .... 
	 * ....
	 * </pre>
	 */

	public boolean setQueen(int row, int column)
			throws IllegalArgumentException {
		setCount++;
		if (!isQueenAllowed(row, column)) {
			// throw new IllegalArgumentException("Queen not allowed");
			return false;
		}
		updateBoard(row, column, 1);
		board[row][column] = FIELD_QUEEN;
		return true;
	}

	private void updateBoard(int row, int column, int diff) {

		// LOG.debug("row=" + row + " column=" + column);
		// als fieldwaarde == FIELD_QUEEN dan exceptie want er staat al een
		// queen

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

		// je hoeft alleen maar te kijken of het veld FIELD_EMPTY is!
		isCount++;
		return board[row][column] == 0;
	}

	public void removeQueen(int row, int column) {

		removeCount++;
		updateBoard(row, column, -1);
		// note that we assume a Queen is never attacked
		// this is enforced by a check in setQueen
		board[row][column] = FIELD_EMPTY;
	}

	/*
	 * public void showBoard() {
	 * System.out.println("=========================================="); for
	 * (int i = 0; i < size; i++) { for (int j = 0; j < size; j++) { String
	 * field; if (board[i][j] == FIELD_QUEEN) { field = "Q"; } else { field =
	 * "."; } System.out.print(field); System.out.print(" "); }
	 * System.out.println(); } }
	 */

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

	public int getIsCount() {
		return isCount;
	}

	public int getSetCount() {
		return setCount;
	}

	public int getRemoveCount() {
		return removeCount;
	}

	/**
	 * Returns the column of the queen in the given row, or 0 if no queen is
	 * found
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
		return 0;// XXX: throw Exception
	}

	/**
	 * Returns the row of the queen in the given column, or 0 if no queen is
	 * found
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
		return 0;// XXX: throw Exception
	}

}
