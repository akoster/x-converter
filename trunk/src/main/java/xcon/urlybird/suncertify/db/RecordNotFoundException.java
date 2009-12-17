package xcon.urlybird.suncertify.db;

public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException() {
	}
	
	public RecordNotFoundException(String description)
	{
		super(description);
	}

}
