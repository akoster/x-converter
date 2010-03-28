package xcon.hotel.model;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import xcon.hotel.HotelApplication;

/**
 * @author loudiyimo
 */
public class HotelRoom implements Serializable {

    private static final long serialVersionUID = 5165L;


    private Logger logger = Logger.getLogger("hotel-application");

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
    private String owner;

    /**
     * 
     */

    public HotelRoom() {
    // TODO Auto-generated constructor stub

    }

    /*
     * public HotelRoom(String[] fields) { this.id = fields[0]; this.name =
     * fields[1]; this.location = fields[2]; this.maxOccupants = fields[3];
     * this.isSmokingAllowed = fields[4]; this.rate = fields[5]; this.date =
     * fields[6]; this.owner = fields[7]; }
     */

    public HotelRoom(long recNo, String[] fields) {

        // this.id = String.valueOf(recNo);

        this.id = recNo;
        this.name = fields[1];
        this.location = fields[2];
        this.size = Integer.valueOf(fields[3]);
        this.isSmokingAllowed = fields[4];
        this.rate = fields[5];
        this.date = fields[6];
        this.owner = (fields[7]);
    }

    /**
     * Creates an instance of this object with a specified list of initial
     * values. Assumes initial number of copies is 1.
     * @param name Holds
     * @param location Holds
     * @param size Holds .
     * @param smoking Holds .
     * @param rate Holds
     * @param date Holds The date available of the HotelName (yyyy-mm-dd).
     * @param owner Holds
     */
    public HotelRoom(long id,
                     String name,
                     String location,
                     int size,
                     String smoking,
                     String rate,
                     String date,
                     String owner)
    {

        this.id = id;
        this.name = name;
        this.location = location;
        this.size = size;
        this.isSmokingAllowed = smoking;
        this.rate = rate;
        this.date = date;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] convertToArray() {
        return new String[] {
                String.valueOf(this.id), this.name, this.location,
                String.valueOf(this.size), this.isSmokingAllowed, this.rate,
                this.date, owner

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
