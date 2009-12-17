package xcon.lottery;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import xcon.lottery.Lottery.Node;

public class LotteryAppTest {

	@Test
	public void testLotteryApp() {

			new Lottery(new String[] {
				"Jut=jut@blunderboy.nl+Jul=jul@blunderboy.nl",
				"Tut=tut@blunderboy.nl+Hola=hola@blunderboy.nl" }).draw();
	}

	@Test
	public void testDistribution() {

		long startTime = System.currentTimeMillis();
		long now = startTime;
		int durationSeconds = 10;
		int size = 5;
		int[] cycleSizeCount = new int[size + 1];
		Arrays.fill(cycleSizeCount, 0);
		int runCount = 0;
		while (now - startTime < durationSeconds * 1000) {

			String[] args = new String[size];
			Arrays.fill(args, "node");
			List<Node> result = new Lottery(args).draw();

			Node start = result.remove(0);
			int cycleSize = 1;
			for (Node node : result) {

				cycleSize++;
				if (node.target == start) {

					cycleSizeCount[cycleSize]++;
					cycleSize = 0;
				}
			}
			runCount++;
			now = System.currentTimeMillis();
		}

		System.out.println("runCount=" + runCount);
		System.out.println(Arrays.toString(cycleSizeCount));

		for (int i = 0; i < cycleSizeCount.length; i++) {
			System.out.println(i + " " + cycleSizeCount[i] + " "
					+ ((double) cycleSizeCount[i]) / ((double) runCount));
		}
	}
}
