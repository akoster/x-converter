package xcon.hotel.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
import org.mockito.internal.matchers.InstanceOf;
import xcon.atm.event.ScreenEvent;
import xcon.hotel.HotelApplication;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.DbAccesssInitializationException;
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

    List<HotelRoom> rooms;
    private ResourceBundle messages;

    // HOME_WORK :
    //
    private ResourceBundle hotelRoomProperties;
    private static int totalHotelRoomsAmount;
    private static int guiDisplayAmount;
    int pagingButtonsAmount;
    private Navigation navigation = new Navigation();;
    private JButton[] navigationButtons;

    private int currentNavigationPointer;

    public SwingGui(Controller controller) {

        messages =
            ResourceBundle.getBundle("hotel_messages", Locale.getDefault());
        setTitle(messages.getString("gui.frame.application.name"));

        hotelRoomProperties = ResourceBundle.getBundle("hotel");

        guiDisplayAmount =
            Integer.parseInt(hotelRoomProperties.getString("gui_room_display"));

        logger.info("numerber of hotels to display in the gui :"
            + guiDisplayAmount);

        // set dependency
        this.controller = controller;

        setDefaultCloseOperation(SwingGui.EXIT_ON_CLOSE);

        // initialize status bar so it shows up
        commentLabel.setText(" ");

        // initialize the TableModel
        hotelTableModel =
            new HotelTableModel(controller.getColumnNames(), messages);
        // 1: search for all rooms

        try {
            rooms = controller.search("", "");
            totalHotelRoomsAmount = rooms.size();

            navigation.displayRooms(rooms, 1, guiDisplayAmount);

            // 2: add all rooms to table model
            // hotelTableModel.setHotelrooms(rooms);
            mainTable.setModel(hotelTableModel);
        }
        catch (ControllerException e) {
            commentLabel.setText(messages.getString(e.getMessageKey()));
            return;
        }

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

            JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new BorderLayout());

            JScrollPane tableScroll = new JScrollPane(mainTable);
            tableScroll.setSize(500, 250);
            tablePanel.add(tableScroll, BorderLayout.CENTER);

            JPanel navigationPanel = new JPanel();
            // TODO read from images from the rsource folder
            // " resources/images/back.gif" and " resources/images/back.gif"
            // JButton backButton = new JButton(new
            // ImageIcon(getClass().getResource("back.gif")));

            JButton backButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/back.gif")));

            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    --currentNavigationPointer;
                    if (currentNavigationPointer <= 0) {
                        ++currentNavigationPointer;
                        return;
                    }
                    navigation.displayRooms(
                            rooms,
                            currentNavigationPointer,
                            guiDisplayAmount);
                }
            });

            JPanel navigationNumbersPanel = new JPanel();

            final Navigation navigation = new Navigation();
            navigation.createNavigationButtons(navigationNumbersPanel);

            JButton nextButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/next.gif")));

            nextButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    // waarom is moet de variable navigation final en hoe kan ik
                    // die oplossen
                    ++currentNavigationPointer;
                    if (currentNavigationPointer > pagingButtonsAmount) {

                        --currentNavigationPointer;
                        return;
                    }
                    navigation.displayRooms(
                            rooms,
                            currentNavigationPointer,
                            guiDisplayAmount);
                }
            });
            navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            navigationPanel.add(backButton);
            navigationPanel.add(navigationNumbersPanel);

            navigationPanel.add(nextButton);
            tablePanel.add(navigationPanel, BorderLayout.SOUTH);

            add(tablePanel, BorderLayout.CENTER);

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

    private class Navigation {

        private JPanel createNavigationButtons(JPanel navigationNumbersPanel) {
            pagingButtonsAmount = totalHotelRoomsAmount / guiDisplayAmount;
            navigationButtons = new NavigationButton[pagingButtonsAmount];
            int buttonNumber = 0;
            for (int i = 0; i < pagingButtonsAmount; i++) {

                ++buttonNumber;
                navigationButtons[i] =
                    new NavigationButton(String.valueOf(buttonNumber),
                        buttonNumber);
                navigationButtons[i].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // TODO Auto-generated method stub

                        Object source = event.getSource();
                        if (source instanceof NavigationButton) {
                            NavigationButton selectedButton =
                                (NavigationButton) source;
                            displayRooms(
                                    rooms,
                                    selectedButton.getButtonNumber(),
                                    guiDisplayAmount);
                        }

                    }
                });
                navigationNumbersPanel.add(navigationButtons[i]);

            }

            return null;

        }

        public void displayRooms(List<HotelRoom> rooms,
                                 int buttonNumber,
                                 int guiDisplayAmount)
        {

            logger.info("rooms will be displayed of buttonNumber: "
                + buttonNumber + " and the displayamount is: "
                + guiDisplayAmount);
            int startDispalyindex =
                Integer.valueOf(guiDisplayAmount) * buttonNumber
                    - guiDisplayAmount + 1;
            logger.info("startDisplayIndex: " + startDispalyindex);

            int endDisplayIndex =
                Integer.valueOf(buttonNumber)
                    * Integer.valueOf(guiDisplayAmount);

            if (endDisplayIndex > rooms.size()) {
                endDisplayIndex = rooms.size();
            }
            logger.info("endDisplayIndex: " + endDisplayIndex);

            List<HotelRoom> subListRooms = new ArrayList<HotelRoom>();
            for (int i = startDispalyindex; i <= endDisplayIndex; i++) {

                subListRooms.add(rooms.get(i - 1));
            }
            hotelTableModel.setHotelrooms(subListRooms);
            currentNavigationPointer = buttonNumber;

        }

        private class NavigationButton extends JButton {

            private int buttonNumber;
            private static final long serialVersionUID = 2L;

            public NavigationButton(String text, int buttonNumber) {
                super(text);
                this.buttonNumber = buttonNumber;
            }

            public int getButtonNumber() {
                return buttonNumber;
            }

        }
    }

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
            logger.info("room to be booked" + room.toString());
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
                customerIdField.setText("");
            }
            catch (ControllerException e) {
                commentLabel.setText(messages.getString(e.getMessageKey()));
                return;
            }
            finally {
                hotelTableModel.fireTableDataChanged();
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

            try {
                List<HotelRoom> hotelRooms =
                    controller.search(hotelName, hotelLocation);

                commentLabel.setText(hotelRooms.size() + " "
                    + messages.getString("gui.jlabel.info.search.result"));
                hotelTableModel.setHotelrooms(hotelRooms);
                navigation.displayRooms(hotelRooms, 1, guiDisplayAmount);
            }
            catch (ControllerException e) {
                commentLabel.setText(messages.getString(e.getMessageKey()));
                return;
            }
        }
    }

    private class QuitApplication implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

}