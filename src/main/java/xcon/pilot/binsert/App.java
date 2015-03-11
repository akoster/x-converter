package xcon.pilot.binsert;

/**
 * Binary 2-3 tree
 */
public class App {

	public static void main(String[] args) {
		int[] values = new int[]{5, 2, 11, 1, 4, 6, 10};
		Node tree = new Node();
		for (int value : values) {
			tree.add(value);
			tree = tree.top();
			System.out.println("----");
			System.out.println(tree);
			System.out.println("----");
		}
		System.out.println("done");
	}
}
