package xcon.pilot.xml;

import java.util.ArrayList;
import java.util.List;

public class Map {
	
	private List<Parent> parents = new ArrayList<Parent>();

	public void addParent(Parent parent) {
		parents.add(parent);
	}

	public List<Parent> getParents() {
		return this.parents;
	}
}
