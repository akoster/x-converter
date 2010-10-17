package xcon.hotel.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.HotelNetworkException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.model.HotelRoom;

public class ControllerImpl implements Controller {

    private DBAccess dbAccess;

    private static Logger logger =
        Logger.getLogger(ControllerImpl.class.getName());

    private String[] columnNames;

    public ControllerImpl(DBAccess dbAccess) throws ControllerException {

        logger.info("initializing controller object");
        this.dbAccess = dbAccess;
        try {
            // convention to request meta data by reading record -1
            columnNames = dbAccess.readRecord(-1L);
        }
        catch (RecordNotFoundException e) {
            throw new ControllerException("Could not read columns", e);
        }
        catch (HotelNetworkException e) {
            throw new ControllerException("error.network", e);
        }
    }

    public void bookRoom(long customerId, HotelRoom hotelRoom)
            throws ControllerException
    {
        // precondition: hotelRoom.getOwner() == null
        logger.info("booking room with customerId: " + customerId);
        long id = hotelRoom.getId();
        Long lockCookie = null;
        try {
            // begin 'transaction'
            lockCookie = dbAccess.lockRecord(id);

            // read the room again to be certain it's available
            HotelRoom currentRoom = new HotelRoom(id, dbAccess.readRecord(id));
            if (currentRoom.getOwner() != null) {
                logger.info("Room already booked " + customerId);
                // copy new owner into room
                hotelRoom.setOwner(currentRoom.getOwner());
                throw new ControllerException(null,
                    "gui.jlabel.info.room.allready.booked");
            }
            hotelRoom.setOwner(customerId);
            dbAccess.updateRecord(id, hotelRoom.convertToArray(), lockCookie);
        }
        catch (RecordNotFoundException e) {
            throw new ControllerException(e, "validation.room.not.found");
        }
        catch (SecurityException e) {
            throw new ControllerException("error.internal", e);
        }
        catch (HotelNetworkException e) {
            throw new ControllerException("error.network", e);
        }
        finally {
            // end 'transaction'
            if (lockCookie != null) {
                try {
                    dbAccess.unlock(id, lockCookie);
                }
                catch (SecurityException e) {
                    logger.severe("Could not unlock cookie " + lockCookie);
                }
            }
            logger.info("Transaction done " + customerId);
        }
    }

    public List<HotelRoom> search(String hotelName, String hotelLocation)
            throws ControllerException
    {
        if (hotelName == null || hotelLocation == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }
        try {
            List<HotelRoom> result = new ArrayList<HotelRoom>();
            
          
            String[] criteria = new String[] {
                    hotelName, hotelLocation
            };
            long[] roomIds = dbAccess.findByCriteria(criteria);
            // TODO:caching approach
            // compare first value (magic cookie) to our last received magic cookie
            // if cookies match, then do not read records again
            // when reading rooms skip first value             
            for (long id : roomIds) {
                String[] roomFields;
                try {
                    roomFields = dbAccess.readRecord(id);

                    logger.fine("roomFields are:" + Arrays.asList(roomFields));
                    HotelRoom hotelRoom = new HotelRoom(id, roomFields);
                    result.add(hotelRoom);
                }
                catch (RecordNotFoundException e) {
                    logger.severe("room " + id + " mysteriously disappeared");
                }
            }
            return result;
        }
        catch (HotelNetworkException e) {
            throw new ControllerException("error.network", e);
        }
    }

    /* @see xcon.hotel.client.Controller#getColumnNames() */
    @Override
    public String[] getColumnNames() {
        return columnNames;
    }

    @Override
    public String[] readRecord(int index) throws RecordNotFoundException {
        String[] record = null;
        record = dbAccess.readRecord(index);
        return record;
    }

}
