package xcon.pilot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class attempts to find different words with the same hashcode by
 * comparing hashcodes of words from a text file.
 * 
 * @author Adriaan
 */
public class HashCodeCollisionFinder {

	public static void main(String[] args) {

		List<String> words = readInput(args);

		System.out.println("Checking " + words.size() + " words");
		long lastTimeLogged = 0;
		double total = (double) words.size();
		for (int n = 0; n < words.size(); n++) {

			String word1 = words.get(n);

			lastTimeLogged = logProgress(lastTimeLogged, total, n);

			for (int m = n; m < words.size(); m++) {

				String word2 = words.get(m);
				if (!word1.equals(word2)
						&& word1.hashCode() == word2.hashCode()) {
					System.out.println(word1 + " = " + word2);
				}
			}
		}
		// log one last time to show 100%
		logProgress(0, total, words.size());
	}

	private static List<String> readInput(String[] args) {
		
		String inputFile;
		if (args == null || args[0] == null) {
			inputFile = "doc/english.txt";
		} else {
			inputFile = args[0];
		}
		List<String> words = readWords(inputFile);
		return words;
	}

	private static List<String> readWords(String fileName) {

		List<String> words = new ArrayList<String>();
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			words = null;
		}

		while (input.hasNext()) {
			String word = input.next();
			if (word.length() > 1) {
				words.add(word.toLowerCase());
			}
		}
		return words;
	}
	
	private static long logProgress(long last, double total, int n) {

		long now = System.currentTimeMillis();
		if (now - last > 5000) {
			double progress = Math.floor(1000.0 * ((double) n) / total) / 10;
			System.out.println(progress + " %");
			last = now;
		}
		return last;
	}

}
