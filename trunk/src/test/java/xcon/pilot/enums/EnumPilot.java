package xcon.pilot.enums;


public class EnumPilot {

    private static MyEnum doStuff(int value) {
        return MyEnum.getInstance(value);
    }

    public static void main(String[] args) {
                
        MyEnum bla = EnumPilot.doStuff(1);      
        System.out.println(bla + " value=" + bla.getValue());
    }
}
