package xcon.codility;

public class OrderedTrees {

    private final int[] treeSizes;
    private int unorderedPairIndex;
    private int unorderedPairCount;

    public OrderedTrees(int[] treeSizes) {
        this.treeSizes = treeSizes;
    }

    public int solution(int[] treeSizes) {
        return new OrderedTrees(treeSizes).solve();
    }

    private int solve() {
        findUnorderedPairs();
        if (unorderedPairCount == 0) {
            return treeSizes.length;
        } else if (unorderedPairCount > 1) {
            return 0;
        } else {
            return numberOfPossibilities();
        }
    }

    private void findUnorderedPairs() {
        for (int index = 0; index < treeSizes.length - 1; index++) {
            if (isUnordered(index, index + 1)) {
                unorderedPairIndex = index;
                unorderedPairCount++;
                if (unorderedPairCount > 1) {
                    break;
                }
            }
        }
    }

    private int numberOfPossibilities() {
        int possibilities = 2;

        if (neighborsAreUnordered(unorderedPairIndex)) {
            possibilities--;
        }

        if (neighborsAreUnordered(unorderedPairIndex + 1)) {
            possibilities--;
        }

        return possibilities;
    }

    private boolean neighborsAreUnordered(int index) {
        return index - 1 >= 0 && index + 1 < treeSizes.length && isUnordered(index - 1, index + 1);
    }

    private boolean isUnordered(int left, int right) {
        return treeSizes[left] > treeSizes[right];
    }

}
