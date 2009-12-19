/**
 * 
 */
package xcon.lottery;

import java.util.List;

/**
 * A Person participating in a Lottery
 * 
 * @author Adriaan
 */
public class Person {

	private String email;
	private String name;
	private Person target;
	private List<Person> excluded;

	public Person() {
		// default
	}

	public Person(String name) {
		this.name = name;
	}

	public Person getTarget() {
		return target;
	}

	public void setTarget(Person target) {
		this.target = target;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getExcluded() {
		return excluded;
	}

	public void setExcluded(List<Person> excluded) {
		this.excluded = excluded;
	}

	public String toString() {
		return name + (target != null ? "->" + target.name : "");
	}
}