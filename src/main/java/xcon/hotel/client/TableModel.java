package xcon.hotel.client;

import java.util.*;
import java.util.logging.*;
import javax.swing.table.*;

import xcon.hotel.model.HotelRoom;

public class TableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5165L;

    private Logger log = Logger.getLogger(TableModel.class.getName());

    // XXX: should be read from database
    private String[] headerNames =
        {
                "id","valid/deleted", "hotelName", "City ", "persons", "smoking",
                "price per Night", "date", "owner"
        };
    // XXX: replace List<String[]> with private List<Hotelroom>
    private List<String[]> hotelRoomRecords = new ArrayList<String[]>();

    /* @see javax.swing.table.TableModel#getColumnCount() */
    public int getColumnCount() {
        return this.headerNames.length;
    }

    /* @see javax.swing.table.TableModel#getRowCount() */
    public int getRowCount() {
        return this.hotelRoomRecords.size();
    }

    /* @see javax.swing.table.TableModel#getValueAt(int, int) */
    public Object getValueAt(int row, int column) {

        String[] rowValues = this.hotelRoomRecords.get(row);
        return rowValues[column];
    }

    /*
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
     *      int, int)
     */
    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.hotelRoomRecords.get(row);
        rowValues[column] = obj;
        fireTableDataChanged();
    }

    /* @see javax.swing.table.AbstractTableModel#getColumnName(int) */
    public String getColumnName(int column) {
        return headerNames[column];
    }

    /* @see javax.swing.table.AbstractTableModel#isCellEditable(int, int) */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addHotelRoomRecord(String id,String isValidOrDeleted,
                                   String hotelName,
                                   String city,
                                   String max_occ,
                                   String smoking,
                                   String price_per_night,
                                   String date,
                                   String owner)
    {
        String[] temp =
            {
                    id, isValidOrDeleted, hotelName, city, max_occ, smoking, price_per_night,
                    date, owner
            };
        this.hotelRoomRecords.add(temp);
        fireTableDataChanged();
    }

    public void clear() {
        hotelRoomRecords.clear();
    }

    public void addHotelRoomRecord(HotelRoom hotelRoom) {
        addHotelRoomRecord(
                hotelRoom.getId(),
                hotelRoom.getIsValidOrDeletedRecord(),
                hotelRoom.getName(),
                hotelRoom.getLocation(),
                hotelRoom.getMaxOccupants(),
                hotelRoom.getSmoking(),
                hotelRoom.getRate(),
                hotelRoom.getDate(),
                hotelRoom.getOwner());
    }

    public void addHotelrooms(List<HotelRoom> rooms) {
        for (HotelRoom hotelRoom : rooms) {
            addHotelRoomRecord(hotelRoom);
            
        }
        fireTableDataChanged();
    }

    public int getTabelHeaderSize() {
        return this.headerNames.length;
    }
}
