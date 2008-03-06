package xcon.pilot.enums;


public class EnumTest {

    private static MyEnum doTest(int value) {
        return MyEnum.getInstance(value);
    }

    public static void main(String[] args) {
                
        MyEnum bla = EnumTest.doTest(1);      
        System.out.println(bla + " value=" + bla.getValue());
    }
}
