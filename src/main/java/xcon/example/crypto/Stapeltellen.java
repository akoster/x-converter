package xcon.example.crypto;

public class Stapeltellen {

    public static void main(String[] args) {
        for (int i = 32; i < 126; i++) {
            int group = (i - 23) / 9;
            System.out.printf("%d -> %d (%d)%n", i, staptellen(i), group);
        }
    }

    public static int staptellen(int input) {
        int sum = input;
        while (sum > 9) {
            String digits = String.valueOf(sum);
            sum = 0;
            for (int d : digits.toCharArray()) {
                int digit = d - '0';
                sum += digit;
            }
        }
        return sum;
    }
}
