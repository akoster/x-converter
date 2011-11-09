package xcon.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * See http://www.paulspages.co.uk/sudoku/howtosolve
 */
public class SudokuSolver {

    private static final String puzzle1 = "080402060034000910960000084000216000000000000000357000840000075026000130090701040";
    private static final String puzzle2 = "080904500000000180560300947293100004000702000400003612839005061046000000002601030";
    private static final String puzzle3 = "000200063300005401001003980000000090000538000030000000026300500503700008470001000";

    public static void main(String[] args) {

        Board board = new Board(puzzle3);
        System.out.println(board);
        solve(board);
        System.out.println(board);
    }

    private static void solve(Board board) {

        while (!board.isSolved()) {
            solveSingleCandidateSquares(board);
            solveSingleSquareCandidates(board);
            System.out.println(board);
            pruneCandidatesByPairs(board);
        }
    }

    private static void pruneCandidatesByPairs(Board board) {

        for (List<Square> area : board.getAreas()) {

            for (Square square : area) {
                if (square.getValue() == 0) {
                    List<Square> matches = findSquaresWithCandidates(area, square.getCandidates(), 2, 2);
                    if (matches.size() == 2) {
                        System.out.println("Prune candidates by pairs: " + Arrays.toString(matches.toArray()));
                        removeCandidatesFromOthers(area, matches);
                    }
                }
            }
        }
    }

    private static List<Square> findSquaresWithCandidates(List<Square> area, Set<Integer> targetCandidates, int minCandidates, int maxCandidates) {
        List<Square> matches = new ArrayList<Square>();
        for (Square square : area) {
            Set<Integer> candidates = square.getCandidates();
            if (candidates.containsAll(targetCandidates) && candidates.size() >= minCandidates && candidates.size() <= maxCandidates) {
                matches.add(square);
            }
        }
        return matches;
    }

    private static void removeCandidatesFromOthers(List<Square> area, List<Square> matches) {
        Set<Integer> candidatesToRemove = new HashSet<Integer>();
        for (Square match : matches) {
            candidatesToRemove.addAll(match.getCandidates());
        }
        for (Square square : area) {
            if (square.getValue() == 0 && !matches.contains(square)) {
                System.out.println("Removed from " + square + " candidatesToRemove " + Arrays.toString(candidatesToRemove.toArray()));
                square.getCandidates().removeAll(candidatesToRemove);
            }
        }
    }

    private static void solveSingleSquareCandidates(Board board) {

        for (List<Square> area : board.getAreas()) {

            Map<Integer, List<Square>> areaCandidateSquares = new HashMap<Integer, List<Square>>();
            for (Square square : area) {

                for (Integer candidate : square.getCandidates()) {

                    List<Square> squares = areaCandidateSquares.get(candidate);
                    if (squares == null) {

                        squares = new ArrayList<Square>();
                        areaCandidateSquares.put(candidate, squares);
                    }
                    squares.add(square);
                }
            }
            for (Entry<Integer, List<Square>> entry : areaCandidateSquares.entrySet()) {

                if (entry.getValue().size() == 1) {
                    System.out.print("Single square candidate ");
                    entry.getValue().get(0).setValue(entry.getKey());
                }
            }
        }
    }

    private static void solveSingleCandidateSquares(Board board) {

        for (Square square : board.getSquares()) {

            if (square.getCandidates().size() == 1) {

                int value = square.getCandidates().iterator().next();
                System.out.print("Single candidate square ");
                square.setValue(value);
            }
        }
    }
}
