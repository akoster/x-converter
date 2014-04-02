package xcon.stackoverflow;

import java.util.Random;

/**
 * I have a sorted array {2.0,7.8,9.0,10.5,12.3}
 * If I given an input 9.5 What is the fastest way to find 9.0 and 10.5 to indicate that 9.5 is
 * in between 9.0 and 10.5 (9.5 >=9.0 and <10.5) ? Is binary search an option?
 * But since the input need not be in the array.I'm not sure how I should do this.
 *
 * http://stackoverflow.com/questions/3520261/find-range-of-a-number-from-an-array/3522842#3522842
 */
public class RangeFinder {

	private void find(double query, double[] data) {

		if (data == null || data.length == 0) {
			throw new IllegalArgumentException("No data");
		}

		System.out.print("query " + query + ", data " + data.length + " : ");

		Result result = new Result();
		int max = data.length;
		int min = 0;
		while (result.lo == null && result.hi == null) {

			int pos = (max - min) / 2 + min;
			if (pos == 0 && query < data[pos]) {
				result.hi = pos;
			} else if (pos == (data.length - 1) && query >= data[pos]) {
				result.lo = pos;
			} else if (data[pos] <= query && query < data[pos + 1]) {
				result.lo = pos;
				result.hi = pos + 1;
			} else if (data[pos] > query) {
				max = pos;
			} else {
				min = pos;
			}
			result.iterations++;
		}
		result.print(data);
	}

	private class Result {

		Integer lo;
		Integer hi;
		int iterations;
		long start = System.nanoTime();

		void print(double[] data) {
			System.out.println(

					(lo == null ? "" : data[lo] + " <= ") +

							"query" +

							(hi == null ? "" : " < " + data[hi]) +

							" (" + iterations + " iterations in " +

							((System.nanoTime() - start) / 1000000.0) + " ms. )"
			);
		}
	}

	public static void main(String[] args) {

		RangeFinder rangeFinder = new RangeFinder();

		// test validation
		try {
			rangeFinder.find(12.4, new double[]{});
			throw new RuntimeException("Validation failed");
		} catch (IllegalArgumentException e) {
			System.out.println("Validation succeeded");
		}
		try {
			rangeFinder.find(12.4, null);
			throw new RuntimeException("Validation failed");
		} catch (IllegalArgumentException e) {
			System.out.println("Validation succeeded");
		}

		// test edge cases with small data set
		double[] smallDataSet = new double[]{2.0, 7.8, 9.0, 10.5, 12.3};
		rangeFinder.find(0, smallDataSet);
		rangeFinder.find(2.0, smallDataSet);
		rangeFinder.find(7.9, smallDataSet);
		rangeFinder.find(10.5, smallDataSet);
		rangeFinder.find(12.3, smallDataSet);
		rangeFinder.find(10000, smallDataSet);

		// test performance with large data set
		System.out.print("Preparing large data set...");
		Random r = new Random();
		double[] largeDataSet = new double[20000000];
		largeDataSet[0] = r.nextDouble();
		for (int n = 1; n < largeDataSet.length; n++) {
			largeDataSet[n] = largeDataSet[n - 1] + r.nextDouble();
		}
		System.out.println("done");
		rangeFinder.find(0, largeDataSet);
		rangeFinder.find(5000000.42, largeDataSet);
		rangeFinder.find(20000000, largeDataSet);
	}
}
