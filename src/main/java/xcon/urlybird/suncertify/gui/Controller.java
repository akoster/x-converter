package xcon.urlybird.suncertify.gui;

import xcon.urlybird.suncertify.db.DBAccess;
import xcon.urlybird.suncertify.db.RecordNotFoundException;
import xcon.urlybird.suncertify.model.HotelRoom;

public class Controller {

    private DBAccess dbAccess;

    public Controller() {
        
        //
        
    }

    public TableModel getHotelRooms() {
        TableModel out = new TableModel();

        // create dummy hotelRooms
        //

        HotelRoom room1 =
            new HotelRoom("1", "best resort", "italie", "2", "y", "129",
                "12-12-2008", "mohamed");
        out.addHotelRoomRecord(room1);
        HotelRoom room2 =
            new HotelRoom("2", " last resort", "spanje", "2", "y", "100",
                "12-12-2009", "piet");
        out.addHotelRoomRecord(room2);

        return out;
    }

    public void bookRoom(long id) {
        
        System.out.println("booking room " +id);
        try {
            dbAccess.lockRecord(id);
        }
        catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (RecordNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
