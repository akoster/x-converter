package xcon.project.queens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SolutionManager implements SolutionFinderCallbackHandler {

	private static final Logger LOG = Logger.getLogger(SolutionManager.class);

	private int boardSize;
	private int totalSolutionCount;
	private long startTime;
	private List<SolutionFinder> solutionsList;
	private int completedCount;
	private ProgressState progressState;
	private ScheduledExecutorService scheduler;
	private ExecutorService threadExecutor;

	// globale architectuur:
	// WorkQueue met work objecten
	// Work {int boardSize; int firstQueenColumn;}
	// queue als terracotta root delen
	// elke solutionfinder heeft ook die queue als root
	// solution finder kijkt of er work in de queue zit en haalt er een af
	// als work klaar is, wordt dit in een tweede queue (CompletedWork) gezet

	public SolutionManager(int boardSize, int poolSize) {

		this.boardSize = boardSize;
		threadExecutor = Executors.newFixedThreadPool(poolSize);
	}

	public void start() {

		startTime = System.currentTimeMillis();
		completedCount = 0;

		// create a SolutionFinder for each column
		// and start each SolutionFinder
		solutionsList = new ArrayList<SolutionFinder>();
		for (int firstQueenColumn = 0; firstQueenColumn < boardSize; firstQueenColumn++) {
			SolutionFinder solutionFinder = new SolutionFinder(this, boardSize,
					firstQueenColumn);
			solutionsList.add(solutionFinder);
			threadExecutor.execute(solutionFinder);
		}

		// create the progress monitor and schedule it
		progressState = new ProgressState(solutionsList, boardSize);
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(progressState, 0, 800,
				TimeUnit.MILLISECONDS);

	}

	public synchronized void solutionFinderCompleted(SolutionFinder finder) {

		this.totalSolutionCount += finder.getSolutionCount();
		LOG.info(" \nTotal solutions are:" + this.totalSolutionCount);

		completedCount++;
		if (completedCount >= boardSize) {

			// alle threads zijn klaar
			long endTime = System.currentTimeMillis();
			long executionTime = (endTime - startTime) / 1000;
			LOG.info("\nsolutionCount=" + this.totalSolutionCount);
			LOG.info("\nthe solution is found in " + executionTime
					+ "  seconds");
			// progressHandle.cancel(true);
			// progressState.run();
		}
	}

}