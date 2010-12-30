package xcon.hotel.client.gui;

import java.awt.BorderLayout;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.SearchResult;

class TablePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(TablePanel.class.getName());
    private JTable mainTable = new JTable();
    private HotelTableModel hotelTableModel;

    TablePanel(Controller controller, ResourceBundle messages, Properties properties, int pageSize) {

        hotelTableModel =
            new HotelTableModel(controller.getColumnNames(), messages);

        
        mainTable.setModel(hotelTableModel);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        mainTable.setToolTipText(messages.getString("gui.Jtabel.maintable.tooltiptext"));

        setLayout(new BorderLayout());
        JScrollPane tableScroll = new JScrollPane(mainTable);
        tableScroll.setSize(500, 250);
        add(tableScroll, BorderLayout.CENTER);
        NavigationPanel navigationPanel =
            new NavigationPanel(controller, mainTable, messages, properties,
                pageSize);
        add(navigationPanel, BorderLayout.SOUTH);
        
        try {
            String searchHotelName = "";
            String searchLocation = "";
            int currentPage = 1;
            SearchResult result =
                controller.search(
                        searchHotelName,
                        searchLocation,
                        currentPage,
                        pageSize);
            hotelTableModel.setHotelrooms(result.getRooms());

        }
        catch (ControllerException e) {
            logger.warning("A Controller Exception has occuerd"
                + e.getStackTrace());
            //commentLabel.setText(messages.getString(e.getMessageKey()));
            return;
        }
    }

    public JTable getHotelTable() {
        return mainTable ;
        
    }

}