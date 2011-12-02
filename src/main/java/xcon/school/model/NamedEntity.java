package xcon.school.model;

public class NamedEntity {

	private final String name;

	public NamedEntity(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"A named entity must have a name");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NamedEntity)) {
			return false;
		}
		NamedEntity other = (NamedEntity) obj;
		return this.name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
