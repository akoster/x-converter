package xcon.hotel.client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.RecordAlreadyExistException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.model.HotelRoom;

public class ControllerImpl implements Controller {

    private DBAccess dbAccess;

    private Logger logger = Logger.getLogger(ControllerImpl.class.getName());

    public ControllerImpl(DBAccess dbAccess) {

        this.dbAccess = dbAccess;
        logger.info("initializing controller object");
    }

    /*
     * (non-Javadoc)
     * @see xcon.hotel.client.Controller#bookRoom(long)
     */
    public HotelRoom bookRoom(long id) throws RecordAlreadyExistException {

        HotelRoom hotelRoom = null;
        try {

            String[] roomFields = dbAccess.readRecord(id);
            String owner = roomFields[roomFields.length - 1];
            if (owner != null) {
                throw new RecordAlreadyExistException("record Already Exists");
            }
            else {
                long lockCookie = dbAccess.lockRecord(id);
                String ownerId = CustomerIdGenerator.generateCustumerId(id);
                hotelRoom = new HotelRoom(id, roomFields);
                hotelRoom.setOwner(ownerId);
                dbAccess.updateRecord(
                        id,
                        hotelRoom.convertToArray(),
                        lockCookie);
                dbAccess.unlock(id, lockCookie);
            }

        }
        catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (RecordNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return hotelRoom;
    }

    /*
     * (non-Javadoc)
     * @see xcon.hotel.client.Controller#search(java.lang.String,
     * java.lang.String)
     */
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
                
                System.out.println("roomFields are:" + Arrays.asList(roomFields));
                HotelRoom hotelRoom = new HotelRoom(id, roomFields);
                result.add(hotelRoom);
            }
            catch (RecordNotFoundException e) {
                System.err.println("room " + id + " mysteriously disappeared");
            }
        }
        return result;
    }
}
