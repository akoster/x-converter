package xcon.codility;

public class PhoneNumbers {

    private int length;
    private boolean isTwoPairsAtTheEnd;

    public String solution(String s) {

        String digits = removeNonDigits(s);
        length = digits.length();
        isTwoPairsAtTheEnd = digits.length() % 3 == 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (needToPutDash(i)) {
                builder.append('-');
            }
            builder.append(digits.charAt(i));
        }
        return builder.toString();
    }

    private String removeNonDigits(String s) {
        return s.replaceAll("\\D", "");
    }

    private boolean needToPutDash(int index) {
        boolean notAtStart = index > 0;
        return notAtStart &&
                (index % 3 == 0 && !(isTwoPairsAtTheEnd && index == length - 1)) ||
                (isTwoPairsAtTheEnd && index == length - 2);
    }

}

