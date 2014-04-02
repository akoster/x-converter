package xcon.pilot.xml;

public class Parent {

	private String key;
	private Child child;

	public Parent(String key) {
		this.key = key;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
