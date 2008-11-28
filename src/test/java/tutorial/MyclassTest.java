package tutorial;

import static org.junit.Assert.*;
import org.junit.Test;


public class MyclassTest {

    @Test
    public void testMultiply() {
        
        Myclass tester = new Myclass(); 
        assertEquals("result", 50, tester.multiply(5, 10));
    }

}
