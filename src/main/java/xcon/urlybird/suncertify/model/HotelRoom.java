package xcon.urlybird.suncertify.model;


/**
 * @author loudiyimo
 */
public class HotelRoom {

//    private static final transient Logger LOG =
//        Logger.getLogger(HotelRoom.class.getName());

    private String id;

    /**
     * The name of the hotel this vacancy record relates to
     */
    private String name;

    /**
     * The location of the hotel
     */
    private String location;

    /**
     * The maximum number of people permitted in this room
     */
    private String size;

    /**
     * Flag indication if smoking is permitted
     */
    private String smoking;

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
    private String maxOccupants;

    /**
     * Creates an instance of this object with default values.
     */
    public HotelRoom() {
    // TODO Auto-generated constructor stub

    }

    // TODO checkOrder
    public HotelRoom(String[] fields) {

        this.id = fields[0];
        this.name = fields[1];
        this.location = fields[2];
        this.maxOccupants = fields[3];
        this.smoking = fields[4];
        this.rate = fields[5];
        this.date = fields[6];
        this.owner = fields[7];
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
    public HotelRoom(String id,
                     String name,
                     String location,
                     String maxOccupants,
                     String smoking,
                     String rate,
                     String date,
                     String owner)
    {

        this.id = id;
        this.name = name;
        this.location = location;
        this.maxOccupants = maxOccupants;
        this.smoking = smoking;
        this.rate = rate;
        this.date = date;
        this.owner = owner;
    }

    public String toString() {

        String retVal =
            "[" + this.name + "; " + this.location + "; " + this.size + "; "
                + this.maxOccupants + "; " + this.smoking + "; " + this.rate
                + "; " + this.date + "; " + this.owner + "]";

        return retVal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
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

    public String getMaxOccupants() {
        return maxOccupants;
    }

    public void setMaxOccupants(String maxOccupants) {
        this.maxOccupants = maxOccupants;
    }

    public String[] convertToArray() {
        return new String[] {
                this.id, this.location, this.size, this.smoking, this.rate,
                this.date, this.owner, this.maxOccupants
        };
    }

}
