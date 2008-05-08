package xcon.pilot.singleton;

public class Singleton {

    private String value;

    private static int counter;
    
    private static Singleton instance;

    // non-lazy initialization
//    static {
//        Singleton.instance = new Singleton();
//    }
    
    /**
     * Private constructor to avoid external creation of Singleon
     */
    private Singleton() {        
        System.out.println("Singleton instance created");
    }

    public static Singleton getInstance() {
        
        System.out.println("Call to getInstance");
        // lazy initialization
        if (Singleton.instance == null) {
            Singleton.instance = new Singleton();
        }
        return Singleton.instance;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public static void inc() {
        Singleton.counter++;        
    }
    
    public static int getCounter() {
        return Singleton.counter;
    }

}
