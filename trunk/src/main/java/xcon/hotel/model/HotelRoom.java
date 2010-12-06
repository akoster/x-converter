package xcon.hotel.model;

import java.io.Serializable;
import java.util.logging.Logger;
import xcon.hotel.db.RecordNotFoundException;

/**
 * @author loudiyimo
 */
public class HotelRoom implements Serializable {

    private static final long serialVersionUID = 5165L;

    
    private Logger logger = Logger.getLogger(HotelRoom.class.getName());

    private long id;

    /**
     * The name of the hotel
     */

    private String name;

    /**
     * The location of the hotel
     */
    private String location;

    /**
     * The maximum number of people permitted in this room
     */
    private int size;

    /**
     * Flag indication if smoking is permitted
     */
    private String isSmokingAllowed;

    /**
     * Charge per night for the room.
     */
    private String rate;

    /**
     * The date which is available, the single night to which this record
     * relates
     */
    private String date;

    /**
     * The customer holding this Record
     */
    private Long owner;

    public HotelRoom(long recNo, String[] fields) {

        this.id = recNo;
        this.name = fields[0];
        this.location = fields[1];
        this.size = Integer.valueOf(fields[2]);
        this.isSmokingAllowed = fields[3];
        this.rate = fields[4];
        this.date = fields[5];
        try {
            this.owner = Long.valueOf(fields[6]);
        }
        catch (NumberFormatException e) {
            this.owner = null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSmoking() {
        return isSmokingAllowed;
    }

    public void setSmoking(String smoking) {
        this.isSmokingAllowed = smoking;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String[] convertToArray() {
        return new String[] {
                this.name, this.location, String.valueOf(this.size),
                this.isSmokingAllowed, this.rate, this.date,
                String.valueOf(owner)
        };
    }

    public String toString() {

        String retVal =
            "[" + this.name + "; " + this.location + "; " + this.size + "; "
                + this.isSmokingAllowed + "; " + this.rate + "; " + this.date
                + "; " + this.owner + "]";

        return retVal;
    }

}
