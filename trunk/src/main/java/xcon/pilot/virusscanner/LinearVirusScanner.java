package xcon.pilot.virusscanner;


public class LinearVirusScanner extends VirusScanEngine {

	private static final byte[][] virusSignatures = new byte[][] {
			new byte[] { 'I', 'L', 'O', 'V', 'E', 'Y', 'O', 'U' },
			new byte[] { 'M', 'e', 'l', 'i', 's', 's', 'a' } };

	@Override
	int findVirus(byte[] memory, int startIndex) {

		int size = 0;
		signatures: for (int v = 0; v < virusSignatures.length; v++) {

			scan: {
				for (int t = 0; t < virusSignatures[v].length; t++) {

					if (memory[startIndex + t] != virusSignatures[v][t]) {
						break scan;
					}
				}
				// virus found
				size = virusSignatures[v].length;
				break signatures;
			}
		}
		return size;
	}

	@Override
	void deleteVirus(byte[] memory, int startIndex, int size) {

		for (int n = startIndex; n < startIndex + size - 1; n++) {
			memory[n] = 0;
		}
	}

	@Override
	void reportVirus(byte[] memory, int startIndex, int size) {

		System.out.println("Virus found at position " + startIndex
				+ " with length " + size);
	}
}
