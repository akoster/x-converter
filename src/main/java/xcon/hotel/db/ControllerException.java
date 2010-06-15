package xcon.hotel.db;

/**
 * Exception indicating that the controller action has failed.
 * @author Mohamed
 */
public class ControllerException extends Exception {

    private static final long serialVersionUID = 1L;
    private String messageKey;

    public ControllerException() {
        super();
    }

    public ControllerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Extra constructor with reversed arguments to allow passing a message key.
     * @param arg1
     * @param key
     */
    public ControllerException(Throwable arg1, String key) {
        super(arg1);
        messageKey = key;
    }

    public ControllerException(String arg0) {
        super(arg0);
    }

    public ControllerException(Throwable arg0) {
        super(arg0);
    }

    public String getMessageKey() {
        return messageKey;
    }

}
