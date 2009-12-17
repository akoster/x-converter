package xcon.urlybird.suncertify.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import xcon.urlybird.suncertify.model.HotelRoom;

public class TableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5165L;

//    private Logger LOG = Logger.getLogger("urlybird.suncertify.gui");

    private String[] headerNames =
        {
                "id", "hotelName", "City ", "Max occ", "smoking",
                "price per Night", "date", "owner"
        };

    private ArrayList<String[]> hotelRoomRecords = new ArrayList<String[]>(7);

    public int getColumnCount() {
        return this.headerNames.length;
    }

    public int getRowCount() {
        return this.hotelRoomRecords.size();
    }

    public Object getValueAt(int row, int column) {
        String[] rowValues = this.hotelRoomRecords.get(row);
        return rowValues[column];
    }

    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.hotelRoomRecords.get(row);
        rowValues[column] = obj;
    }

    public String getColumnName(int column) {
        return headerNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addHotelRoomRecord(String id,
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
                    id, hotelName, city, max_occ, smoking, price_per_night,
                    date, owner
            };
        this.hotelRoomRecords.add(temp);
    }

    public void addHotelRoomRecord(HotelRoom hotelRoom) {
        addHotelRoomRecord(
                hotelRoom.getId(),
                hotelRoom.getName(),
                hotelRoom.getLocation(),
                hotelRoom.getMaxOccupants(),
                hotelRoom.getSmoking(),
                hotelRoom.getRate(),
                hotelRoom.getDate(),
                hotelRoom.getOwner());
    }
}
