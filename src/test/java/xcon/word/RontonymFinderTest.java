package xcon.word;

import junit.framework.TestCase;

public class RontonymFinderTest extends TestCase {

	public void testMain1() {
		RotonymFinderApp.main(null);
	}

	public void testMain2() {
		RotonymFinderApp.main(new String[] {});
	}

	public void testMain3() {
		RotonymFinderApp.main(new String[] { "english.txt" });
	}

	public void testMain4() {
		RotonymFinderApp.main(new String[] { "english.txt", "180" });
	}

	public void testMain5() {
		RotonymFinderApp
				.main(new String[] { "english.txt", "180", "90", "bla" });
	}
}
