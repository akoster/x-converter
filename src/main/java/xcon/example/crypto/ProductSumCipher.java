package xcon.example.crypto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xcon.example.crypto.Tools.checkExactSize;

public class ProductSumCipher {

    private static final int A = 1;
    private static final int Z = 26;

    public static void main(String[] args) {
        for (int a = A; a <= Z; a++) {
            for (int b = A; b <= Z; b++) {
                int[] input = {a, b};
                int[] encoded = encode(input);
                int count = decode(encoded);
                if (count > 2) {
                    System.out.printf("%s -> %s, %d%n", Arrays.toString(input), Arrays.toString(encoded), count);
                }
            }
        }
    }

    public static int[] encode(int[] input) {
        checkExactSize(input, 2);
        int[] result = new int[2];
        result[0] = input[0] * input[1];
        result[1] = input[0] + input[1];
        return result;
    }

    public static int decode(int[] input) {
        checkExactSize(input, 2);
        int product = input[0];
        int sum = input[1];
        List<int[]> results = new ArrayList<>();
        for (int a = A; a <= Z; a++) {
            for (int b = A; b <= Z; b++) {
                if (a * b == product && a + b == sum) {
                    int[] result = {a, b};
                    results.add(result);
                    System.out.printf("ProductSum=%s -> AB=%s%n", Arrays.toString(input), Arrays.toString(result));
                }
            }
        }
        return results.size();
    }

}



