package xcon.project.hotel.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JTextField;
import xcon.project.hotel.client.Controller;
import xcon.project.hotel.client.HotelTableModel;
import xcon.project.hotel.db.ControllerException;
import xcon.project.hotel.model.HotelRoom;

public class BookAction implements ActionListener {

    private Logger logger = Logger.getLogger(BookAction.class.getName());
    private JTextField customerIdField = null;
    private HotelTableModel hotelTableModel;
    private ResourceBundle messages;
    private Controller controller;

    public BookAction(Controller controller,
                      ResourceBundle messages,
                      HotelTableModel hotelTableModel,
                      JTextField customerIdField)
    {
        this.controller = controller;
        this.messages = messages;
        this.hotelTableModel = hotelTableModel;
        this.customerIdField = customerIdField;
    }

    public void actionPerformed(ActionEvent ae) {

        HotelRoom room = hotelTableModel.getSelectedHotelRoom();
        if (room == null) {
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.room.not.selected"));
            return;
        }

        logger.info("room to be booked" + room.toString());
        if (room.getOwner() != null) {
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.room.allready.booked"));
            return;
        }

        String customerIdFieldValue = customerIdField.getText();
        if (customerIdFieldValue.equals("")) {
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.customerid.not.entered"));
            return;
        }

        long customerId;
        try {
            customerId = Long.parseLong(customerIdFieldValue);
        }
        catch (NumberFormatException e) {
            StatusPanel.setCommentLabel("gui.jlabel.info.customerid.not.number");
            return;
        }

        if (customerIdFieldValue.length() != 8) {
            StatusPanel.setCommentLabel(messages.getString("validation.customerid.length"));
            return;
        }

        try {
            controller.bookRoom(customerId, room);
            hotelTableModel.fireTableDataChanged();
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.room.is.booked"));
            customerIdField.setText("");
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
            return;
        }
    }
}