package xcon.lottery;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LotteryTest {

	@Test
	public void testLottery7() {

		Lottery lottery = new Lottery(LotteryApp.extractPersons(new String[] {
				"Jut+Jul", "Tut+Hola", "Mies", "Teun", "Gijs" }));
		lottery.draw();
	}

	@Test
	public void testCycleDistribution() {

		long startTime = System.currentTimeMillis();
		long now = startTime;
		int durationSeconds = 10;
		int size = 4;
		int[] cycleSizeCount = new int[size + 1];
		Arrays.fill(cycleSizeCount, 0);
		int runCount = 0;
		while (now - startTime < durationSeconds * 1000) {

			String[] args = new String[size];
			Arrays.fill(args, "node");
			Lottery lottery = new Lottery(LotteryApp.extractPersons(args));
			List<Person> result = lottery.draw();

			Person start = null;
			int cycleSize = 0;
			Person current = null;
			while (result.size() > 0) {

				if (start == null) {

					start = result.remove(0);
					current = start;
					cycleSize = 1;
				}

				// get next node
				current = current.getTarget();
				result.remove(current);
				cycleSize++;

				if (current.getTarget() == start) {

					cycleSizeCount[cycleSize]++;
					start = null;
				}
			}
			runCount++;
			now = System.currentTimeMillis();
		}

		System.out.println("runCount=" + runCount);
		System.out.println(Arrays.toString(cycleSizeCount));

		for (int i = 1; i < cycleSizeCount.length; i++) {
			System.out.println(i + " " + cycleSizeCount[i] + " "
					+ ((double) cycleSizeCount[i]) / ((double) runCount));
		}
	}
}
