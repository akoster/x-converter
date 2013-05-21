package xcon.pilot;

import java.util.Arrays;
import java.util.List;

public class WordUppercaser {

	public static void main(String[] args) {

		List<String> wordsToUppercase = Arrays.asList(new String[] { "lazy",
				"quick" });
		
		String input = "a quick brown fox jumps over a lazy dog.";

		for (String word : wordsToUppercase) {
			input = input.replaceAll(word, word.toUpperCase());
		}

		System.out.println(input);
	}
}
