package xcon.example.gauss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gauss {

    private Random RANDOM = new Random();

    private int throwDice() {
        return RANDOM.nextInt(1, 7);
    }

    public List<Double> getValues(int numberOfDice) {
        int highestThrow = numberOfDice * 6;
        int[] totals = new int[highestThrow + 1];
        int max = 0;
        int numberOfThrows = 10000000;
        for (int n = 0; n < numberOfThrows; n++) {
            int sum = 0;
            for (int m = 0; m < numberOfDice; m++) {
                sum = sum + throwDice();
            }
            totals[sum] = totals[sum] + 1;
            if (totals[sum] > max) {
                max = totals[sum];
            }
        }

        int scale = max / 80;

        List<Double> values = new ArrayList<>();

        for (int i = numberOfDice; i <= highestThrow; i++) {
            double percentage = Math.round(((double) totals[i] / (double) numberOfThrows) * 100.0 * 1000.0) / 1000.0;
            values.add(percentage);
            printStars(totals, i, scale, percentage);
        }

        return values;
    }

    private static void printStars(int[] totals, int i, int scale, double percentage) {
        int total = totals[i] / scale;
        System.out.printf("%-3s - %-8s", i, percentage);
        for (int j = 1; j <= total; j++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
