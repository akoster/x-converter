package xcon.example.election;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Election {

	protected Map<Integer, Candidate> ballot = new HashMap<Integer, Candidate>();

	public void addCandidate(Candidate candidate) {
		ballot.put(candidate.getId(), candidate);
	}

	public void countVote(Integer vote) {
		Candidate candidate = ballot.get(vote);
		if (candidate != null) {
			candidate.addVote();
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<Candidate> rank = new ArrayList<Candidate>(ballot.values());
		Collections.sort(rank, new Comparator<Candidate>() {
			@Override
			public int compare(Candidate o1, Candidate o2) {
				return o2.getVotes() - o1.getVotes();
			}
		});
		for (Candidate candidate : rank) {
			sb.append(candidate);
		}
		return sb.toString();
	}
}
