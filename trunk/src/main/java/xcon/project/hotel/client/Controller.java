package xcon.project.hotel.client;

import xcon.project.hotel.db.ControllerException;
import xcon.project.hotel.db.RecordNotFoundException;
import xcon.project.hotel.model.HotelRoom;
import xcon.project.hotel.model.HotelRoomSearch;

/**
 * Controller with the business logic of the hotel application
 */
public interface Controller {

    /**
     * Books the room for the given customer id
     * @param customerId
     * @param room
     * @throws ControllerException
     */
    public abstract void bookRoom(long customerId, HotelRoom room)
            throws ControllerException;

    /**
     * Searches for a room with the given hotel name and/or location
     * @param hotelRoomSearch
     * @throws ControllerException
     */
    public void search(HotelRoomSearch hotelRoomSearch)
            throws ControllerException;

    /**
     * The columnNmaes which are determined in the constructor of the Dbacces
     * implementation will be return by this method
     * @return
     */
    public abstract String[] getColumnNames();

    public abstract String[] readRecord(int index)
            throws RecordNotFoundException;

}