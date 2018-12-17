package xcon.stackoverflow.calculator;

public class App {
    public static void main(String[] args) {
        Calculator calculator = Calculator.forName("Minus");
        System.out.println(calculator + " seconds = " + calculator.getSeconds());
    }
}
