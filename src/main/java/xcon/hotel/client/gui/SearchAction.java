package xcon.hotel.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JTextField;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;

public class SearchAction implements ActionListener {

    private Logger logger = Logger.getLogger(SearchAction.class.getName());

    private Controller controller;
    private ResourceBundle messages;
    private HotelTableModel hotelTableModel;
    private JTextField nameSearchField;
    private JTextField locationSearchField;

    public SearchAction(Controller controller,
                        ResourceBundle messages,
                        HotelTableModel hotelTableModel,
                        JTextField nameSearchField,
                        JTextField locationSearchField)
    {
        this.controller = controller;
        this.messages = messages;
        this.hotelTableModel = hotelTableModel;
        this.nameSearchField = nameSearchField;
        this.locationSearchField = locationSearchField;
    }

    public void actionPerformed(ActionEvent ae) {

        String hotelName = nameSearchField.getText();
        String hotelLocation = locationSearchField.getText();
        logger.info("searching for hotelRoom:" + hotelName
            + "and and hotelLocation " + hotelLocation);
        // sanity check
        if (hotelName == null || hotelLocation == null) {
            return;
        }

        hotelTableModel.getHotelRoomSearch().setHotelName(hotelName);
        hotelTableModel.getHotelRoomSearch().setHotelLocation(hotelLocation);
        hotelTableModel.getHotelRoomSearch().setPage(1);

        try {
            controller.search(hotelTableModel.getHotelRoomSearch());
            // XXX: ugly that this is required as separate call. Maybe fix?
            hotelTableModel.fireTableDataChanged();

            StatusPanel.setCommentLabel(hotelTableModel.getHotelRoomSearch().getTotalRooms()
                + " " + messages.getString("info.rooms.found"));
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
        }
    }
}
