package xcon.stackoverflow.caclulator;

public enum Calculator {

    PLUS(30),
    MINUS(21),
    DIVIDE(21),
    TIMES(30);

    private int seconds;

    Calculator(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public static Calculator forName(String name) {
        return valueOf(name.toUpperCase());
    }

}