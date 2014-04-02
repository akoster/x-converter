package xcon.project.word;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

// 1. een woord converteren naar zijn rotonym
// invoeren van een string
// bepalen van de conversie (rotonym90 of rotonym180)
// rotonym bepalen:
// string character voor character langs lopen
// testen of het character toegestaan is
// -> nee: stoppen en foutmelding geven
// character converteren
// woord omkeren
// (einde rotonym bepalen)
// woord uitvoeren

// 2. rotonymen vinden in een woordenboek
// woordenboek helemaal inlezen in een Map<String, Boolean>
// woord voor woord over de keyset van de Map itereren
// (rotonym bepalen)
// als geen foutmelding:
// opzoeken of rotonym bestaat in woordenboek
// -> ja: woord en rotonym uitvoeren
public class RotonymSwingApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JRadioButton rotonym180;
	private JRadioButton rotonym90;
	private JTextField textfield;
	private JLabel label;
	private JButton submitButton;
	private ButtonGroup strategySelect;

	public static void main(String args[]) {
		new RotonymSwingApp();
	}

	public RotonymSwingApp() {

		super("Rotonym application");

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(400, 200);
		setVisible(true);

		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		strategySelect = new ButtonGroup();
		rotonym180 = new JRadioButton("180", true);
		rotonym90 = new JRadioButton("90", true);
		strategySelect.add(rotonym180);
		strategySelect.add(rotonym90);
		north.add(rotonym180);
		north.add(rotonym90);

		textfield = new JTextField("", 20);
		center.add(textfield);

		submitButton = new JButton("Submit");
		center.add(submitButton);

		label = new JLabel();
		south.add(label);

		submitButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {

		String output;
		RotonymStrategy strategy = null;
		String word = textfield.getText();
		if (rotonym180.isSelected()) {
			strategy = new Rotonym180();
		} else if (rotonym90.isSelected()) {
			strategy = new Rotonym90();
			word = word.toUpperCase();
			textfield.setText(word);
		}

		if (strategy == null) {
			output = "Strategy unknown";
		} else {
			output = "Using strategy " + strategy + ": ";
		}

		try {
			output += "Het rotonym van " + word + " is: "
					+ strategy.determineRotonym(word);
		} catch (RotonymException e) {
			output += word + " is geen rotonym";
		}
		printMessage(output);
	}

	private void printMessage(String msg) {
		System.out.println(msg);
		label.setText(msg);
	}
}
