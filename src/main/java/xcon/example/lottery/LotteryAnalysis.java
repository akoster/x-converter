package xcon.example.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Application for analysing statistics concerning the possible results of a
 * lottery for different numbers of participants.
 * 
 */
public class LotteryAnalysis {

	private static int runSize = 4;
	private static boolean singleRun = true;
	private static boolean showSolutions = false;

	public static void main(String[] args) {

		int from = singleRun ? runSize : 2;
		for (int i = from; i <= runSize; i++) {
			LotteryAnalysis proposalCombinator = new LotteryAnalysis(i);
			proposalCombinator.run();
		}
	}

	private int nodeCount;
	private long solutionCount;
	private long failureCount;
	private Map<String, Integer> cycleCounts;
	private int[][] drawOdds;

	public LotteryAnalysis(int nodeCount) {
		this.nodeCount = nodeCount;
		this.cycleCounts = new HashMap<String, Integer>();
		this.drawOdds = new int[nodeCount][nodeCount];
	}

	private void run() {

		List<Integer> nodes = new ArrayList<Integer>();
		for (int i = 1; i <= nodeCount; i++) {
			nodes.add(i);
		}
		long start = System.nanoTime();
		draw(1, nodes, "");
		long stop = System.nanoTime();

		System.out.println("nodecount=" + nodeCount + " solutions="
				+ solutionCount + " failures=" + failureCount + " duration="
				+ (stop - start) / 1000000.0);
		for (String cycleKey : cycleCounts.keySet()) {
			System.out.println(cycleKey + " : " + cycleCounts.get(cycleKey));
		}
		for (int i = 1; i <= nodeCount; i++) {
			System.out.println("pos " + i + Arrays.toString(drawOdds[i - 1]));
		}
	}

	private void draw(int node, List<Integer> nodes, String solutionSoFar) {

		if (nodes.size() == 0) {
			return;
		}
		String solution = null;
		for (int drawIndex = 0; drawIndex < nodes.size(); drawIndex++) {

			// skip self
			if (nodes.get(drawIndex) == node) {
				continue;
			}
			int drawnNode = nodes.remove(drawIndex);
			solution = solutionSoFar + " " + drawnNode;
			draw(node + 1, nodes, solution);
			nodes.add(drawIndex, drawnNode);
		}
		if (nodes.size() == 1) {
			if (solution != null) {

				countCycles(solution);
				updateOdds(solution);
				solutionCount++;
			} else {
				failureCount++;
			}
		}
	}

	private void updateOdds(String solution) {

		StringTokenizer tok = new StringTokenizer(solution, " ", false);
		int i = 0;
		while (tok.hasMoreTokens()) {

			int drawnNode = Integer.parseInt(tok.nextToken());
			drawOdds[i][drawnNode - 1]++;
			i++;
		}

	}

	private void countCycles(String solution) {

		String cycleKey = determineCycles(solution);
		Integer count = cycleCounts.get(cycleKey);
		if (count == null) {
			cycleCounts.put(cycleKey, 1);
		} else {
			cycleCounts.put(cycleKey, count + 1);
		}
		if (showSolutions) {
			System.out.println("\t" + solution.replaceAll(" ", "") + "\t"
					+ cycleKey);
		}
	}

	private String determineCycles(String solution) {

		String[] available = solution.split(" ");
		assert (available.length == nodeCount + 1);
		List<Integer> cycleSizes = new ArrayList<Integer>();
		int visited = 1;
		int cycleSize = 2;
		int current = 1;
		int start = current;
		int next = removeAvailable(available, current);
		if (showSolutions) {
			System.out.print(current);
		}
		while (visited < (available.length - 1)) {

			current = next;
			if (showSolutions) {
				System.out.print(current);
			}
			visited++;
			next = removeAvailable(available, current);
			if (next == start) {

				cycleSizes.add(cycleSize);
				next = findNext(available);
				cycleSize = 1;
				start = next;
				if (next > 0 && showSolutions) {
					System.out.print("/");
				}
			} else {
				cycleSize++;
			}
		}
		Collections.sort(cycleSizes);
		return cycleSizes.toString();
	}

	private int findNext(String[] available) {
		int next = 1;
		while (available[next] == null) {
			next++;
			if (next >= available.length) {
				return -1;
			}
		}
		return next;
	}

	private int removeAvailable(String[] available, int node) {

		int result;
		if (available[node] == null) {
			throw new RuntimeException("Could not remove node " + node
					+ " from available nodes " + Arrays.toString(available));
		} else {
			int next = Integer.parseInt(available[node]);
			available[node] = null;
			result = next;
		}
		return result;
	}
}
