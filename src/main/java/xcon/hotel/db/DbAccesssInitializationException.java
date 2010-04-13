package xcon.hotel.db;

/**
 * Exception indicating that the DbAccess could not be initialized
 * @author Mohamed
 */
public class DbAccesssInitializationException extends Exception {

    private static final long serialVersionUID = 1L;

    public DbAccesssInitializationException() {
        super();
    }

    public DbAccesssInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbAccesssInitializationException(String message) {
        super(message);
    }

    public DbAccesssInitializationException(Throwable cause) {
        super(cause);
    }

}
