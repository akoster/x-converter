package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArraySorting {

    public static void main(String[] args) {

        int[] indices = {5, 8, 3, 2};
        double[] relevance = {0.1234, 0.3567, 0.2254, 0.0005};

        ArraySorting app = new ArraySorting();
        app.run(indices, relevance);
    }

    void run(int[] indices, double[] relevance) {
        List<RelevanceIndex> relevanceIndices = getRelevanceIndices(indices, relevance);

        System.out.println(relevanceIndices);
        Collections.sort(relevanceIndices);
        System.out.println(relevanceIndices);
    }

    List<RelevanceIndex> getRelevanceIndices(int[] indices, double[] relevance) {
        List<RelevanceIndex> relevanceIndices = new ArrayList<>();
        for (int i = 0; i < indices.length; i++) {
            relevanceIndices.add(new RelevanceIndex(indices[i], relevance[i]));
        }
        return relevanceIndices;
    }

    class RelevanceIndex implements Comparable<RelevanceIndex> {
        private int index;
        private double relevance;

        RelevanceIndex(int index, double relevance) {
            this.index = index;
            this.relevance = relevance;
        }

        @Override
        public int compareTo(RelevanceIndex other) {
            return Double.compare(this.relevance, other.relevance);
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", index, relevance);
        }
    }
}
