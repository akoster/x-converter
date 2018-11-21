package xcon.codility;

public class PhoneNumbersRecursive {
    public String solution(String S) {
        String onlyTheNumbers = S.replaceAll("-", "").replaceAll(" ", "");
        return solve(onlyTheNumbers);
    }

    // recursive solution
    private String solve(String input) {
        if (input.length() >= 5) {
            return addBlock(input, 3);
        } else if (input.length() == 4) {
            return addBlock(input, 2);
        } else {
            return input;
        }
    }

    private String addBlock(String input, int blockSize) {
        return input.substring(0, blockSize) + "-" + solve(input.substring(blockSize));
    }
}
