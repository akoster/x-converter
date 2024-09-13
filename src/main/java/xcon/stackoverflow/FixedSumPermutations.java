package xcon.stackoverflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class FixedSumPermutations {

    private PermutationProcessor processor;
    private long partitionCount;
    private long permutationCount;

    private class PermutationProcessor implements Consumer<int[]> {

        private double bestScore;
        private String bestPermutation;

        @Override
        public void accept(int[] permutation) {
            double standardDeviation = calculateStandardDeviation(permutation);
            if (bestPermutation == null || standardDeviation < bestScore) {
                bestScore = standardDeviation;
                bestPermutation = Arrays.toString(permutation);
                System.out.printf("\rpartitions:%s permutations:%s %s", partitionCount, permutationCount, this);
            }
        }

        @Override
        public String toString() {
            return String.format("bestPermutation:%s standardDeviation:%s", bestPermutation, bestScore);
        }

        public double calculateStandardDeviation(int[] array) {

            double sum = 0.0;
            for (int i : array) {
                sum += i;
            }

            int length = array.length;
            double mean = sum / length;

            double standardDeviation = 0.0;
            for (double num : array) {
                standardDeviation += Math.pow(num - mean, 2);
            }

            return Math.sqrt(standardDeviation / length);
        }

    }

    private void findPartitions(int targetSum, int size) {

        processor = new PermutationProcessor();
        partitionCount = 0;
        permutationCount = 0;

        findPartitions(size, targetSum, targetSum, new ArrayList<>());
    }

    private void findPartitions(int positions, int remaining, int max, List<Integer> result) {
        if (remaining == 0) {
            List<Integer> partition = new ArrayList<>(result);
            while (partition.size() < positions) {
                partition.add(0, 0);
            }
            partitionCount++;
            findPermutations(partition);
            return;
        }
        if (result.size() == positions) {
            return;
        }
        for (int i = Math.min(max, remaining); i >= 1; i--) {
            List<Integer> next = new ArrayList<>(result);
            next.add(i);
            findPartitions(positions, remaining - i, i, next);
        }
    }

    private void findPermutations(List<Integer> value) {
        int[] permutation = value.stream().mapToInt(i -> i).toArray();
        Arrays.sort(permutation);
        int changePos;
        do {
            processor.accept(permutation);
            permutationCount++;
            changePos = walkDown(permutation.length - 2, permutation);

            if (changePos >= 0) {

                int swapPos = walkUp(changePos + 1, permutation, changePos);

                swap(permutation, changePos, swapPos);
                for (int i = changePos + 1, j = permutation.length - 1; i < j; ++i, --j) {
                    swap(permutation, i, j);
                }
            }
        } while (changePos >= 0);
    }

    private int walkUp(int swapPos, int[] chars, int changePos) {
        while (swapPos + 1 < chars.length && chars[swapPos + 1] > chars[changePos]) {
            ++swapPos;
        }
        return swapPos;
    }

    private int walkDown(int changePos, int[] chars) {
        while (changePos >= 0 && chars[changePos] >= chars[changePos + 1]) {
            --changePos;
        }
        return changePos;
    }

    private void swap(int[] chars, int changePos, int swapPos) {
        Integer temp = chars[changePos];
        chars[changePos] = chars[swapPos];
        chars[swapPos] = temp;
    }

    public static void main(String[] args) throws IOException {
        FixedSumPermutations partitions = new FixedSumPermutations();
        partitions.findPartitions(213, 6);
    }
}
