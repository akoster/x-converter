package xcon.hotel.client.gui;

import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import xcon.hotel.client.HotelTableModel;

public class SelectionListener implements ListSelectionListener {

    private static Logger logger =
        Logger.getLogger(SelectionListener.class.getName());
    private JTable table;

    SelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // If cell selection is enabled, both row and column change events are fired

        if (e.getSource() == table.getSelectionModel()
            && table.getRowSelectionAllowed())

        {
            ListSelectionModel rowSelectionModel =
                (ListSelectionModel) e.getSource();
            int selectedIndex = rowSelectionModel.getMinSelectionIndex();
            ((HotelTableModel) table.getModel()).setSelectedrow(selectedIndex);
            logger.info("row : " + selectedIndex + " has been selected");
        }
    }

}
