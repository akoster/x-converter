package xcon.pilot.enums;

enum MyEnum {
    ONE(1), TWO(2), THREE(3);

    private int value;

    MyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MyEnum getInstance(int value) {
        if (value == 1) {
            return MyEnum.ONE;
        }
        else if (value == 2) {
            return MyEnum.TWO;
        }
        else {
            return MyEnum.THREE;
        }
    }
}