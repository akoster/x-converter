package xcon.urlybird.suncertify.db;

public class RecordDoesAllreadyExistException  extends Exception{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RecordDoesAllreadyExistException(String description)
    {
        super(description);
    }

}
