package xcon.project.hotel.client;

import java.util.Arrays;
import java.util.logging.Logger;
import xcon.project.hotel.db.ControllerException;
import xcon.project.hotel.db.DBAccess;
import xcon.project.hotel.db.HotelNetworkException;
import xcon.project.hotel.db.RecordNotFoundException;
import xcon.project.hotel.db.SecurityException;
import xcon.project.hotel.model.HotelRoom;
import xcon.project.hotel.model.HotelRoomSearch;

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
            logger.info("lockCookie: " + lockCookie
                + "has been returned by dbAcces");
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
            logger.warning(e.getMessage());
            throw new ControllerException(e, "error.internal");
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

    public void search(HotelRoomSearch hotelRoomSearch) throws ControllerException
    {
        if (hotelRoomSearch.getHotelName() == null || hotelRoomSearch.getHotelLocation() == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }
        try {            
            String[] criteria = new String[] {
                    hotelRoomSearch.getHotelName(), hotelRoomSearch.getHotelLocation()
            };
            long[] roomIds = dbAccess.findByCriteria(criteria);
            hotelRoomSearch.setTotalRooms(roomIds.length);
            // TODO:caching approach
            // compare first value (magic cookie) to our last received magic
            // cookie
            // if cookies match, then do not read records again
            // when reading rooms skip first value
            logger.info("rooms of page: " + hotelRoomSearch.getPage() + " pagesize: " + hotelRoomSearch.getPageSize());
            int startDisplayindex = hotelRoomSearch.getPageSize() * hotelRoomSearch.getPage() - hotelRoomSearch.getPageSize();
            logger.info("startDisplayIndex: " + startDisplayindex);

            int endDisplayIndex = hotelRoomSearch.getPage() * hotelRoomSearch.getPageSize() - 1;
            if (endDisplayIndex >= roomIds.length) {
                endDisplayIndex = roomIds.length - 1;
            }
            logger.info("endDisplayIndex: " + endDisplayIndex);

            //XXX add to design doc
            hotelRoomSearch.getRooms().clear();
            for (int i = startDisplayindex; i <= endDisplayIndex; i++) {

                long id = roomIds[i];
                try {
                    String[] roomFields = dbAccess.readRecord(id);
                    logger.fine("roomFields are:" + Arrays.asList(roomFields));
                    HotelRoom hotelRoom = new HotelRoom(id, roomFields);
                    hotelRoomSearch.getRooms().add(hotelRoom);
                }
                catch (RecordNotFoundException e) {
                    logger.severe("room " + id + " mysteriously disappeared");
                }
            }
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
