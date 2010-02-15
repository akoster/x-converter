package xcon.hotel.model;

import java.io.Serializable;
import java.util.logging.Logger;

import xcon.hotel.HotelApplication;

/**
 * @author loudiyimo
 */
public class HotelRoom implements Serializable {

	private static final long serialVersionUID = 5165L;

	private static final transient Logger log = Logger
			.getLogger(HotelApplication.HOTEL_APPLICATION);

	// XXX: these constants are specific to the file implementation of the
	// database, so do not belong in the domein model
	static final int IS_VALID_OR_DELETED_RECORD_LENGTH = 2;

	static final int NAME_LENGHT = 64;

	static final int LOCATION_LENGHT = 64;

	static final int SIZE_LENGHT = 4;

	static final int IS_SMOKING_ALLOWED_LENGHT = 1;

	static final int RATE_LENGHT = 8;

	static final int DATE_LENGHT = 10;

	static final int OWNER_LENGHT = 8;

	public static int getIsValidOrDeletedRecordLength() {
		return IS_VALID_OR_DELETED_RECORD_LENGTH;
	}

	public String getIsSmokingAllowed() {
		return isSmokingAllowed;
	}

	public void setIsSmokingAllowed(String isSmokingAllowed) {
		this.isSmokingAllowed = isSmokingAllowed;
	}

	public static Logger getLog() {
		return log;
	}

	public static int getNameLenght() {
		return NAME_LENGHT;
	}

	public static int getLocationLenght() {
		return LOCATION_LENGHT;
	}

	public static int getSizeLenght() {
		return SIZE_LENGHT;
	}

	public static int getIsSmokingAllowedLenght() {
		return IS_SMOKING_ALLOWED_LENGHT;
	}

	public static int getRateLenght() {
		return RATE_LENGHT;
	}

	public static int getDateLenght() {
		return DATE_LENGHT;
	}

	public static int getOwnerLenght() {
		return OWNER_LENGHT;
	}

	/**
	 * The size of a complete record in the database
	 */
	private static final int RECORD_LENGTH = IS_VALID_OR_DELETED_RECORD_LENGTH
			+ NAME_LENGHT + LOCATION_LENGHT +

			SIZE_LENGHT + IS_SMOKING_ALLOWED_LENGHT + RATE_LENGHT + DATE_LENGHT

			+ OWNER_LENGHT;

	private String id;

	public String getIsValidOrDeletedRecord() {
		return isInvalidOrDeletedRecord;
	}

	private String isInvalidOrDeletedRecord;
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
	private String size;

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
	private String maxOccupants;

	/**
	 * Creates an instance of this object with default values.
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
		this.id = fields[0];
		this.isInvalidOrDeletedRecord = fields[1];
		this.name = fields[2];
		this.location = fields[3];
		this.maxOccupants = fields[4];
		this.isSmokingAllowed = fields[5];
		this.rate = fields[6];
		this.date = fields[7];
		this.owner = fields[8];
	}

	/**
	 * Creates an instance of this object with a specified list of initial
	 * values. Assumes initial number of copies is 1.
	 * 
	 * @param name
	 *            Holds
	 * @param location
	 *            Holds
	 * @param size
	 *            Holds .
	 * @param smoking
	 *            Holds .
	 * @param rate
	 *            Holds
	 * @param date
	 *            Holds The date available of the HotelName (yyyy-mm-dd).
	 * @param owner
	 *            Holds
	 */
	public HotelRoom(String id, String isValidOrDeletedRecord, String name,
			String location, String maxOccupants, String smoking, String rate,
			String date, String owner) {

		this.id = id;
		this.isInvalidOrDeletedRecord = isValidOrDeletedRecord;
		this.name = name;
		this.location = location;
		this.maxOccupants = maxOccupants;
		this.isSmokingAllowed = smoking;
		this.rate = rate;
		this.date = date;
		this.owner = owner;
	}

	public String toString() {

		String retVal = "[" + this.name + "; " + this.location + "; "
				+ this.maxOccupants + "; " + this.isSmokingAllowed + "; "
				+ this.rate + "; " + this.date + "; " + this.owner + "]";

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

	public String getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(String maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	public String[] convertToArray() {
		return new String[] { this.id, this.isInvalidOrDeletedRecord,
				this.name, this.location, this.size, this.isSmokingAllowed,
				this.rate, this.date, this.owner

		};
	}

	public static int getRecordLength() {
		return RECORD_LENGTH;
	}

}
