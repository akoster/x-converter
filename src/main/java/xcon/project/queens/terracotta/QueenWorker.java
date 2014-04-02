package xcon.project.queens.terracotta;

public class QueenWorker {

	// private final static Logger LOG = Logger.getLogger(Worker.class);

	public QueenWork queenWork = new QueenWork();
	private QueenBoard queenBoard;
	int solutionCount = 0;
	private static final long REPORT_INTERVAL_MILLIS = 2000;
	private long lastProgressTimeMillis;
	private QueenTask currentTask;

	public static void main(String[] args) throws InterruptedException {
		new QueenWorker().run();
	}

	private void run() throws InterruptedException {

		// LOG.debug("excuting Worker run method");
		while (true) {
			solutionCount = 0;
			System.out.println(" in the while loop");
			currentTask = (QueenTask) queenWork.tasks.take();
			System.out.println(" queenColumn: " + currentTask.queenColumn);

			queenBoard = new QueenBoard(currentTask.boardSize);
			queenBoard.init();
			queenBoard.setQueen(0, currentTask.queenColumn);
			placeQueenOnNextRow(1);
			// 1 = type progress
			// 2 = type result
			QueenResult queenResult = new QueenResult(QueenResult.Type.RESULT,
					currentTask.queenColumn, solutionCount);
			queenWork.results.add(queenResult);
			System.out.println("column" + currentTask.queenColumn + "  "
					+ solutionCount + " solutions:\n");
			// LOG.debug(solutionCount +" found for column "+ task.queenColumn);
		}
	}

	private void placeQueenOnNextRow(int row) {

		reportProgess();
		for (int column = 0; column < queenBoard.getSize(); column++) {
			if (queenBoard.setQueen(row, column)) {
				if (row < (queenBoard.getSize() - 1)) {
					placeQueenOnNextRow(row + 1);
				} else {
					solutionCount++;
				}
				queenBoard.removeQueen(row, column);
			}
		}
	}

	private void reportProgess() {

		long now = System.currentTimeMillis();
		if (now - lastProgressTimeMillis > REPORT_INTERVAL_MILLIS) {
			lastProgressTimeMillis = now;
			// bereken hier de progress en zet QueenResult (type=PROGRESS) op de
			// result queue;
			int progressPercentage;
			int size = queenBoard.getSize();
			int q1 = queenBoard.getColumn(1);
			int q2 = queenBoard.getColumn(2);
			double queenRank = (double) (size * q1 + q2);
			double queenTotal = (double) ((size * size) - 1);
			progressPercentage = (int) Math.round(100.0 * queenRank
					/ queenTotal);
			System.out.println("size=" + size + " q1=" + q1 + " q2=" + q2
					+ " result=" + progressPercentage);

			QueenResult queenResult = new QueenResult(
					QueenResult.Type.PROGRESS, currentTask.queenColumn,
					progressPercentage);
			queenWork.results.add(queenResult);

			// bereken de percentage
			// initialiseer een queenResult (type.Pogress,int, int)
			// stop deze in quee

		}
	}
}
