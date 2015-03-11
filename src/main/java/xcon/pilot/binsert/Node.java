package xcon.pilot.binsert;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private Node parent;
	private List<Integer> values = new ArrayList<Integer>(3);
	private List<Node> children = new ArrayList<Node>(4);

	public Node top() {
		if (parent != null) {
			return parent.top();
		}
		return this;
	}

	public void add(int value) {
		if (children.size() > 0) {
			pushDown(value);
		} else {
			int index = findIndex(value);
			values.add(index, value);
			if (values.size() > 2) {
				pushUp();
			}
		}
	}

	private void pushDown(int value) {
		int index = findIndex(value);
		Node child = children.get(index);
		child.add(value);
	}

	private int findIndex(int value) {
		int index;
		for (index = 0; index < values.size(); index++) {
			if (values.get(index) > value) {
				break;
			}
		}
		return index;
	}

	private void pushUp() {
		if (parent == null) {
			parent = new Node();
		}

		Node smallerSibling = new Node();
		smallerSibling.parent = parent;
		smallerSibling.add(values.remove(0));

		parent.add(values.remove(0), smallerSibling, this);
	}

	private void add(int value, Node smallerSibling, Node largerSibling) {
		int index = findIndex(value);
		values.add(index, value);
		if (!children.contains(largerSibling)) {
			children.add(index, largerSibling);
		}
		children.add(index, smallerSibling);
		if (values.size() > 2) {
			pushUp();
		}
	}

	@Override
	public String toString() {
		String result = "" + values + "\n";
		result += "    ";
		for (Node child : children) {
			result += child.toString();
		}
		return result;
	}
}
