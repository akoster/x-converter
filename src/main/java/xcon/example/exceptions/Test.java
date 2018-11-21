package xcon.example.exceptions;

public class Test {
    int value;


    public int getValue() {
        return value;
    }

    public void reset() {
        value = 0;
    }

    // Calculates without exception
    public void method1(int i) {
        value = ((value + i) / i) << 1;
        // Will never be true
        if ((i & 0xFFFFFFF) == 1000000000) {
            System.out.println("You'll never see this!");
        }
    }

    // Could in theory throw one, but never will
    public void method2(int i) throws Exception {
        value = ((value + i) / i) << 1;
        // Will never be true
        if ((i & 0xFFFFFFF) == 1000000000) {
            throw new Exception();
        }
    }

    // This one will regularly throw one
    public void method3(int i) throws Exception {
        value = ((value + i) / i) << 1;
        // i & 1 is equally fast to calculate as i & 0xFFFFFFF; it is both
        // an AND operation between two integers. The size of the number plays
        // no role. AND on 32 BIT always ANDs all 32 bits
        if ((i & 0x1) == 1) {
            throw new Exception();
        }
    }

    // This one will regularly throw one
    public void method4(int i) throws Exception {
        value = ((value + i) / i) << 1;
        if ((i & 0x1) == 1) {
            new Exception();
        }
    }

    public static void main(String[] args) {
        int i;
        long l;
        Test t = new Test();

        l = System.currentTimeMillis();
        t.reset();
        for (i = 1; i < 100000000; i++) {
            t.method1(i);
        }
        l = System.currentTimeMillis() - l;
        System.out.println(
                "method1 took " + l + " ms, result was " + t.getValue()
        );

        l = System.currentTimeMillis();
        t.reset();
        for (i = 1; i < 100000000; i++) {
            try {
                t.method2(i);
            } catch (Exception e) {
                System.out.println("You'll never see this!");
            }
        }
        l = System.currentTimeMillis() - l;
        System.out.println(
                "method2 took " + l + " ms, result was " + t.getValue()
        );

        l = System.currentTimeMillis();
        t.reset();
        for (i = 1; i < 100000000; i++) {
            try {
                t.method3(i);
            } catch (Exception e) {
                // Do nothing here, as we will get here
            }
        }
        l = System.currentTimeMillis() - l;
        System.out.println(
                "method3 took " + l + " ms, result was " + t.getValue()
        );

        l = System.currentTimeMillis();
        t.reset();
        for (i = 1; i < 100000000; i++) {
            try {
                t.method4(i);
            } catch (Exception e) {
                // Do nothing here, as we will get here
            }
        }
        l = System.currentTimeMillis() - l;
        System.out.println(
                "method4 took " + l + " ms, result was " + t.getValue()
        );
    }
}