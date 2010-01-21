package xcon.pilot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class PhoneWords {

	private static Set<String> words;

	private static final String[][] digits = new String[][] {

	new String[] {}, new String[] {}, new String[] { "a", "b", "c" },
			new String[] { "d", "e", "f" }, new String[] { "g", "h", "i" },
			new String[] { "j", "k", "l" }, new String[] { "m", "n", "o" },
			new String[] { "p", "q", "r", "s" },
			new String[] { "t", "u", "v" }, new String[] { "w", "x", "y", "z" } };

	public static void main(String[] args) throws FileNotFoundException {

		if (args == null || args.length < 2) {
			System.out.println("Geef dictionay file en telefoonnummer");
			return;
		}

		String fileName = args[0];
		readWords(fileName);

		String phoneNumber = args[1];
		printWordsMatching(phoneNumber);

		System.out.println("Done.");
	}

	private static void readWords(String fileName) throws FileNotFoundException {

		words = new HashSet<String>();
		Scanner input = new Scanner(new File(fileName));
		while (input.hasNext()) {

			String word = input.next();
			if (word.length() > 1 && StringUtils.isAlpha(word)) {
				words.add(word.toLowerCase());
			}
		}
	}

	private static void printWordsMatching(String phoneNumber) {

		if (!StringUtils.isNumeric(phoneNumber)) {
			throw new IllegalArgumentException(
					"Phone number can only contain digits");
		}
		Set<String> candidates = new HashSet<String>();
		for (String digit : phoneNumber.split("")) {

			if (digit.equals("")) {
				continue;
			}
			String[] letters = digits[Integer.parseInt(digit)];
			if (letters.length == 0) {
				continue;
			}
			if (candidates.isEmpty()) {

				candidates.addAll(Arrays.asList(letters));
			} else {
				Set<String> newCandidates = new HashSet<String>();
				for (String letter : letters) {

					for (String candidate : candidates) {

						String newCandidate = candidate + letter;
						if (wordsExistStartingWith(newCandidate)) {
							newCandidates.add(newCandidate);
						}
					}
				}
				if (newCandidates.isEmpty()) {
					newCandidates.addAll(Arrays.asList(letters));
				}
				candidates = newCandidates;
			}
			System.out.println(candidates);
		}
	}

	private static boolean wordsExistStartingWith(String startOfWord) {

		for (String word : words) {
			if (word.startsWith(startOfWord)) {
				return true;
			}
		}
		return false;
	}
}
