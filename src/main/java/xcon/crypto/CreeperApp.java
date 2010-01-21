package xcon.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * An application applying the Creeper encryption/decryption algorithm
 * 
 * @author Adriaan
 */
public class CreeperApp {

	/*
	 * Prints the usage instructions to System.out
	 */
	private static void printUsageAndExit() {

		System.out.println("Creeper usage:\n"
				+ "arg1: e (encrypt) or d (decrypt) \n" + "arg2: inputfile \n"
				+ "arg3: key \n");
		System.exit(-1);
	}

	/**
	 * Starts the application
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args == null || args.length < 3) {
			printUsageAndExit();
		}
		String action = args[0];
		String inputFile = args[1];
		String key = args[2];
		if (action == null || inputFile == null || key == null) {
			printUsageAndExit();
		}
		String input = readFile(inputFile);
		String output = null;
		if (action.startsWith("e")) {
			output = Creeper.encrypt(input, key);
		} else if (action.startsWith("d")) {
			output = Creeper.decrypt(input, key);
		} else {
			printUsageAndExit();
		}
		System.out.println(output);
		analyzeText(output, true);
	}

	/*
	 * Reads input from a file
	 */
	public static String readFile(String fileName) throws IOException {

		StringBuilder contents = new StringBuilder();
		File file = new File(fileName);
		if (!file.exists()) {
			throw new IllegalArgumentException("The file was not found: "
					+ fileName);
		}
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				contents.append(line);
			}
		} finally {
			input.close();
		}
		return contents.toString();
	}

	/**
	 * Counts the frequencies of all alpha characters in the text and calculates
	 * the standard deviation
	 * 
	 * @param text
	 */
	public static double analyzeText(String text, boolean debug) {

		// remove all non-alpha characters
		text = text.replaceAll("\\W", "").replaceAll("\\d", "").replaceAll("_",
				"").toUpperCase();
		// count the number of occurrences of each character in the text
		Map<Character, Integer> counts = new TreeMap<Character, Integer>();
		for (Character ch : text.toCharArray()) {

			Integer count = counts.get(ch);
			if (count == null) {
				count = 0;
			}
			count++;
			counts.put(ch, count);
		}

		// calculate the mean
		double tot = 0;
		double[] percentages = new double[counts.keySet().size()];
		int i = 0;
		for (Character ch : counts.keySet()) {

			Integer count = counts.get(ch);
			percentages[i] = (double) (10000 * count / text.length()) / 100.0;
			if (debug) {
				System.err.print(ch + "=" + percentages[i] + "% ");
			}
			tot += percentages[i];
			i++;
		}
		if (debug) {
			System.out.println();
		}

		// calculate the standard deviation
		double mean = tot / counts.keySet().size();
		double variance = 0;
		for (double percentage : percentages) {
			variance += (percentage - mean) * (percentage - mean);
		}
		double stddev = Math.sqrt(variance);
		if (debug) {
			System.err.println("Standard deviation=" + stddev);
		}
		return stddev;
	}
}
