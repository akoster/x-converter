package xcon.hotel.client.gui.screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.HotelRoom;

public class HotelRoomBooking implements ActionListener {

    private Logger logger = Logger.getLogger(HotelRoomBooking.class.getName());
    private JTextField customerIdField = null;
    private JTable mainTable;
    private ResourceBundle messages;
    private Controller controller ;
    private JLabel commentLabel ;
    private HotelTableModel hotelTableModel ;
    
    public HotelRoomBooking(JTextField customerIdField , JTable mainTable, ResourceBundle messages, Controller controller, JLabel commentLabel, HotelTableModel hotelTableModel) {
        this.customerIdField = customerIdField;
        this.mainTable = mainTable ;
        this.messages = messages;
        this.controller = controller;
        this.commentLabel = commentLabel ;
        this.hotelTableModel = hotelTableModel ;
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