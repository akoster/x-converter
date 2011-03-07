package xcon.hotel.client.gui;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.SwingGuiException;

class TablePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable hotelRoomTable;

    public TablePanel(Controller controller,
                      ResourceBundle messages,
                      HotelTableModel hotelTableModel) throws SwingGuiException
    {
        setLayout(new BorderLayout());

        hotelRoomTable = new JTable();
        
        
        hotelRoomTable.setModel(hotelTableModel);
        hotelRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hotelRoomTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        hotelRoomTable.setToolTipText(messages.getString("gui.Jtabel.maintable.tooltiptext"));
        
        
        SelectionListener listener = new SelectionListener(hotelRoomTable);
        hotelRoomTable.getSelectionModel().addListSelectionListener(listener);
        hotelRoomTable.getColumnModel().getSelectionModel()
            .addListSelectionListener(listener);

        JScrollPane tableScroll = new JScrollPane(hotelRoomTable);
        tableScroll.setSize(500, 250);
        add(tableScroll, BorderLayout.CENTER);

        NavigationPanel navigationPanel =
            new NavigationPanel(controller, messages, hotelTableModel);
        add(navigationPanel, BorderLayout.SOUTH);

        try {
            controller.search(hotelTableModel.getHotelRoomSearch());
        }
        catch (ControllerException e) {
            throw new SwingGuiException("Could not initialize Table panel", e);
        }
    }

    public JTable getHotelTable() {
        return hotelRoomTable;

    }

}