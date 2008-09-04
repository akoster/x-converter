package xcon.recursion;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {

        int[] data = new int[] {
                3,2,1
        };

        System.out.println("unsorted:" + Arrays.toString(data));

        sort(data, 0);

        System.out.println("sorted:" + Arrays.toString(data));
    }

    private static void sort(int[] data, int depth) {

        depth++;
        
        System.out.println(tab(depth) + "Sorting " + Arrays.toString(data));

        // stopcondition: only one element
        if (data.length == 1) {

            System.out.println(tab(depth) + "Already sorted "
                + Arrays.toString(data));
            return;
        }

        // split in two halves
        int leftLength = data.length / 2;
        int[] left = new int[leftLength];
        System.arraycopy(data, 0, left, 0, leftLength);
        System.out.println(tab(depth) + "Left " + Arrays.toString(left));

        int rightLength = data.length - leftLength;
        int[] right = new int[rightLength];
        System.arraycopy(data, leftLength, right, 0, rightLength);
        System.out.println(tab(depth) + "Right" + Arrays.toString(right));

        // sort each half recursively
        System.out.println(tab(depth) + "Sorting left");
        sort(left, depth);
        System.out.println(tab(depth) + "Sorting right");
        sort(right, depth);

        // merge two sorted halves
        System.out.println(tab(depth) + "Merging " + Arrays.toString(left)
            + " and " + Arrays.toString(right));
        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < data.length; i++) {

            boolean stillElementsInRight = (rightIndex < right.length);
            boolean stillElementsInLeft = (leftIndex < left.length);
            boolean readFromLeft;
            if (stillElementsInLeft && stillElementsInRight) {

                int leftValue = left[leftIndex];
                int rightValue = right[rightIndex];
                readFromLeft = leftValue <= rightValue;
            }
            else {
                readFromLeft = stillElementsInLeft;
            }
            if (readFromLeft) {
                data[i] = left[leftIndex];
                leftIndex++;
            }
            else {
                data[i] = right[rightIndex];
                rightIndex++;
            }
        }

        System.out.println(tab(depth) + "Sorted: " + Arrays.toString(data));
    }

    public static String tab(int size) {
        String tab = "";
        for (int i = 0; i < size * 4; i++) {
            tab += " ";
        }
        return tab;
    }
}
