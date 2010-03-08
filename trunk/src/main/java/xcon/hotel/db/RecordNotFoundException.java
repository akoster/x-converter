package xcon.hotel.db;

public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
    }
    
    public RecordNotFoundException(String description)
    {
        super(description);
    }

    public RecordNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RecordNotFoundException(Throwable arg0) {
        super(arg0);
    }

}
