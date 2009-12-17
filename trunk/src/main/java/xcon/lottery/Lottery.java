package xcon.lottery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// input: een aantal namen van deelnemers
// minimaal 2 deelnemers
// namen mogen geen + of = bevatten
//
// output: voor elke deelnemer de (random) naam van diegene voor wie een
// kadootje moet worden gekocht
//
// constraints: niemand trekt zichzelf
// 
// uitbreiding 1: als namen alsvolgt worden opgegeven: Annie+Willem+Jan dan
// betekent dit dat die personen elkaar niet mogen trekken
//
// uitbreiding 2: de output wordt in afzonderlijke files weggeschreven,
// waarbij elke file de naam van een deelnemer heeft en de naam van de
// getrokken deelnemer als tekst bevat
//
// uitbreiding 3: als input wordt naam=emailadres opgegeven, bijvoorbeeld:
// Annie=annie@hotmail.com. De uitslag van de loting wordt aan de deelnemer
// gemaild.
// email adressen mogen geen + of = bevatten
//
public class Lottery {

	private static final int LEVEL_VERBOSE = 0;
	private static final int LEVEL_NORMAL = 1;
//	private static final int LEVEL_SILENT = 2;
	private static final int LEVEL = LEVEL_VERBOSE;
	private static final boolean ENABLE_SHUFFLE = false;
	private static final boolean ENABLE_FILE = false;
	private static final boolean ENABLE_EMAIL = false;
	private static final String EMAIL_TEMPLATE = "Dear {0},\nYou have drawn {1}.\n";
	private static final String EMAIL_FROM_ADDRESS = "noreply@blunderboy.nl";

	private List<Node> nodes;
	private String[] args;

	public class Node {

		Node target;
		String email;
		String name;
		List<Node> notNeighbors;

		public String toString() {
			return name + (target != null ? "->" + target.name : "");
		}
	}

	public Lottery(String[] args) {

		if (args == null || args.length == 0) {

			throw new IllegalArgumentException(
					"Please provide the names of the participants "
							+ "as commandline arguments");
		}

		print("Creating new lottery", LEVEL_NORMAL);
		this.args = args;
		nodes = new ArrayList<Node>();
	}

	public List<Node> draw() {

		extractAndShuffleNodes();

		if (nodes.size() < 2) {

			throw new IllegalArgumentException(
					"At least 2 participants are necessary");
		}

		// fixViolations();

		connectNodes();

		print("Connected: ", LEVEL_VERBOSE);
		printNodes(nodes);

		return nodes;
	}

	private void extractAndShuffleNodes() {

		for (String arg : args) {

			// insert nodes randomly
			for (Node node : extractNodeGroup(arg)) {

				int insertPos;
				if (ENABLE_SHUFFLE) {
					insertPos = (int) (Math.random() * (nodes.size() + 1));

				} else {
					insertPos = 0;
				}
				nodes.add(insertPos, node);
			}
		}

		print("Shuffled: ", LEVEL_VERBOSE);
		printNodes(nodes);
	}

	private List<Node> extractNodeGroup(String arg) {

		List<Node> nodeGroup = new ArrayList<Node>();
		// create a group of one or more non-neighboring nodes
		for (String nodeArg : arg.split("\\+")) {
			nodeGroup.add(extractNode(nodeArg));
		}
		// set the non-neighboring nodes in each node
		String msg = "Adding: ";
		for (Node node : nodeGroup) {

			node.notNeighbors = nodeGroup;
			msg += String.valueOf(node);
		}
		print(msg,  LEVEL_VERBOSE);

		return nodeGroup;
	}

	private Node extractNode(String part) {

		Node participant = new Node();
		String[] participantFields = part.split("=");
		participant.name = participantFields[0];
		if (participantFields.length > 1) {
			participant.email = participantFields[1];
		}
		return participant;
	}
//	private void fixViolations() {
//
//		// fix exclusive neighbors
//		boolean violationsFound;
//		int iterations = 0;
//		do {
//			violationsFound = false;
//			for (int currentIndex = 0; currentIndex < nodes.size(); currentIndex++) {
//
//				Node current = nodes.get(currentIndex);
//				Node next = getNextNode(currentIndex);
//
//				// check if the current and next nodes are in violation
//				if (current.notNeighbors.contains(next)) {
//
//					violationsFound = true;
//					fixViolation(current);
//
//					print("Fix " + current.name + " </> " + next.name + " : ", LEVEL_VERBOSE);
//					printNodes(nodes);
//				}
//			}
//			iterations++;
//
//		} while (violationsFound && iterations < 10);
//
//		if (violationsFound) {
//			print("Not all violations could be fixed. "
//					+ "The constraints are probably unsolvable.", LEVEL_NORMAL);
//		}
//	}
//
//	private Node getNextNode(int currentIndex) {
//
//		// get the next node
//		int nextIndex = currentIndex + 1;
//		if (nextIndex >= nodes.size()) {
//			nextIndex = 0;
//		}
//		Node next = nodes.get(nextIndex);
//		return next;
//	}
//
//	private void fixViolation(Node current) {
//
//		// put current node before a valid next node
//		for (int i = 0; i < nodes.size(); i++) {
//
//			Node node = nodes.get(i);
//			if (!current.notNeighbors.contains(node)) {
//
//				nodes.remove(current);
//				nodes.add(i, current);
//				break;
//			}
//		}
//	}

