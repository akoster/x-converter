package xcon.enums;


public class EnumTest {

    private static MyEnum doStuff(int value) {
        return MyEnum.getInstance(value);
    }

    public static void main(String[] args) {
                
        MyEnum bla = EnumTest.doStuff(1);      
        System.out.println(bla + " value=" + bla.getValue());
    }
}
