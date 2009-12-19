package xcon.lottery;

import org.junit.Test;

public class LotteryAppTest {

	@Test
	public void testLotteryApp() {

		LotteryApp.run(new String[] {
				"Jut=jut@blunderboy.nl+Jul=jul@blunderboy.nl",
				"Tut=tut@blunderboy.nl+Hola=hola@blunderboy.nl" }, false);
	}

}
