package xcon.hotel.db;

public class RecordAlreadyExistException  extends Exception{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistException(String description)
    {
        super(description);
    }

}