	private void connectNodes() {

		List<Node> result = new ArrayList<Node>();
		Node current = nodes.remove(0);
		// start a cycle
		Node start = current;
		result.add(current);
		Node next;
		while (nodes.size() > 0) {

			next = nodes.remove(0);

			// if current cycle contains at least two nodes
			// and at least two free nodes remain
			// and coin flip indicates to close cycle
			boolean coinFlip = Math.random() < 0.38;
			boolean notStart = start != current;
			boolean notLastNode = nodes.size() > 0;
			boolean closeCycle = coinFlip && notStart && notLastNode;

			print("start=" + start + " next=" + next + " coinFlip=" + coinFlip
					+ " notStart=" + notStart + " notLastNode=" + notLastNode
					+ " -> closeCycle=" + closeCycle, LEVEL_VERBOSE);
			if (closeCycle) {

				// close cycle
				current.target = start;
				// start new cycle
				start = next;
			} else {
				// continue cycle
				current.target = next;
			}
			current = next;
			result.add(current);
		}
		// close last cycle
		current.target = start;

		nodes = result;
	}

	public static void main(String[] args) {

		Lottery lottery = new Lottery(args);
		List<Node> solution = lottery.draw();
		handleOutput(solution);
	}

	private static void handleOutput(List<Node> nodes) {

		printNodes(nodes);

		if (ENABLE_EMAIL) {
			sendEmail(nodes);
		}
		if (ENABLE_FILE) {
			writeFiles(nodes);
		}
	}

	private static void printNodes(List<Node> nodes) {

		String msg = "";
		for (Node node : nodes) {
			msg += node.toString() + " ";
		}
		print(msg, LEVEL_VERBOSE);
	}

	private static void writeFiles(List<Node> nodes) {

		File dir = new File("lottery_" + System.currentTimeMillis());
		dir.mkdirs();

		Node last = nodes.get(nodes.size() - 1);
		for (Node current : nodes) {

			writeToFile(dir, last.name, current.name);
			last = current;
		}
	}

	private static void writeToFile(File dir, String name, String contents) {

		try {
			File file = new File(dir, name + ".txt");
			Writer output = new BufferedWriter(new FileWriter(file));
			try {
				output.write(contents);
			} finally {
				output.close();
			}
		} catch (IOException e) {
			print("Cannot write file " + e, LEVEL_NORMAL);
			e.printStackTrace();
		}
	}

	private static void sendEmail(List<Node> nodes) {

		Session session;
		try {
			String hostname = readStringFromCommandLine("hostname?");

			Properties props = System.getProperties();
			props.put("mail.smtp.host", hostname);

			session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					try {
						return new PasswordAuthentication(
								readStringFromCommandLine("user?"),
								readStringFromCommandLine("password?"));
					} catch (IOException e) {
						System.err.println("Cannot send email " + e);
						e.printStackTrace();
						return null;
					}
				}
			});
		} catch (IOException e) {

			System.err.println("Cannot send email " + e);
			e.printStackTrace();
			return;
		}

		MessageFormat form = new MessageFormat(EMAIL_TEMPLATE);

		Node last = nodes.get(nodes.size() - 1);
		for (Node current : nodes) {

			String body = form.format(new Object[] { last.name, current.name });

			MimeMessage message = new MimeMessage(session);
			try {

				message.setFrom(new InternetAddress(EMAIL_FROM_ADDRESS));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(last.email));
				message.setSubject("Lottery");
				message.setText(body);

				print("Sending mail to " + last.email, LEVEL_VERBOSE);
				Transport.send(message);

			} catch (MessagingException e) {
				print("Cannot send email " + e, LEVEL_NORMAL);
				e.printStackTrace();
			}

			last = current;
		}
	}

	private static String readStringFromCommandLine(String prompt)
			throws IOException {

		System.out.println(prompt);
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

	private static void print(String msg, int level) {
		if (level >= LEVEL) {
			System.out.println(msg);
		}
	}
}
