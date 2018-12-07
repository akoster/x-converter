package xcon.stackoverflow.matrix;

import java.util.Arrays;

public class IncrementOperation extends Operation {

	private int increment;

	public IncrementOperation(Cell[] cells, int increment) {

		super(cells);
		this.increment = increment;
	}

	@Override
	void execute() {
		Cell[] cells = cells();
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			cell.setValue(cell.getValue() + increment);
			try {
				sleep(30);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Incremented " + Arrays.toString(cells()));
	}
}
