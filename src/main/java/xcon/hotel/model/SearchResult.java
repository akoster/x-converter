package xcon.hotel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for search results and total number of rooms found
 * @author Mohamed
 */
public class SearchResult {

    private List<HotelRoom> rooms = new ArrayList<HotelRoom>();
    private int totalRooms;

    public List<HotelRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<HotelRoom> rooms) {
        this.rooms = rooms;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

}
