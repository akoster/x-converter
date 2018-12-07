package xcon.stackoverflow.matrix;

import java.util.Arrays;

public class ReverseOperation extends Operation {

	public ReverseOperation(Cell[] cells) {
		super(cells);
	}

	@Override
	void execute() {
		Cell[] cells = cells();
		for (int i = 0; i < (cells.length / 2); i++) {
			Cell a = cells[i];
			Cell b = cells[cells.length - i - 1];
			int tmp = a.getValue();
			a.setValue(b.getValue());
			b.setValue(tmp);
			try {
				sleep(30);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Reversed " + Arrays.toString(cells()));
	}
}
