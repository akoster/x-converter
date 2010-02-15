package xcon.urlybird.suncertify.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import xcon.urlybird.suncertify.db.DBAccess;
import xcon.urlybird.suncertify.db.RecordDoesAllreadyExistException;
import xcon.urlybird.suncertify.db.RecordNotFoundException;
import xcon.urlybird.suncertify.db.SecurityException;
import xcon.urlybird.suncertify.db.local.DbAccessFileImpl;
import xcon.urlybird.suncertify.db.stub.DbAccessStub;
import xcon.urlybird.suncertify.db.utils.Utils;
import xcon.urlybird.suncertify.model.HotelRoom;

public class Controller {

    private DBAccess dbAccess;
    private DBAccess dbAccessFileImpl;

    private Logger logger = Logger.getLogger("urlybirdApplication");

    public Controller() {

        // should depend on startup parameters of the application
        logger.info("initializing controller object");
        // dbAccess = new DbAccessStub();
        try {
            dbAccess = new DbAccessFileImpl();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public HotelRoom bookRoom(long id) throws RecordDoesAllreadyExistException {

        HotelRoom hotelRoom = null;
        try {

            String[] roomFields = dbAccess.readRecord(id);
            String owner = roomFields[roomFields.length - 1];
            if (owner != null) {
                throw new RecordDoesAllreadyExistException(
                    "record Already Exists");
            }
            else {
                long lockCookie = dbAccess.lockRecord(id);
                String ownerId = Utils.generateCustumerId(id);
                hotelRoom = new HotelRoom(id, roomFields);
                hotelRoom.setOwner(ownerId);
                dbAccess.updateRecord(
                        id,
                        hotelRoom.convertToArray(),
                        lockCookie);
                dbAccess.unlock(id, lockCookie);
            }

            //

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
