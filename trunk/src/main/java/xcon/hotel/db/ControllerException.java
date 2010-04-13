package xcon.hotel.db;

/**
 * Exception indicating that the controller action has failed. 
 * @author Mohamed
 */
public class ControllerException extends Exception {

    private static final long serialVersionUID = 1L;

    public ControllerException() {
        super();
    }

    public ControllerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ControllerException(String arg0) {
        super(arg0);
    }

    public ControllerException(Throwable arg0) {
        super(arg0);
    }

}
