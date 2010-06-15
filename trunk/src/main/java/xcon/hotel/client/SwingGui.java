package xcon.hotel.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.SwingGuiException;
import xcon.hotel.model.HotelRoom;

public class SwingGui extends JFrame {

    private static final long serialVersionUID = 5165L;

    private Logger logger = Logger.getLogger(SwingGui.class.getName());

    private Controller controller;
    private JTable mainTable = new JTable();
    private JLabel commentLabel = new JLabel();
    private JTextField nameSearchField = new JTextField(20);
    private JTextField locationSearchField = new JTextField(20);
    private JLabel nameLabel;
    private JLabel locationLabel;

    private HotelTableModel hotelTableModel;

    private ResourceBundle messages;

    public SwingGui(Controller controller) {

        messages =
            ResourceBundle.getBundle("hotel_messages", Locale.getDefault());
        setTitle(messages.getString("gui.frame.application.name"));
        // set dependency
        this.controller = controller;

        setDefaultCloseOperation(SwingGui.EXIT_ON_CLOSE);

        // initialize the TableModel

        hotelTableModel =
            new HotelTableModel(controller.getColumnNames(), messages);
        // 1: search for all rooms

        List<HotelRoom> rooms = controller.search("", "");

        // 2: add all rooms to table model

        hotelTableModel.setHotelrooms(rooms);
        mainTable.setModel(hotelTableModel);

        // initialize status bar so it shows up
        commentLabel.setText(" ");

        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(messages.getString("gui.jmenu.file.name"));
        JMenuItem quitMenuItem =
            new JMenuItem(messages.getString("gui.jmenu.item.quit"));
        quitMenuItem.addActionListener(new QuitApplication());
        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
        fileMenu.add(quitMenuItem);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        add(new HotelRoomScreen());

        pack();
        setSize(750, 300);

        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - getWidth()) / 2);
        int y = (int) ((d.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setVisible(true);
    }

    private class HotelRoomScreen extends JPanel {

        private static final long serialVersionUID = 5165L;

        public HotelRoomScreen() {
            setLayout(new BorderLayout());
            JScrollPane tableScroll = new JScrollPane(mainTable);
            tableScroll.setSize(500, 250);

            add(tableScroll, BorderLayout.CENTER);

            JButton searchButton =
                new JButton(messages.getString("gui.button.seach"));
            searchButton.addActionListener(new SearchHotelRoom());
            searchButton.setMnemonic(KeyEvent.VK_S);
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            nameLabel = new JLabel(messages.getString("gui.label.hotelname"));
            searchPanel.add(nameLabel);
            searchPanel.add(nameSearchField);
            locationLabel =
                new JLabel(messages.getString("gui.label.location"));
            searchPanel.add(locationLabel);
            searchPanel.add(locationSearchField);
            searchPanel.add(searchButton);

            JLabel bookingLabel = new JLabel();
            bookingLabel.setText(messages.getString("gui.label.enter.customerid"));

            JTextField ownerIdTextField = new JTextField(8);
            JButton BookButton =
                new JButton(messages.getString("gui.button.book"));

            BookButton.addActionListener(new BookHotelRoom(ownerIdTextField));
            BookButton.setRequestFocusEnabled(false);
            BookButton.setMnemonic(KeyEvent.VK_B);
            JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bookingPanel.add(BookButton);
            bookingPanel.add(bookingLabel);
            bookingPanel.add(ownerIdTextField);

            JPanel commentPanel = new JPanel();
            commentPanel.add(commentLabel);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(searchPanel, BorderLayout.NORTH);
            bottomPanel.add(bookingPanel, BorderLayout.EAST);
            bottomPanel.add(commentLabel, BorderLayout.SOUTH);
            add(bottomPanel, BorderLayout.SOUTH);

            mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            mainTable.setToolTipText(messages.getString("gui.Jtabel.maintable.tooltiptext"));

            BookButton.setToolTipText(messages.getString("gui.jbutton.book.tooltiptext"));
            nameSearchField.setToolTipText(messages.getString("gui.jtextfield.search.location.tooltiptext"));
            locationSearchField.setToolTipText(messages.getString("gui.jtextfield.search.hotelname.tooltiptext"));
            searchButton.setToolTipText(messages.getString("gui.button.seach"));
        }
    }

    // XXX: extract as top level class
    private class BookHotelRoom implements ActionListener {

        private JTextField customerIdField = null;

        public BookHotelRoom(JTextField customerIdField) {
            this.customerIdField = customerIdField;
        }

        public void actionPerformed(ActionEvent ae) {

            int index = mainTable.getSelectedRow();
            if (index < 0) {
                commentLabel.setText(messages.getString("gui.jlabel.info.room.not.selected"));
                return;
            }

            HotelRoom room = hotelTableModel.getHotelRoom(index);
            if (room.getOwner() != null) {
                commentLabel.setText(messages.getString("gui.jlabel.info.room.allready.booked"));
                return;
            }

            String customerIdFieldValue = customerIdField.getText();
            if (customerIdFieldValue.equals("")) {
                commentLabel.setText(messages.getString("gui.jlabel.info.customerid.not.entered"));
                return;
            }

            long customerId;
            try {
                customerId = Long.parseLong(customerIdFieldValue);
            }
            catch (NumberFormatException e) {
                commentLabel.setText("gui.jlabel.info.customerid.not.number");
                return;
            }

            if (customerIdFieldValue.length() != 8) {
                commentLabel.setText(messages.getString("validation.customerid.length"));
                return;
            }

            try {

                controller.bookRoom(customerId, room);
                commentLabel.setText(messages.getString("gui.jlabel.info.room.is.booked"));
                hotelTableModel.fireTableDataChanged();
                customerIdField.setText("");
            }
            catch (ControllerException e) {
                commentLabel.setText(messages.getString(e.getMessageKey()));
                return;
            }

        }

    }

    private class SearchHotelRoom implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            String hotelName = nameSearchField.getText();
            String hotelLocation = locationSearchField.getText();
            if (hotelName == null || hotelLocation == null) {
                return;
            }

            List<HotelRoom> hotelRooms =
                controller.search(hotelName, hotelLocation);
            commentLabel.setText(hotelRooms.size() + " "
                + messages.getString("gui.jlabel.info.search.result"));
            hotelTableModel.setHotelrooms(hotelRooms);
        }
    }

    private class QuitApplication implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

}
