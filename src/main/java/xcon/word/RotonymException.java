package xcon.word;


/**
 * Thrown when a given word is not a rotonym
 * @author loudiyimo
 */
public class RotonymException extends Exception {

	private static final long serialVersionUID = 1L;

	public RotonymException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RotonymException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public RotonymException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public RotonymException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
