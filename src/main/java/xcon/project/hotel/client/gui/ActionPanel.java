package xcon.project.hotel.client.gui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import xcon.project.hotel.client.Controller;
import xcon.project.hotel.client.HotelTableModel;

public class ActionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public ActionPanel(Controller controller,
                       ResourceBundle messages,
                       HotelTableModel hotelTableModel)
    {
        JPanel searchPanel =
            createSearchPanel(controller, messages, hotelTableModel);
        add(searchPanel);

        JPanel bookingPanel =
            createBookingPanel(controller, messages, hotelTableModel);
        add(bookingPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private JPanel createBookingPanel(Controller controller,
                                      ResourceBundle messages,
                                      HotelTableModel hotelTableModel)
    {
        JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel bookingLabel = new JLabel();
        bookingLabel.setText(messages.getString("gui.label.enter.customerid"));
        bookingPanel.add(bookingLabel);
        JTextField ownerIdTextField = new JTextField(8);
        bookingPanel.add(ownerIdTextField);

        JButton bookButton = new JButton(messages.getString("gui.button.book"));
        bookButton.setRequestFocusEnabled(false);
        bookButton.setMnemonic(KeyEvent.VK_B);
        bookingPanel.add(bookButton);

        BookAction bookAction =
            new BookAction(controller, messages, hotelTableModel,
                ownerIdTextField);
        bookButton.addActionListener(bookAction);
        return bookingPanel;
    }

    private JPanel createSearchPanel(Controller controller,
                                     ResourceBundle messages,
                                     HotelTableModel hotelTableModel)
    {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel =
            new JLabel(messages.getString("gui.label.hotelname"));
        searchPanel.add(nameLabel);
        JTextField nameSearchField = new JTextField(20);
        searchPanel.add(nameSearchField);
        JLabel locationLabel =
            new JLabel(messages.getString("gui.label.location"));
        searchPanel.add(locationLabel);
        JTextField locationSearchField = new JTextField(20);
        searchPanel.add(locationSearchField);

        JButton searchButton =
            new JButton(messages.getString("gui.button.seach"));
        searchPanel.add(searchButton);
        searchButton.setMnemonic(KeyEvent.VK_S);

        SearchAction searchAction =
            new SearchAction(controller, messages, hotelTableModel,
                nameSearchField, locationSearchField);
        searchButton.addActionListener(searchAction);
        return searchPanel;
    }
}
