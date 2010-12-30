package xcon.hotel.client;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import xcon.hotel.db.SwingGuiException;
import xcon.hotel.model.HotelRoom;
import xcon.hotel.model.HotelRoomSearch;

public class HotelTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5165L;

    private static Logger logger =
        Logger.getLogger(HotelTableModel.class.getName());
    private String[] headerNames = null;

    private HotelRoomSearch hotelRoomSearch;

    public HotelTableModel(String[] columnNames, ResourceBundle messages)
            throws SwingGuiException
    {
        logger.info("constructing the HotelTableModedel");
        headerNames = new String[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            headerNames[i] =
                messages.getString("hotel.table.column.header."
                    + columnNames[i]);
        }
        hotelRoomSearch = new HotelRoomSearch();

        String pageSizePropertyValue =
            messages.getString("hotel.table.pagesize");
        try {
            int pageSize = Integer.parseInt(pageSizePropertyValue);
            hotelRoomSearch.setPageSize(pageSize);
        }
        catch (NumberFormatException e) {
            throw new SwingGuiException("Could not parse hotel.table.pagesize "
                + pageSizePropertyValue, e);
        }
    }

    public HotelRoomSearch getHotelRoomSearch() {
        return hotelRoomSearch;
    }

    /* @see javax.swing.table.TableModel#getColumnCount() */
    public int getColumnCount() {
        return headerNames.length;
    }

    /* @see javax.swing.table.TableModel#getRowCount() */
    public int getRowCount() {
        return hotelRoomSearch.getRooms().size();
    }

    public HotelRoom getHotelRoom(int row) {
        return hotelRoomSearch.getRooms().get(row);
    }

    /* @see javax.swing.table.TableModel#getValueAt(int, int) */
    public Object getValueAt(int row, int column) {

        HotelRoom room = hotelRoomSearch.getRooms().get(row);
        Object value = null;
        if (column == 0) {
            value = room.getName();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 1) {
            value = room.getLocation();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 2) {
            value = room.getSize();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 3) {
            value = room.getSmoking();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 4) {
            value = room.getRate();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 5) {
            value = room.getDate();
            logger.fine("column: " + column + " value: " + value);
        }
        else if (column == 6) {
            value = room.getOwner();
            logger.fine("column: " + column + " value: " + value);
        }

        return value;
    }

    /* @see javax.swing.table.AbstractTableModel#getColumnName(int) */
    public String getColumnName(int column) {
        return headerNames[column];
    }

    /* @see javax.swing.table.AbstractTableModel#isCellEditable(int, int) */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public int getTabelHeaderSize() {
        return headerNames.length;
    }
}
