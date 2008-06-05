package xcon.word;

import junit.framework.TestCase;

public class RontonymFinderTest extends TestCase {

    public void testMain1() {
        try {
            RotonymFinder.main(null);
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }

    public void testMain2() {
        try {
            RotonymFinder.main(new String[] {});
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }

    public void testMain3() {
        try {
            RotonymFinder.main(new String[] {
                "english.txt"
            });
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }

    public void testMain4() {
        try {
            RotonymFinder.main(new String[] {
                    "english.txt", "180"
            });
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }

    public void testMain5() {
        try {
            RotonymFinder.main(new String[] {
                    "english.txt", "180", "90", "bla"
            });
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }

    public void testMain6() {
        try {
            RotonymFinder.main(new String[] {
                    "english.txt", null, "180", "90"
            });
        }
        catch (Throwable t) {
            fail("Exception thrown" + t);
        }
    }
}
