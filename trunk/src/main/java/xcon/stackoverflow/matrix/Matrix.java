package xcon.stackoverflow.matrix;

import java.util.Arrays;

public class Matrix {

	private Cell[][] cells;

	public Matrix(int rows, int cols) {

		if (rows == 0 || cols == 0) {
			throw new IllegalArgumentException(
					"Rows and cols should both be > 0");
		}
		cells = new Cell[rows][cols];
	}

	public int getRows() {
		return cells.length;
	}

	public int getCols() {
		return cells[0].length;
	}

	public Cell[] getRow(int row) {
		return cells[row];
	}

	public void setRow(int row, int... values) {

		if (values.length != cells[0].length) {
			throw new IllegalArgumentException(String.format(
					"Bad number of rows:%s should be %s", values.length,
					cells[0].length));
		}
		for (int col = 0; col < cells[0].length; col++) {

			if (cells[row][col] == null) {
				cells[row][col] = new Cell();
			}
			cells[row][col].setValue(values[col]);
		}
	}

	public Cell getCell(int row, int col) {
		return cells[row][col];
	}

	public void setCell(int row, int col, int value) {

		if (cells[row][col] == null) {
			cells[row][col] = new Cell();
		}
		cells[row][col].setValue(value);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < cells.length; row++) {
			sb.append(Arrays.toString(cells[row])).append(
					System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
