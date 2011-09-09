package xcon.pilot.threads.matrix;

import java.util.Arrays;

public class IncrementOperation extends Operation {
	private int increment;

	public IncrementOperation(Cell[] cells, int increment) {
		super(cells);
		this.increment = increment;
	}

	@Override
	void execute() {
		for (int i = 0; i < cells().length; i++) {
			cells()[i].setValue(cells()[i].getValue() + increment);
			try {
				sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Incremented " + Arrays.toString(cells()));
	}
}
