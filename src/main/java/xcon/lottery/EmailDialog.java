package xcon.lottery;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple UI for obtaining the email host and credentials.
 * 
 * @author Adriaan
 */
public class EmailDialog extends Dialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean dialogCompleted = false;
	private Button ok, can;
	private TextField hostname;
	private TextField username;
	private TextField password;

	public EmailDialog() {

		super(new Frame(""), "Email dialog", true);
		setLayout(new FlowLayout());
		hostname = new TextField(15);
		username = new TextField(15);
		password = new TextField(15);
		password.setEchoChar('*');
		add(new Label("Host :"));
		add(hostname);
		add(new Label("User :"));
		add(username);
		add(new Label("Password :"));
		add(password);
		addOKCancelPanel();
		createFrame();
		pack();
		setVisible(true);
	}

	private void addOKCancelPanel() {

		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		createButtons(p);
		add(p);
	}

	private void createButtons(Panel p) {

		p.add(ok = new Button("OK"));
		ok.addActionListener(this);
		p.add(can = new Button("Cancel"));
		can.addActionListener(this);
	}

	private void createFrame() {

		Dimension d = getToolkit().getScreenSize();
		setLocation(d.width / 4, d.height / 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == ok) {

			dialogCompleted = true;
			setVisible(false);
		} else if (ae.getSource() == can) {

			dialogCompleted = false;
			setVisible(false);
		}
	}

	public boolean isDialogCompleted() {
		return dialogCompleted;
	}

	public String getUsername() {
		return username.getText();
	}

	public String getHostname() {
		return hostname.getText();
	}

	public String getPassword() {
		return password.getText();
	}
}