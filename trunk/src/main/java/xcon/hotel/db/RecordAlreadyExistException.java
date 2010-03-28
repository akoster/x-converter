package xcon.hotel.db;

public class RecordAlreadyExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistException() {
        super();
    }

    public RecordAlreadyExistException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RecordAlreadyExistException(String arg0) {
        super(arg0);
    }

    public RecordAlreadyExistException(Throwable arg0) {
        super(arg0);
    }

}
