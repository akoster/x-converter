package xcon.crypto;

import java.util.Arrays;

/**
 * A simple encryption/decryption algorithm.
 * 
 * THIS ALGORITHM HAS NOT BEEN PROVEN TO BE SECURE. DON'T USE FOR THE PROTECTION
 * OF SENSITIVE DATA.
 * 
 * @author Adriaan Koster <info@adriaankoster.nl>
 */
public class Creeper {

	public static final int PLAINTEXT_RANGE = Character.MAX_VALUE;
	public static char CRYPTEX_EMPTY_CHAR = 32;
	public static char CRYPTEX_MIN_CHAR = 65;
	public static int CRYPTEX_CHAR_RANGE = 26;

	private static final boolean DEBUG = false;

	/**
	 * Encrypts the plainText using the given key.
	 * 
	 * @param plainText
	 * @param key
	 * @return a cryptex representing the plainText
	 */
	public static String encrypt(String plainText, String key) {

		// each plaintext char generates key.length() cryptex chars
		int cryptexSize = plainText.length() * key.length();
		char[] cryptex = new char[cryptexSize];
		Arrays.fill(cryptex, CRYPTEX_EMPTY_CHAR);

		// determine the starting position
		long num = nGramToNum(key);
		int pos = (int) (num % cryptex.length);

		// encrypt each plaintext character
		for (char plainTextChar : plainText.toCharArray()) {

			// write an nGram at the current pos, and generate the next pos
			pos = writeNgram(cryptex, pos, plainTextChar, key);
		}
		return new String(cryptex);
	}

	/*
	 * generates and writes an nGram into the cryptex from the given start
	 * position. The nGram represents the given plainText character as well as
	 * the position of the next nGram
	 */
	private static int writeNgram(char[] cryptex, int startPos,
			char plainTextChar, String key) {

		// Find a number which encodes both the character and the next position.
		//
		// The largest number we can encode in an nGram
		long maxNum = (long) Math.pow(CRYPTEX_CHAR_RANGE, key.length()) - 1;
		// The maximum multiples of PLAINTEXT_RANGE that will fit in maxNum
		long maxFactor = maxNum / PLAINTEXT_RANGE;
		// pick a random factor within the boundaries
		long randomFactor = (long) (Math.random() * maxFactor);
		// calculate the number so that it has the target char as the modulus
		long num = plainTextChar + (randomFactor * PLAINTEXT_RANGE);

		// encode the number as an nGram and write it into the cryptex
		//
		// the positional value of the first nGram digit
		long posValue = (long) Math.pow(CRYPTEX_CHAR_RANGE, key.length() - 1);
		int pos = startPos;
		long remain = num;
		for (int i = 0; i < key.length(); i++) {

			// skip non-empty position
			pos = findNextFreePosition(cryptex, pos);
			// convert characters to make the cryptex more readable
			cryptex[pos] = (char) ((remain / posValue) + CRYPTEX_MIN_CHAR);
			remain = remain % posValue;
			posValue = posValue / CRYPTEX_CHAR_RANGE;
		}
		if (DEBUG) {
			System.out.println(new String(cryptex) + "\t" + num + "\t" + pos
					+ "\t" + (int) plainTextChar);
		}
		// calculate and return the next position
		// we don't care if the position is non-empty
		int nextPos = (int) (num % cryptex.length);
		return nextPos;
	}

	/*
	 * Finds the next free position in the cryptex from the given start
	 * position. If the end of the cryptex is reached, continues searching from
	 * the beginning up to the start position.
	 */
	private static int findNextFreePosition(char[] cryptex, int startPos) {

		// if out of range, start at the beginning
		if (startPos >= cryptex.length) {
			startPos = 0;
		}

		// walk from startPos to end
		int nextPos = startPos;
		while (nextPos < cryptex.length
				&& cryptex[nextPos] != CRYPTEX_EMPTY_CHAR) {
			nextPos++;
		}

		// if not found
		if (nextPos == cryptex.length) {

			// walk from begin to startPos
			nextPos = 0;
			while (nextPos < startPos && cryptex[nextPos] != CRYPTEX_EMPTY_CHAR) {
				nextPos++;
			}
		}

		// if still not found
		if (cryptex[nextPos] != CRYPTEX_EMPTY_CHAR) {
			throw new IllegalArgumentException("Could not find free position: "
					+ cryptex.toString());
		}
		return nextPos;
	}

	/**
	 * Decrypts the cryptex using the given key.
	 * 
	 * @param cryptex
	 * @param key
	 * @return the plaintext represented by the cryptex
	 */
	public static String decrypt(String cryptexStr, String key) {

		// the cryptex must be a multiple of the key.length()
		if (cryptexStr.length() % key.length() != 0) {
			throw new IllegalArgumentException(
					"Crypto text size does not match key size");
		}

		// set up the cryptex and the plaintext
		char[] cryptex = cryptexStr.toCharArray();
		int plainTextSize = cryptexStr.length() / key.length();
		String plainText = "";

		// determine the starting position
		long num = nGramToNum(key);
		int pos = (int) (num % cryptex.length);

		// read the plaintext from the cryptex
		while (plainText.length() < plainTextSize) {

			// decode the next n-gram
			num = readNgram(cryptex, pos, key);
			pos = (int) (num % cryptex.length);
			char plainTextChar = (char) (num % PLAINTEXT_RANGE);
			if (DEBUG) {
				System.out.println(new String(cryptex) + "\t" + num + "\t"
						+ pos + "\t" + (int) plainTextChar);
			}
			plainText += plainTextChar;
		}
		return plainText;
	}

	/*
	 * Reads an n-gram from the cryptex, erasing it so it won't be read again.
	 * Reading starts at the given start position and skips over erased
	 * positions. If the end of the cryptex is reached, continues reading from
	 * the beginning.
	 */
	private static long readNgram(char[] cryptex, int startPos, String key) {

		String nGram = "";
		int pos = startPos;
		while (nGram.length() < key.length()) {

			// only read non-empty positions
			if (cryptex[pos] != CRYPTEX_EMPTY_CHAR) {

				// convert the cryptex character back to 0-based
				nGram += (char) (cryptex[pos] - CRYPTEX_MIN_CHAR);
				// remove to avoid reading it again
				cryptex[pos] = CRYPTEX_EMPTY_CHAR;
			}
			pos++;
			if (pos >= cryptex.length) {
				pos = 0;
			}
		}
		long num = nGramToNum(nGram);
		return num;
	}

	/*
	 * Converts an nGram into a number
	 */
	private static long nGramToNum(String nGram) {

		long num = 0;
		long posValue = 1;
		// start with the least significant digit (right-to-left)
		for (int i = nGram.length() - 1; i >= 0; i--) {

			char nGramChar = nGram.charAt(i);
			num += nGramChar * posValue;
			posValue = posValue * CRYPTEX_CHAR_RANGE;
		}
		return Math.abs(num);
	}
}