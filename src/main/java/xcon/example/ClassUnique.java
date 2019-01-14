package xcon.example;

public class ClassUnique {

    public static void main(String [] args) throws ClassNotFoundException {
        Class<?>  c1 = Class.forName("java.util.Date");
        Class<?>  c2 = Class.forName("java.util.Date");

        System.out.println(c1.equals(c2));
    }
}
