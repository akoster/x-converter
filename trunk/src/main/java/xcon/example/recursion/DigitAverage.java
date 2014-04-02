package xcon.example.recursion;

public class DigitAverage {

	public static void main(String[] args) {
		System.out.println(digitAverage(12345));
	}

	private static double digitAverage(int remainder, int... depth) {

		boolean first = false;
		if (depth.length == 0) {
			depth = new int[] { 1 };
			first = true;
		} else {
			depth[0] = depth[0] + 1;
		}
		if (remainder < 10) {
			return remainder;
		}
		double result = remainder % 10 + digitAverage(remainder / 10, depth);
		if (first) {
			result = result / depth[0];
		}
		return result;
	}
}
