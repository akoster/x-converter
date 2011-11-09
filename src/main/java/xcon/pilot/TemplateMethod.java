package xcon.pilot;

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
