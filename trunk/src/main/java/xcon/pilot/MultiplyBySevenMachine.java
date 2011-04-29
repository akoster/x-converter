package xcon.pilot;

import java.util.Scanner;

public class MultiplyBySevenMachine {

	public static void main(String[] args) {

		MultiplyBySevenMachine multiplyBySevenMachine = new MultiplyBySevenMachine();
		multiplyBySevenMachine.start();
	}

	private void start() {
		while (true) {
			int input = readInput();
			int result = calculateResult(input);
			writeOutput(result);
		}
	}

	private int readInput() {
		Scanner console = new Scanner(System.in);
		System.out.print("Please type a number: ");
		int number = console.nextInt();
		return number;
	}

	private int calculateResult(int input) {
		int result = input * 7;
		return result;
	}

	private void writeOutput(int result) {
		System.out.println("The result is: " + result);
	}
}
