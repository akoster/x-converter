package xcon.stackoverflow;

/**
 * http://stackoverflow.com/questions/7350297/good-method-to-make-it-obvious-that-an-overriden-method-should-call-super/7350417#7350417
 */
public class TemplateMethod {

    public interface ThirdPartyInterface {
        public void doSomething();
    }
    
    public abstract static class Parent {
        public final void doSomething() {
            System.out.println("Parent doing something");
            childDoSomething();
        }

        public abstract void childDoSomething();
    }

    public static class Child extends Parent implements ThirdPartyInterface{
        public void childDoSomething() {
            System.out.println("Child doing something");
        }
        
//        public final void doSomething() {
//            // cannot do this because Parent makes it final
//        }
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.doSomething();
    }
}
