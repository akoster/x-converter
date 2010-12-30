package xcon.hotel.client.gui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import xcon.hotel.client.Controller;

public class ActionPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField nameSearchField = new JTextField(20);
    private JTextField locationSearchField = new JTextField(20);

    public ActionPanel(Controller controller,
                       ResourceBundle messages,
                       Properties properties, JTable hotelTable, int pageSize)
    {
        JButton searchButton =
            new JButton(messages.getString("gui.button.seach"));
        searchButton.addActionListener(new SearchAction(controller, hotelTable,
            nameSearchField, locationSearchField, messages, properties, pageSize));
        searchButton.setMnemonic(KeyEvent.VK_S);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel =
            new JLabel(messages.getString("gui.label.hotelname"));
        searchPanel.add(nameLabel);
        searchPanel.add(nameSearchField);
        JLabel locationLabel =
            new JLabel(messages.getString("gui.label.location"));
        searchPanel.add(locationLabel);
        searchPanel.add(locationSearchField);
        searchPanel.add(searchButton);

        JLabel bookingLabel = new JLabel();
        bookingLabel.setText(messages.getString("gui.label.enter.customerid"));

        JTextField ownerIdTextField = new JTextField(8);
        JButton BookButton = new JButton(messages.getString("gui.button.book"));

        BookButton.addActionListener(new BookAction(ownerIdTextField,
            hotelTable, messages, controller));
        BookButton.setRequestFocusEnabled(false);
        BookButton.setMnemonic(KeyEvent.VK_B);

        JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookingPanel.add(BookButton);
        bookingPanel.add(bookingLabel);
        bookingPanel.add(ownerIdTextField);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(searchPanel);
        add(bookingPanel);

    }

}
