package xcon.hotel.db.local;

public interface DatabaseHeader {
    
	
	/**
     * 4 byte numeric , magic coockie value, Identifies this as a data file 
     */
    static final int MAGIC_COOKIE_SIZE = 4 ;
    
    /**
     * The size of the number of fields in each record.
     */
    static final int FIELDS_COUNT_SIZE = 2;
    
    static final int FIELD_NAME_LENGTH_SIZE = 1 ;
    
    
    static final int FIELD_LENGTH_SIZE = 1 ;
}
