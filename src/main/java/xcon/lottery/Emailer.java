package xcon.lottery;

import java.text.MessageFormat;
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

/**
 * Helper class for sending email to a list of Persons
 * 
 * @author Adriaan
 */
public class Emailer {

	private static final String EMAIL_TEMPLATE = "Dear {0},\nYou have drawn {1}.\n";
	private static final String EMAIL_FROM_ADDRESS = "noreply@blunderboy.nl";

	/**
	 * Sends an email to each Person with the name of their target
	 * 
	 * @param nodes
	 */
	public static void sendDrawByEmail(List<Person> nodes) {

		Session session = createEmailSession();
		if (session == null) {
			return;
		}

		MessageFormat form = new MessageFormat(EMAIL_TEMPLATE);
		Person last = nodes.get(nodes.size() - 1);
		for (Person current : nodes) {

			String body = form.format(new Object[] { last.getName(),
					current.getName() });

			MimeMessage message = new MimeMessage(session);
			try {

				message.setFrom(new InternetAddress(EMAIL_FROM_ADDRESS));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(last.getEmail()));
				message.setSubject("Lottery");
				message.setText(body);

				System.out.println("Sending mail to " + last.getEmail());
				Transport.send(message);

			} catch (MessagingException e) {
				System.out.println("Cannot send email " + e);
			}

			last = current;
		}
	}

	private static Session createEmailSession() {

		EmailDialog emailDialog = new EmailDialog();
		if (!emailDialog.isDialogCompleted()) {

			System.out.println("Email authentication cancelled by user");
			return null;
		}

		String hostname = emailDialog.getHostname();
		final String userName = emailDialog.getUsername();
		final String passWord = emailDialog.getPassword();

		Properties props = System.getProperties();
		props.put("mail.smtp.host", hostname);

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, passWord);
			}
		});
		return session;
	}

}
