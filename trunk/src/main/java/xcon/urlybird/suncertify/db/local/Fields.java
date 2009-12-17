package xcon.urlybird.suncertify.db.local;


public interface Fields {

    /**
     * The length of the name of the hotel this vacancy record relates to
     */
    public static final int NAME_LENGHT = 64;
    /**
     * The length of location for this hotel
     */
    
    public static final int LOCATION_LENGTH = 64;
    /**
     * The length of the maximum number of people permitted in this room
     */
    public static final int SIZE_LENGTH = 4;
    /**
     * The length of the field Smoking flag indicating if smoking is permitted
     */
    public static final int SMOKING_LENGHT = 1;
    /**
     * the length of field rate
     */
    public static final int RATE_LENGTH = 8;
    /**
     * The lenght of field date
     */
    public static final int DATE_LENGHT = 10;
    /**
     * The length of Customer holding this record
     */
    public static final int OWNER_LENGTH = 8;
    /**
         * The size of a complete record in the database. Calculated by adding all
         * the previous fields together. Knowing this makes it easy to work with an
         * entire block of data at a time (rather than reading individual fields),
         * reducing the time we need to block on database access.
         */
       public static final int RECORD_LENGTH =
            NAME_LENGHT + LOCATION_LENGTH + SIZE_LENGTH + SMOKING_LENGHT
                + RATE_LENGTH + DATE_LENGHT + OWNER_LENGTH;

}
