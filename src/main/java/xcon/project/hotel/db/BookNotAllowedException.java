package xcon.project.hotel.db;

public class BookNotAllowedException extends Exception {
	private static final long serialVersionUID = 1L;
	public BookNotAllowedException() {
	}
	public BookNotAllowedException(String message)
	{
		super(message);
	}
}
