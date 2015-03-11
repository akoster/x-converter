package xcon.example.logfile;

public class Finder {

	String[] lines;

	public static void main(String[] args) {
		Finder finder = new Finder(args);
		int oldestPosition = finder.findOldestPosition();
		System.out.printf("Oldest entry found at position %s: %s%n", oldestPosition,args[oldestPosition]);
	}

	public Finder(String[] lines) {
		this.lines = lines;
	}

	public int findOldestPosition() {
		if (lines.length == 0) {
			throw new RuntimeException("No lines found");
		}
		int start = 0;
		int end = lines.length - 1;
		if (start == end) {
			return start;
		}
		// invariant: start < end
		return findOldestPosition(start, end);
	}

	public int findOldestPosition(int start, int end) {
		long startTime = timestamp(start);
		long endTime = timestamp(end);
		if (startTime < endTime) {
			// stop: there are no older positions than start
			return start;
		}
		if ((end - start) == 1) {
			// stop: positions are adjacent
			return end;
		}
		int mid = selectPositionBetween(start, end);

		long midTime = timestamp(mid);
		if (midTime < startTime) {
			return findOldestPosition(start, mid);
		} else {
			return findOldestPosition(mid, end);
		}
	}

	private int selectPositionBetween(int start, int end) {
		return (end - start) / 2 + start;
	}

	long timestamp(int position) {
		final int positionOfTimestampInLine = 0;
		return Long.parseLong(lines[position].split(",")[positionOfTimestampInLine]);
	}
}
