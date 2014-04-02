package xcon.example.recursion;

public class Factorial {

    public static void main(String[] args) {        
        System.out.println(factorial(5, 0));
    }

    private static int factorial(int number, int depth) {

        depth = depth + 4;
        System.out.println(tab(depth) + "Must calculate factorial of " + number);
        if (number == 1) {
            System.out.println(tab(depth) + "I know the factorial of 1 is 1");
            return 1;
        }
        else {
            System.out.println(tab(depth) + "I don't known the factorial of " + number);
            System.out.println(tab(depth) + "Subproblem: " + number
                + " * factorial(" + (number - 1) + ")");            
            int subresult = factorial(number - 1, depth);
            System.out.println(tab(depth) + "subresult factorial(" + (number - 1) + ")=" + subresult);
            int result = number * subresult;
            System.out.println(tab(depth) + "my result: " + number
                    + " * " + subresult + " = " + result);
            return result;
        }
    }
        
    public static String tab(int size) {
        String tab = "";
        for (int i = 0; i< size; i++) {
            tab += " ";
        }
        return tab;
    }
}
