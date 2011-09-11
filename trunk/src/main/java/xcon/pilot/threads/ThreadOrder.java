package xcon.pilot.threads;

public class ThreadOrder {

	public static class WaitingForMyTurn extends Thread {

		private static Integer currentNumber = 1;
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
