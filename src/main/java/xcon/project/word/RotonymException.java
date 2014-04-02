package xcon.project.word;


/**
 * Thrown when a given word is not a rotonym
 * @author loudiyimo
 */
public class RotonymException extends Exception {

	private static final long serialVersionUID = 1L;

	public RotonymException() {
        super();
    }

    public RotonymException(String message, Throwable cause) {
        super(message, cause);
    }

    public RotonymException(String message) {
        super(message);
    }

    public RotonymException(Throwable cause) {
        super(cause);
    }

}
