package xcon.teracottaQueens;

public class QueenWorker {

    // private final static Logger LOG = Logger.getLogger(Worker.class);

    public QueenWork queenWork = new QueenWork();
    private QueenBoard queenBoard;
    int solutionCount = 0;

    public static void main(String[] args) throws InterruptedException {
        new QueenWorker().run();
    }

    private void run() throws InterruptedException {
        // LOG.debug("excuting Worker run method");
        while (true) {
            System.out.println(" in the while loop");
            QueenTask task = (QueenTask) queenWork.tasks.take();
            System.out.println(" queenColumn: " + task.queenColumn);

            queenBoard = new QueenBoard(task.boardSize);
            queenBoard.init();
            queenBoard.setQueen(0, task.queenColumn);
            placeQueenOnNextRow(1);
            System.out.println("column" + task.queenColumn + "  " + solutionCount +  " solutions:\n" );
            // LOG.debug(solutionCount +" found for column "+ task.queenColumn);
        }
    }

    private void placeQueenOnNextRow(int row) {
        for (int column = 0; column < queenBoard.getSize(); column++) {
            if (queenBoard.setQueen(row, column)) {
                if (row < (queenBoard.getSize() - 1)) {
                    placeQueenOnNextRow(row + 1);
                }
                else {
                    solutionCount++;
                }
                queenBoard.removeQueen(row, column);
            }
        }
    }

}
