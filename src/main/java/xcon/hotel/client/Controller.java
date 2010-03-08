package xcon.hotel.client;

import java.util.List;

import xcon.hotel.db.RecordAlreadyExistException;
import xcon.hotel.model.HotelRoom;

/**
 * Controller with the business logic of the hotel appliction
 */
public interface Controller {

	/**
	 * Books the room with the given id
	 * 
	 * @param id
	 * @return
	 * @throws RecordAlreadyExistException
	 */
	public abstract HotelRoom bookRoom(long id)
			throws RecordAlreadyExistException;

	/**
	 * Searches for a room with the given hotel name and/or location
	 * 
	 * @param hotelName
	 * @param hotelLocation
	 * @return
	 */
	public abstract List<HotelRoom> search(String hotelName,
			String hotelLocation);

    public abstract String [] getColumnNames();

   

}