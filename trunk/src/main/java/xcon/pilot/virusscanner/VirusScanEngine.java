package xcon.pilot.virusscanner;

import java.util.Arrays;


public abstract class VirusScanEngine {

	public static void main(String[] args) {
		
		byte[] memory = new byte[] { 'a', 'b', 'c', 'M', 'e', 'l', 'i', 's', 's',
				'a' , 'd', 'e', 'f', 'g'};
		System.out.println("Before: " + Arrays.toString(memory));
		new LinearVirusScanner().scan(memory, Action.DELETE);
		System.out.println("After: " + Arrays.toString(memory));
	}

	public enum Action {
		DELETE, REPORT
	};

	public boolean scan(byte[] memory, Action action) {

		boolean virusFound = false;
		int index = 0;
		while (index < memory.length) {

			int size = findVirus(memory, index);
			if (size > 0) {
				switch (action) {

				case DELETE:
					deleteVirus(memory, index, size);
					break;
				case REPORT:
					reportVirus(memory, index, size);
					break;
				}
				index += size;
			}
			index++;
		}
		return virusFound;
	}

	abstract int findVirus(byte[] memory, int startIndex);

	abstract void reportVirus(byte[] memory, int startIndex, int size);

	abstract void deleteVirus(byte[] memory, int startIndex, int size);
}
