package xcon.stackoverflow;

import java.io.IOException;
import java.io.StringReader;

public class JonSkeetCounter {

	public static void main(String[] args) throws IOException {

		String jonSkeet = "Jon Skeet";
		String q = "How many Skeets does a Jon Skeet Skeet when a Jon Skeet Skeets Jon Skeets?";
		StringReader reader = new StringReader(q);
		int c;
		int t = 0;
		int jonSkeetsFound = 0;
		while ((c = reader.read()) != -1) {
			if (c == jonSkeet.charAt(t)) {
				t++;
				if (t == jonSkeet.length()) {
					t = 0;
					jonSkeetsFound++;
				}
			}
		}
		System.out.println("Jon Skeets found: " + jonSkeetsFound);
	}
}
