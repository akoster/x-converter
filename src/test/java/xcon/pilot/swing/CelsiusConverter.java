package xcon.pilot.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// This example demonstrates the use of JButton, JTextField and JLabel.

public class CelsiusConverter implements ActionListener {
	JFrame converterFrame;

	JPanel converterPanel;

	JTextField tempCelsius;

	JLabel celsiusLabel, fahrenheitLabel;

	JButton convertTemp;

	// Constructor
	public CelsiusConverter() {
		// Create the frame and container.
		converterFrame = new JFrame("Convert Celsius to Fahrenheit");
		converterPanel = new JPanel();
		converterPanel.setLayout(new GridLayout(2, 2));

		// Add the widgets.
		addWidgets();

		// Add the panel to the frame.

		converterFrame.getContentPane()
				.add(converterPanel, BorderLayout.CENTER);

		// Exit when the window is closed.

		converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the converter.
		converterFrame.pack();
		converterFrame.setSize(300, 100); 
		converterFrame.setVisible(true);
	}

	// Create and add the widgets for converter.
	private void addWidgets() {
		// Create widgets.
		tempCelsius = new JTextField(2);
		celsiusLabel = new JLabel("Celsius", SwingConstants.LEFT);
		convertTemp = new JButton("Convert...");
		fahrenheitLabel = new JLabel("Fahrenheit", SwingConstants.LEFT);

		// Listen to events from Convert button.
		convertTemp.addActionListener(this);

		// Add widgets to container.
		converterPanel.add(tempCelsius);
		converterPanel.add(celsiusLabel);
		converterPanel.add(convertTemp);
		converterPanel.add(fahrenheitLabel);

		celsiusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		fahrenheitLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	}

	// Implementation of ActionListener interface.
	public void actionPerformed(ActionEvent event) {
		// Parse degrees Celsius as a double and convert to Fahrenheit.
		int tempFahr = (int) ((Double.parseDouble(tempCelsius.getText())) * 1.8 + 32);
		fahrenheitLabel.setText(tempFahr + " Fahrenheit");
	}

	// main method
	public static void main(String[] args) {
		// Set the look and feel.
		/*try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}*/

		CelsiusConverter converter = new CelsiusConverter();
	}
}
