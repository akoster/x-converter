package xcon.pilot.threads.matrix;

public abstract class Operation extends Thread implements Runnable {

	private final Cell[] cells;

	public Operation(Cell[] cells) {

		if (cells == null) {
			throw new IllegalArgumentException("Cells may not be null");
		}
		this.cells = cells;
	}

	@Override
	public void run() {

		synchronized (cells) {
			execute();
		}
	}

	Cell[] cells() {
		return cells;
	}

	abstract void execute();
}
