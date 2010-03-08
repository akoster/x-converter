package xcon.hotel.db.local;

public interface DataFileFormatConstants {

        
        static final int IS_VALID_OR_DELETED_RECORD_LENGTH = 1;
        
        static final int NAME_LENGHT = 64;

        static final int LOCATION_LENGHT = 64;

        static final int SIZE_LENGHT = 4;

        static final int IS_SMOKING_ALLOWED_LENGHT = 1;

        static final int RATE_LENGHT = 8;

        static final int DATE_LENGHT = 10;

        static final int OWNER_LENGHT = 8;



        /**
         * The size of a complete record in the database
         */
         static final int RECORD_LENGTH = IS_VALID_OR_DELETED_RECORD_LENGTH +
             NAME_LENGHT + LOCATION_LENGHT +

            SIZE_LENGHT + IS_SMOKING_ALLOWED_LENGHT + RATE_LENGHT + DATE_LENGHT

            + OWNER_LENGHT;



}
