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

public class MyLogin extends Dialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	boolean id = false;
	Button ok, can;
	TextField hostname;
	TextField username;
	TextField password;

	MyLogin(Frame frame) {
		super(frame, "Welcome", true);
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

	void addOKCancelPanel() {
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		createButtons(p);
		add(p);
	}

	void createButtons(Panel p) {
		p.add(ok = new Button("OK"));
		ok.addActionListener(this);
		p.add(can = new Button("Cancel"));
		can.addActionListener(this);
	}

	void createFrame() {
		Dimension d = getToolkit().getScreenSize();
		setLocation(d.width / 4, d.height / 3);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == ok) {
			id = true;
			setVisible(false);
		} else if (ae.getSource() == can) {
			id = false;
			setVisible(false);
		}
	}
}