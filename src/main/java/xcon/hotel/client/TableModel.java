package xcon.hotel.client;

import java.util.*;
import java.util.logging.*;
import javax.swing.table.*;
import xcon.hotel.model.HotelRoom;

public class TableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5165L;

    private Logger log = Logger.getLogger(TableModel.class.getName());

    // XXX: should be read from database
    private String[] headerNames = null ;
        /*{
                "id", "hotelName", "City ", "size", "smoking",
                "price per Night", "date", "owner"
        };*/
    // XXX: replace List<String[]> with private List<Hotelroom>
    private List<HotelRoom> hotelRoomRecords = new ArrayList<HotelRoom>();

    public TableModel(String [] columnNames)
    {
      this.headerNames = columnNames ;
    }
 
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

        HotelRoom room = this.hotelRoomRecords.get(row);
        Object value = null;
        if (column == 0) {
            value = room.getId();
        }
        if (column == 1) {
            value = room.getName();
        }
        else if (column == 2) {
            value = room.getLocation();
        }
        else if (column == 3) {
            value = room.getSize();
        }
        else if (column == 4) {
            value = room.getSmoking();
        }
        else if (column == 5) {
            value = room.getRate();
        }
        else if (column == 6) {
            value = room.getDate();
        }
        else if (column == 7) {
            value = room.getOwner();
        }

        return value;
    }

    /*
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
     * int, int)
     */
    public void setValueAt(Object obj, int row, int column) {
        HotelRoom hotelRoom = this.hotelRoomRecords.get(row);

        if (column == 0 )
        {
            hotelRoom.setId( (Integer) obj );
        }
        if (column == 1) {
            hotelRoom.setName((String) obj);
        }
        else if (column == 2) {
            hotelRoom.setLocation((String) (obj));
        }
        else if (column == 3) {
            hotelRoom.setSize((Integer) (obj));
        }
        else if (column == 4) {
            hotelRoom.setSmoking((String) (obj));
        }
        else if (column == 5) {
            hotelRoom.setRate((String) (obj));
        }
        else if (column == 6) {
            hotelRoom.setDate((String) (obj));
        }
        else if (column == 7) {
            hotelRoom.setOwner((String) (obj));
        }
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
/*
    public void addHotelRoomRecord(String id,
                                   String hotelName,
                                   String city,
                                   String size,
                                   String smoking,
                                   String price_per_night,
                                   String date,
                                   String owner)
    {

        String[] temp =
            {
                    id, hotelName, city, size, smoking, price_per_night, date,
                    owner
            };
        this.hotelRoomRecords.add(temp);
        fireTableDataChanged();
    }*/

    public void clear() {
        hotelRoomRecords.clear();
    }

    public void addHotelRoomRecord(HotelRoom hotelRoom) {
        this.hotelRoomRecords.add(hotelRoom);
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
