package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Recursive solution for a variation on the 'knapsack' problem.
 *
 * http://stackoverflow.com/questions/5817444/recursive-backtracking-help/5823577#5823577
 */
public class SteelMill {

	private int maximumLength;
	private Order best;

	public SteelMill() {
		Order ordered = new Order(3, 4, 1, 6, 2, 5);
		Order filled = new Order();
		maximumLength = 7;
		fillOrder(ordered, filled, 0);
		System.out.println("best solution found: " + best + " waste = "
				+ (maximumLength - best.total()));
	}

	private void fillOrder(Order ordered, Order filled, int depth) {
		print(depth, "ordered = " + ordered + " filled = " + filled);
		depth++;
		if (filled.total() > maximumLength) {
			return;
		}
		if (filled.total() == maximumLength || best == null
				|| filled.total() > best.total()) {
			best = filled;
			if (filled.total() == maximumLength) {
				System.out.println("perfect solution found: " + filled);
			}
		}
		for (Integer bar : ordered) {
			Order childOrdered = new Order(ordered);
			childOrdered.remove(bar);
			Order childFilled = new Order(filled);
			childFilled.add(bar);
			fillOrder(childOrdered, childFilled, depth);
		}
	}

	public class Order extends ArrayList<Integer> {
		private static final long serialVersionUID = 1L;

		public Order(Order toCopy) {
			super(toCopy);
		}

		public Order(Integer... values) {
			for (Integer value : values) {
				add(value);
			}
		}

		public int total() {
			int total = 0;
			for (Integer value : this) {
				if (value != null) {
					total += value;
				}
			}
			return total;
		}

		public String toString() {
			return Arrays.toString(toArray());
		}
	}

	private static void print(int depth, String msg) {
		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			tabs.append("  ");
		}
		System.out.println(tabs + msg);
	}

	public static void main(String[] args) {
		new SteelMill();
	}
}
