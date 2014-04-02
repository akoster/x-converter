package xcon.example.lottery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lottery which allocates each Person a target to buy a present for.
 * 
 * @author Adriaan
 */
public class Lottery {

	private List<Person> persons;

	public Lottery(List<Person> persons) {

		if (persons.size() < 2) {

			throw new IllegalArgumentException(
					"Lottery needs at least 2 persons");
		}
		this.persons = persons;
	}

	public List<Person> draw() {

		determineTargets();
		return persons;
	}

	private void determineTargets() {

		Set<Person> topHat = new HashSet<Person>(persons);
		Person lastNode = persons.get(persons.size() - 1);
		for (Person current : persons) {

			// if the current node is in the topHat, remove it temporarily
			// to avoid drawing itself
			boolean currentRemoved = topHat.remove(current);

			Person target = drawNodeFromTopHat(topHat, lastNode);
			current.setTarget(target);
			// reinsert the node if removed earlier
			if (currentRemoved) {
				topHat.add(current);
			}
		}
	}

	private Person drawNodeFromTopHat(Set<Person> topHat, Person last) {

		Person target;
		// if topHat has two persons, and one of them is the last one, draw it
		// to avoid forcing leaving the last person with no options
		if (topHat.size() == 2 && topHat.contains(last)) {
			target = last;
		} else {
			// otherwise, draw the next target from the topHat
			// this is random because the persons were shuffled earlier
			target = topHat.iterator().next();
		}
		topHat.remove(target);
		return target;
	}
}
