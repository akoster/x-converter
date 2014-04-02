package xcon.stackoverflow;

/**
 * http://stackoverflow.com/questions/8037399/method-calls-another-method-that-gets-overridden-which-is-called-in-the-subclas/8038388#8038388
 */
public class Inheritance {

	public static interface CanDoMethod1 {
		public void method1();
	}

	public static class Parent implements CanDoMethod1 {
		public void method1() {
			System.err.println("Parent doing method1");
		}
	}

	public static class Child extends Parent {
		public void method1() {
			System.err.println("Child doing method1");
		}
	}

	public static void main(String[] args) {

		CanDoMethod1 instance = new Parent();
		instance.method1();

		CanDoMethod1 instance2 = new Child();
		instance2.method1();

		Parent instance3 = new Child();
		instance3.method1();
		
		Child instance4 = (Child) new Parent();
		instance4.method1();

	}

}
