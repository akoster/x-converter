package xcon.stackoverflow.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * http://stackoverflow.com/questions/7351073/java-how-to-synchronize-array-accesses-and-what-are-the-limitations-on-what-goe/7351268#7351268
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		Matrix matrix = createMatrix();
		System.out.println(matrix);

		List<Operation> operations = createOperations(matrix);

		startOperations(operations);
		Thread.sleep(1000);
		System.out.println(matrix);
	}

	private static Matrix createMatrix() {
		Matrix matrix = new Matrix(10, 5);
		matrix.setRow(0, 1, 2, 3, 4, 5);
		matrix.setRow(1, 6, 7, 8, 9, 10);
		matrix.setRow(2, 11, 12, 13, 14, 15);
		matrix.setRow(3, 16, 17, 18, 19, 20);
		matrix.setRow(4, 21, 22, 23, 24, 25);
		matrix.setRow(5, 26, 27, 28, 29, 30);
		matrix.setRow(6, 31, 32, 33, 34, 35);
		matrix.setRow(7, 36, 37, 38, 39, 40);
		matrix.setRow(8, 41, 42, 43, 44, 45);
		matrix.setRow(9, 46, 47, 48, 49, 50);
		return matrix;
	}

	private static List<Operation> createOperations(Matrix matrix) {
		List<Operation> operations = new ArrayList<Operation>();
		for (int row = 0; row < matrix.getRows(); row++) {
			operations.add(new ReverseOperation(matrix.getRow(row)));
			operations.add(new IncrementOperation(matrix.getRow(row), 100));
		}
		return operations;
	}

	private static void startOperations(List<Operation> operations) {
		for (Thread operation : operations) {
			operation.start();
		}
	}
}
