package xcon.hotel.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
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
import xcon.hotel.db.RecordAlreadyExistException;
import xcon.hotel.model.HotelRoom;

public class SwingGui extends JFrame {

    private static final long serialVersionUID = 5165L;

    private Logger logger = Logger.getLogger("hotel-application");

    private Controller controller;
    private JTable mainTable = new JTable();
    private JLabel commentLabel = new JLabel();
    private JTextField nameSearchField = new JTextField(20);
    private JTextField locationSearchField = new JTextField(20);
    private JLabel nameLabel = new JLabel("name");
    private JLabel locationLabel = new JLabel("location");

    private HotelTableModel hotelTableModel;

    public SwingGui(Controller controller) {

        super("Hotel application");

        // set dependency
        this.controller = controller;

        setDefaultCloseOperation(SwingGui.EXIT_ON_CLOSE);

        // initialize the TableModel

        hotelTableModel = new HotelTableModel(controller.getColumnNames());
        // 1: search for all rooms

        List<HotelRoom> rooms = controller.search("", "");

        // 2: add all rooms to table model

        hotelTableModel.setHotelrooms(rooms);
        mainTable.setModel(hotelTableModel);

        // initialize status bar so it shows up
        commentLabel.setText(" ");

        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
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

            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(new SearchHotelRoom());
            searchButton.setMnemonic(KeyEvent.VK_S);
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            searchPanel.add(nameLabel);
            searchPanel.add(nameSearchField);
            searchPanel.add(locationLabel);
            searchPanel.add(locationSearchField);
            searchPanel.add(searchButton);

            JButton BookButton = new JButton("book selected room");

            BookButton.addActionListener(new BookHotelRoom());
            BookButton.setRequestFocusEnabled(false);
            BookButton.setMnemonic(KeyEvent.VK_B);
            JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bookingPanel.add(BookButton);

            JPanel commentPanel = new JPanel();
            commentPanel.add(commentLabel);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(searchPanel, BorderLayout.NORTH);
            bottomPanel.add(bookingPanel, BorderLayout.EAST);
            bottomPanel.add(commentLabel, BorderLayout.SOUTH);
            add(bottomPanel, BorderLayout.SOUTH);

            mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            mainTable.setToolTipText("Select a hotel record book a room.");

            BookButton.setToolTipText("book a room selected in the above table.");
            nameSearchField.setToolTipText("Enter the name of the hotel you want to find.");
            locationSearchField.setToolTipText("Enter the name of the hotel you want to find");
            searchButton.setToolTipText("Submit the Hotelroom search.");
        }
    }

    private class BookHotelRoom implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            int index = mainTable.getSelectedRow();
            if (index >= 0) {

                try {
                    commentLabel.setText("book");
                    HotelRoom room = hotelTableModel.getHotelRoom(index);
                    controller.bookRoom(room);
                    commentLabel.setText("");
                }
                catch (RecordAlreadyExistException e) {
                    logger.warning(e.getMessage());
                    commentLabel.setText("error whilst booking room");
                }
            }
        }
    }

    private class SearchHotelRoom implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            String hotelName = nameSearchField.getText();
            String hotelLocation = locationSearchField.getText();
            commentLabel.setText("search");
            List<HotelRoom> hotelRooms =
                controller.search(hotelName, hotelLocation);

            hotelTableModel.setHotelrooms(hotelRooms);
        }
    }

    private class QuitApplication implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

}
