package xcon.pilot;

public class StaticDemo {

	// a static initialization block, executed once when the class is loaded
	static {
		System.out.println("Class StaticDemo loading...");
	}

	// a constant
	static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

	// a static field
	static int instanceCounter;

	// a second static initialization block
	// static members are processed in the order they appear in the class
	static {
		// we can now acces the static fields initialized above
		System.out.println("ONE_DAY_IN_MILLIS=" + ONE_DAY_IN_MILLIS
				+ " instanceCounter=" + instanceCounter);
	}

	// an instance initialization block
	// instance blocks are executed each time a class instance is created
	{
		StaticDemo.instanceCounter++;
		System.out.println("instanceCounter=" + instanceCounter);
	}

	public StaticDemo() {
		System.out.println("Constructor call " + instanceCounter);
	}
	
	public static void main(String[] args) {
		System.out.println("Starting StaticDemo");
		new StaticDemo();
		new StaticDemo();
		new StaticDemo();
	}

	static {
		System.out.println("Class StaticDemo loaded");
	}

}
