package xcon.hotel.db;

public class DuplicateKeyException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateKeyException() {
		// TODO Auto-generated constructor stub
	}

	public DuplicateKeyException(String description) {
		super(description);
	}

}
