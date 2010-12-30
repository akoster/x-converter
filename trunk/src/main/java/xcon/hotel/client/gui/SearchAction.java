package xcon.hotel.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextField;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.SearchResult;

public class SearchAction implements ActionListener {

    private Logger logger = Logger.getLogger(SearchAction.class.getName());
    private JTextField nameSearchField;
    private JTextField locationSearchField;
    private Controller controller;
    private JTable hotelTable;
    private Properties hotelRoomProperties;
    private ResourceBundle messages;
    private int pageSize;

    public SearchAction(Controller controller,
                        JTable hotelTable,
                        JTextField nameSearchField,
                        JTextField locationSearchField,
                        ResourceBundle messages,
                        Properties properties,
                        int pageSize)
    {

        this.controller = controller;
        this.hotelTable = hotelTable;
        this.nameSearchField = nameSearchField;
        this.locationSearchField = locationSearchField;
        this.messages = messages;
        this.hotelRoomProperties = properties;
        this.pageSize = pageSize;
    }

    public void actionPerformed(ActionEvent ae) {

        String hotelName = nameSearchField.getText();
        String hotelLocation = locationSearchField.getText();
        logger.info("searching for hotelRoom:" + hotelName
            + "and and hotelLocation " + hotelLocation);
        if (hotelName == null || hotelLocation == null) {
            return;
        }

        int currentPage = 1;
        try {
            SearchResult result =
                controller.search(
                        hotelName,
                        hotelLocation,
                        currentPage,
                        pageSize);

            ((HotelTableModel) hotelTable.getModel()).setHotelrooms(result.getRooms());
            ((HotelTableModel) hotelTable.getModel()).fireTableDataChanged();
            StatusPanel.setCommentLabel(result.getTotalRooms()
                + " rooms have been found");

            hotelRoomProperties.setProperty("hotelName", hotelName);
            hotelRoomProperties.setProperty("hotelLocation", hotelLocation);
            int totalRooms = result.getTotalRooms();
            logger.info("number of totalRooms:" + totalRooms);
            int pagsSize =
                Integer.parseInt(hotelRoomProperties.getProperty("pageSize"));
            logger.info("the pageSize from the properties file is:" + pagsSize);
            int numberOfPages = totalRooms / pagsSize;

            logger.info("number of Pages to show:" + numberOfPages);
            hotelRoomProperties.setProperty(
                    "NumberOfPagesToShow",
                    String.valueOf(numberOfPages));
            try {
                hotelRoomProperties.store(new FileOutputStream(
                    "hotel.properties"), "Author: mohamed loudiyi");
            }
            catch (FileNotFoundException e) {
                logger.warning("file not found");
                e.printStackTrace();
            }
            catch (IOException e) {
                logger.warning("io exception has occured");
                e.printStackTrace();
            }
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
        }
    }
}
