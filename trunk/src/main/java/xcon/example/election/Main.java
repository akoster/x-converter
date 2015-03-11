package xcon.example.election;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) {
		Election election = new Election();

		addCandidates(election);

		countVotesFromFile(election, new File("D:/votes"));

		System.out.println(election.toString());
	}

	private static void countVotesFromFile(Election election, File file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextInt()) {
				election.countVote(scanner.nextInt());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static void addCandidates(Election election) {
		election.addCandidate(new Candidate("Washington", 1));
		election.addCandidate(new Candidate("Adams", 2));
		election.addCandidate(new Candidate("Jefferson", 3));
		election.addCandidate(new Candidate("Madison", 4));
		election.addCandidate(new Candidate("Monroe", 5));
		election.addCandidate(new Candidate("Quincy Adams", 6));
		election.addCandidate(new Candidate("Jackson", 7));
		election.addCandidate(new Candidate("Van Buren", 8));
		election.addCandidate(new Candidate("Harrison", 9));
		election.addCandidate(new Candidate("Tyler", 10));
		election.addCandidate(new Candidate("Polk", 11));
		election.addCandidate(new Candidate("Taylor", 12));
		election.addCandidate(new Candidate("Fillmore", 13));
		election.addCandidate(new Candidate("Pierce", 14));
		election.addCandidate(new Candidate("Buchanan", 15));
		election.addCandidate(new Candidate("Lincoln", 16));
		election.addCandidate(new Candidate("Johnson", 17));
		election.addCandidate(new Candidate("Grant", 18));
		election.addCandidate(new Candidate("Hayes", 19));
		election.addCandidate(new Candidate("Garfield", 20));
		election.addCandidate(new Candidate("Arthur", 21));
		election.addCandidate(new Candidate("Cleveland", 22));
		election.addCandidate(new Candidate("McKinely", 23));
		election.addCandidate(new Candidate("Roosevely", 24));
	}

}