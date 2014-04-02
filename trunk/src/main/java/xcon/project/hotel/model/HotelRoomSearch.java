package xcon.project.hotel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for search results and total number of rooms found
 * @author Mohamed
 */
public class HotelRoomSearch {

    // the query
    private String hotelName;
    private String hotelLocation;
    private HotelRoom selectedRoom;
    private int page;
    private int pageSize;

    // the search result
    private List<HotelRoom> rooms;
    private int totalRooms;

    public HotelRoomSearch() {
        rooms = new ArrayList<HotelRoom>();
        hotelName = "";
        hotelLocation = "";
        page = 1;
    }
    
    public List<HotelRoom> getRooms() {
        return rooms;
    }

    // TODO: keep in mind to always call fireTableDataChanged() after this
    public void setRooms(List<HotelRoom> rooms) {
        this.rooms = rooms;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public HotelRoom getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(HotelRoom selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
}
