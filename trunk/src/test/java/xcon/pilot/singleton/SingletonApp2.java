package xcon.pilot.singleton;

public class SingletonApp2 {

    public static void start() {
                
        // kan niet want constructor is private
        //Singleton bla = new Singleton();
        Singleton bla2 = Singleton.getInstance();
        
        System.out.println("SingletonApp2 before:" + bla2.getValue());
        
        bla2.setValue("doei");
        
        System.out.println("SingletonApp2 after:" + bla2.getValue());
        
    }
}
