package xcon.queens;

import org.apache.log4j.Logger;

public class SolutionFinder implements Runnable {

    public enum State {
        IDLE, RUNNING, COMPLETED
    };

    private static final Logger LOG = Logger.getLogger(SolutionFinder.class);

    private SolutionFinderCallbackHandler solutionFinderCallbackHandler;
    private QueenBoard queenBoard;
    private int solutionCount;
    private State state;

    public SolutionFinder(SolutionFinderCallbackHandler solutionFinderCallbackHandler,
                          int size,
                          int firstQueenColumn)
    {
        this.solutionFinderCallbackHandler = solutionFinderCallbackHandler;
        queenBoard = new QueenBoard(size);
        queenBoard.setQueen(0, firstQueenColumn);
        solutionCount = 0;
        state = State.IDLE;
    }

    @Override
    public void run() {

        state = State.RUNNING;
        LOG.debug("executing thread: " + Thread.currentThread().getName());
        placeQueenOnNextRow(1);
        state = State.COMPLETED;
        LOG.debug("completed thread: " + Thread.currentThread().getName()
            + " solutionCount: " + solutionCount);
        solutionFinderCallbackHandler.solutionFinderCompleted(this);

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

    public QueenBoard getQueenBoard() {
        return queenBoard;
    }

    public int getSolutionCount() {
        return solutionCount;
    }

    public int getProgress() {

        int result;

        switch (state) {
        case IDLE:
            result = 0;
            break;
        case RUNNING:
            int size = queenBoard.getSize();
            int q1 = queenBoard.getColumn(1);
            int q2 = queenBoard.getColumn(2);
            double queenRank = (double) (size * q1 + q2);
            double queenTotal = (double) ((size * size) - 1);
            result = (int) Math.round(100.0 * queenRank / queenTotal);
            LOG.info("size=" + size + " q1=" + q1 + " q2=" + q2 + " result="
                + result);
            break;
        case COMPLETED:
            result = 100;
            break;
        default:
            throw new RuntimeException("Switch-case zuigt");
        }
        return result;
    }

    public State getState() {
        return state;
    }

}
