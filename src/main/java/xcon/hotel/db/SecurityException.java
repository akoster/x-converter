package xcon.hotel.db;


public class SecurityException extends Exception  {

	private static final long serialVersionUID = 1L;

	public SecurityException() {
	}

	public SecurityException(String description) {
		super(description);
	}

	public SecurityException(String description, Throwable e) {
		super(description, e);
	}

}
