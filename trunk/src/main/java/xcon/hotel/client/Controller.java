package xcon.hotel.client;

import xcon.hotel.db.ControllerException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.model.HotelRoom;
import xcon.hotel.model.SearchResult;

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
     * @param hotelName
     * @param hotelLocation
     * @param page
     * @param pageSize
     * @return
     * @throws ControllerException
     */
    public SearchResult search(String hotelName,
                               String hotelLocation,
                               int page,
                               int pageSize) throws ControllerException;

    /**
     * The columnNmaes which are determined in the constructor of the Dbacces
     * implementation will be return by this method
     * @return
     */
    public abstract String[] getColumnNames();

    public abstract String[] readRecord(int index)
            throws RecordNotFoundException;

}