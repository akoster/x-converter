package xcon.lottery;

import java.util.ArrayList;
import java.util.List;

/**
 * Application for running a lottery
 * 
 * @author Adriaan
 */
//input: een aantal namen van deelnemers
//minimaal 2 deelnemers
//namen mogen geen + of = bevatten
//
//output: voor elke deelnemer de (random) naam van diegene voor wie een
//kadootje moet worden gekocht
//
//constraints: niemand trekt zichzelf
//
//uitbreiding 1: als namen alsvolgt worden opgegeven: Annie+Willem+Jan dan
//betekent dit dat die personen elkaar niet mogen trekken
//
//uitbreiding 3: als input wordt naam=emailadres opgegeven, bijvoorbeeld:
//Annie=annie@hotmail.com. De uitslag van de loting wordt aan de deelnemer
//gemaild.
//email adressen mogen geen + of = bevatten
public class LotteryApp {

	/**
	 * Runs a lottery, sending email to the participants
	 * @param args
	 */
	public static void main(String[] args) {
		run(args, false);
	}

	/**
	 * Runs a lottery
	 * @param args
	 * @param isDebugMode if true, no email is sent but the results are printed
	 */
	public static void run(String[] args, boolean isDebugMode) {

		Lottery lottery = new Lottery(extractPersons(args));
		List<Person> result = lottery.draw();
		if (isDebugMode) {
			printNodes(result);
		} else {
			Emailer.sendDrawByEmail(result);
		}
	}
	

	public static List<Person> extractPersons(String[] args) {
		
		if (args == null || args.length == 0) {

			throw new IllegalArgumentException(
					"Please provide the names of the participants "
							+ "as commandline arguments");
		}
		
		List<Person> persons = new ArrayList<Person>();
		for (String arg : args) {

			for (Person person : extractGroup(arg)) {
				// (int) (Math.random() * (persons.size() + 1))
				persons.add(0, person);
			}
		}
		return persons;
	}

	private static List<Person> extractGroup(String groupArg) {

		List<Person> group = new ArrayList<Person>();
		// create a group of one or more mutually excluded persons
		for (String personArg : groupArg.split("\\+")) {
			group.add(extractPerson(personArg));
		}
		// set the non-neighboring nodes in each node
		for (Person node : group) {
			node.setExcluded(group);
		}

		return group;
	}

	private static Person extractPerson(String personArg) {

		String[] fields = personArg.split("=");
		Person person = new Person(fields[0]);
		if (fields.length > 1) {
			person.setEmail(fields[1]);
		}
		return person;
	}
	
	private static void printNodes(List<Person> nodes) {

		String msg = "";
		for (Person node : nodes) {
			msg += node.toString() + " ";
		}
		System.out.println(msg);
	}
}