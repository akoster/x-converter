package xcon.stackoverflow;

import java.util.Arrays;
import java.util.List;

/**
 * http://stackoverflow.com/questions/9147454/how-to-change-a-specific-word-in-a-string-to-uppercase/9148243#9148243
 */
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
