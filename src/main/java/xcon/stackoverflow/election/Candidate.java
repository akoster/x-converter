package xcon.stackoverflow.election;

public class Candidate {
	protected String name;
	protected int id;
	protected int votes;

	public Candidate(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void addVote() {
		votes++;
	}

	public int getVotes() {
		return votes;
	}

	public String toString() {
		return String.format("Candidate: %s ID: %s Votes: %s%n", name, id, votes);
	}
}
