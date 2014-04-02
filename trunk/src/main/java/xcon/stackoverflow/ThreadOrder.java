package xcon.stackoverflow;

/**
 * http://stackoverflow.com/questions/7378071/printing-threads-in-strict-order/7380685#7380685
 */
public class ThreadOrder {

	private static final Object monitor = new Object();

	public static class WaitingForMyTurn extends Thread {

		private static volatile Integer currentNumber = 0;
		private Integer myNumber;

		public WaitingForMyTurn(Integer number) {
			this.myNumber = number;
		}

		public void run() {

			while (currentNumber < myNumber) {
			}
			System.out.println(myNumber);
			currentNumber = myNumber + 1;
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new WaitingForMyTurn(i).start();
		}
	}
}
