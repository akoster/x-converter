package xcon.hotel.client.gui.screen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.SearchResult;

public class HotelRoomScreen extends JPanel {

    private static final long serialVersionUID = 5165L;
    private Logger logger = Logger.getLogger(HotelRoomScreen.class.getName());

    private JTextField nameSearchField = new JTextField(20);
    private JTextField locationSearchField = new JTextField(20);

    private int numberOfPages;
    private JButton[] navigationButtons;

    private Controller controller;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private int currentPage;
    private ResourceBundle messages;
    private String searchHotelName;
    private String searchLocation;
    int pageSize;

    private int totalHotelRoomsAmount;

    private JTable mainTable = new JTable();
    private JLabel commentLabel = new JLabel();

    private HotelTableModel hotelTableModel;

    private ResourceBundle hotelRoomProperties;

    public HotelRoomScreen(Controller controller, HotelTableModel hotelTableModel, ResourceBundle messages) {

        this.controller = controller;
        this.messages = messages;
        this.hotelTableModel = hotelTableModel ;


        commentLabel.setText(" ");
        logger.info("number of hotels to display in the gui :" + pageSize);
        setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        JScrollPane tableScroll = new JScrollPane(mainTable);
        tableScroll.setSize(500, 250);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        hotelRoomProperties = ResourceBundle.getBundle("hotel");
        pageSize =
            Integer.parseInt(hotelRoomProperties.getString("gui_room_display"));

        // TODO read from images from the resource folder
        // " resources/images/back.gif" and " resources/images/back.gif"
        // JButton backButton = new JButton(new
        // ImageIcon(getClass().getResource("back.gif")));

        try {
            searchHotelName = "";
            searchLocation = "";
            currentPage = 1;
            SearchResult result =
                controller.search(
                        searchHotelName,
                        searchLocation,
                        currentPage,
                        pageSize);
            totalHotelRoomsAmount = result.getTotalRooms();
            hotelTableModel.setHotelrooms(result.getRooms());
            mainTable.setModel(hotelTableModel);
        }
        catch (ControllerException e) {
            logger.warning("A Controller Exception has occuerd"
                + e.getStackTrace());
            commentLabel.setText(messages.getString(e.getMessageKey()));
            return;
        }

        logger.info("creating a backButton");
        JButton backButton =
            new JButton(new ImageIcon(this.getClass().getResource("/back.gif")));

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                if (currentPage > 1) {
                    displayPage(currentPage - 1);
                }
            }
        });

        // This navigationNumbersPanel is only for creation the
        // numberbuttons to navigate
        JPanel navigationNumbersPanel = new JPanel();
        numberOfPages =
            (int) Math.ceil(((double) totalHotelRoomsAmount)
                / ((double) pageSize));

        logger.info("number of pages are: " + numberOfPages);
        navigationButtons = new NavigationButton[numberOfPages];
        for (int page = 1; page <= numberOfPages; page++) {

            navigationButtons[page - 1] = new NavigationButton(page);
            navigationButtons[page - 1].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    // TODO Auto-generated method stub

                    Object source = event.getSource();
                    if (source instanceof NavigationButton) {
                        NavigationButton selectedButton =
                            (NavigationButton) source;
                        displayPage(selectedButton.getPageNumber());
                    }

                }
            });
            navigationNumbersPanel.add(navigationButtons[page - 1]);
        }

        //
        JButton nextButton =
            new JButton(new ImageIcon(this.getClass().getResource("/next.gif")));

        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPage < numberOfPages) {
                    displayPage(currentPage + 1);
                }
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
        locationLabel = new JLabel(messages.getString("gui.label.location"));
        searchPanel.add(locationLabel);
        searchPanel.add(locationSearchField);
        searchPanel.add(searchButton);

        JLabel bookingLabel = new JLabel();
        bookingLabel.setText(messages.getString("gui.label.enter.customerid"));

        JTextField ownerIdTextField = new JTextField(8);
        JButton BookButton = new JButton(messages.getString("gui.button.book"));

        BookButton.addActionListener(new HotelRoomBooking(ownerIdTextField,
            mainTable, messages, controller, commentLabel, hotelTableModel));
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

    private void displayPage(int pageNumber) {

        currentPage = pageNumber;
        try {
            SearchResult result =
                controller.search(
                        searchHotelName,
                        searchLocation,
                        currentPage,
                        pageSize);
            // totalHotelRoomsAmount = result.getTotalRooms();

            hotelTableModel.setHotelrooms(result.getRooms());
            hotelTableModel.fireTableDataChanged();
        }
        catch (ControllerException e) {
            commentLabel.setText(messages.getString(e.getMessageKey()));
        }
    }

    private class SearchHotelRoom implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            String hotelName = nameSearchField.getText();
            String hotelLocation = locationSearchField.getText();
            if (hotelName == null || hotelLocation == null) {
                return;
            }

            searchHotelName = hotelName;
            searchLocation = hotelLocation;
            currentPage = 1;
            displayPage(currentPage);
        }
    }

}