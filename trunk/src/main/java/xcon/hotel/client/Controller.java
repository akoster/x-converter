package xcon.hotel.client;

import java.util.List;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.HotelRoom;

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
     * @return
     * @throws ControllerException
     */
    public abstract List<HotelRoom> search(String hotelName,
                                           String hotelLocation)
            throws ControllerException;

    /**
     * The columnNmaes which are determined in the constructor of the Dbacces
     * implementation will be return by this method
     * @return
     */
    public abstract String[] getColumnNames();

}