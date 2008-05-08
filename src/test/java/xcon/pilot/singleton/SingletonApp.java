package xcon.pilot.singleton;

public class SingletonApp {

    public static void main(String[] args) {

        // static access to Singleton (no instance required)
        Singleton.inc();
        Singleton.inc();
        System.out.println("SingletonApp counter=" + Singleton.getCounter());
        
        // kan niet want constructor is private
        // Singleton bla = new Singleton();
        Singleton bla1 = Singleton.getInstance();

        System.out.println("SingletonApp before:" + bla1.getValue());

        bla1.setValue("hallo");

        System.out.println("SingletonApp after:" + bla1.getValue());

        SingletonApp2.start();

        System.out.println("klaar");
    }
}
