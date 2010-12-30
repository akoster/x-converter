package xcon.hotel.client.gui;

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

public class BookAction implements ActionListener {

    private Logger logger = Logger.getLogger(BookAction.class.getName());
    private JTextField customerIdField = null;
    private JTable mainTable;
    private ResourceBundle messages;
    private Controller controller ;
    
    public BookAction(JTextField customerIdField , JTable mainTable, ResourceBundle messages, Controller controller) {
        this.customerIdField = customerIdField;
        this.mainTable = mainTable ;
        this.messages = messages;
        this.controller = controller;
    }


    public void actionPerformed(ActionEvent ae) {

        int index = mainTable.getSelectedRow();
        if (index < 0) {
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.room.not.selected"));
            return;
        }

        HotelRoom room = ((HotelTableModel) mainTable.getModel()).getHotelRoom(index);
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
            StatusPanel.setCommentLabel(messages.getString("gui.jlabel.info.room.is.booked"));
            customerIdField.setText("");
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
            return;
        }
        finally {
            //hotelTableModel.fireTableDataChanged();
            ((HotelTableModel) mainTable.getModel()).fireTableDataChanged();
        }

    }
}