package xcon.pilot;

import java.io.IOException;
import java.io.StringReader;

public class StringLength {
	public static void main(String arg[]) throws IOException {

		System.out.println(new StringReader("stackoverflow")
				.skip(Long.MAX_VALUE));
	}
}
