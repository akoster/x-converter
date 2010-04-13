package xcon.hotel.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.model.HotelRoom;

public class ControllerImpl implements Controller {

    private DBAccess dbAccess;

    private static Logger logger = Logger.getLogger("hotel-application");

    private String[] columnNames;

    public ControllerImpl(DBAccess dbAccess)
            throws DbAccesssInitializationException
    {

        logger.info("initializing controller object");
        this.dbAccess = dbAccess;
        try {
            // convention to request meta data by reading record -1
            columnNames = dbAccess.readRecord(-1L);
        }
        catch (RecordNotFoundException e) {
            throw new DbAccesssInitializationException(
                "Could not read columns", e);
        }
    }

    public void bookRoom(HotelRoom hotelRoom) throws ControllerException {

        long id = hotelRoom.getId();
        long lockCookie;
        try {
            // read
            lockCookie = dbAccess.lockRecord(id);
            dbAccess.updateRecord(id, hotelRoom.convertToArray(), lockCookie);
            dbAccess.unlock(id, lockCookie);
        }
        catch (RecordNotFoundException e) {
            throw new ControllerException(
                "the room you wanted to book could not be found", e);
        }
        catch (SecurityException e) {
            throw new ControllerException(
                "you are not allowed to book the room", e);
        }

    }

    public List<HotelRoom> search(String hotelName, String hotelLocation) {

        if (hotelName == null || hotelLocation == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }

        List<HotelRoom> result = new ArrayList<HotelRoom>();
        String[] criteria = new String[] {
                hotelName, hotelLocation
        };

        long[] roomIds = dbAccess.findByCriteria(criteria);

        for (long id : roomIds) {
            String[] roomFields;
            try {
                roomFields = dbAccess.readRecord(id);

                logger.info("roomFields are:" + Arrays.asList(roomFields));
                HotelRoom hotelRoom = new HotelRoom(id, roomFields);
                result.add(hotelRoom);
            }
            catch (RecordNotFoundException e) {
                logger.severe("room " + id + " mysteriously disappeared");
            }
        }
        return result;
    }

    /* @see xcon.hotel.client.Controller#getColumnNames() */
    @Override
    public String[] getColumnNames() {
        return columnNames;
    }

}
