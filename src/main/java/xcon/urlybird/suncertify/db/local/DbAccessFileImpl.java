package xcon.urlybird.suncertify.db.local;

import java.io.IOException;
import java.util.List;
import xcon.urlybird.suncertify.db.DBAccess;
import xcon.urlybird.suncertify.db.DuplicateKeyException;
import xcon.urlybird.suncertify.db.RecordNotFoundException;
import xcon.urlybird.suncertify.db.SecurityException;
import xcon.urlybird.suncertify.model.HotelRoom;


/*
 * Dit moet de DBAccess implementatie worden die de file leest en schrijft
 */
public class DbAccessFileImpl implements DBAccess {

	HotelRoomFileAcces database  = null ;
	private final static  String ROOMS_DATABASE =  "urlyBird_bd.db" ;
    
	public DbAccessFileImpl() throws IOException
	{
		this(System.getProperty("user.dir"));
	}
	
	
	
	public DbAccessFileImpl(String path) throws IOException {
		database = new HotelRoomFileAcces(path + "/"+ ROOMS_DATABASE);
	}



	private String[][] data = new String[][] {
      
            new String[]{"1", "Loudiyi Inn", "123"},
            new String[]{"2", "Koster del Sol", "200"}
    };
	
	
	
    
    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
    // TODO Auto-generated method stub

    }

    @Override
    public long[] findByCriteria(String[] criteria) {
        // for example
        long[] ids = new long[data.length];
        int i = 0;
        for (String[] record : data) {
            ids[i++] = Long.parseLong(record[0]);
        }
        return ids;
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {
    // TODO Auto-generated method stub

    }

    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
    	System.out.println("updating the record");
    	
    }
    
    public List<HotelRoom> readRecords () throws IOException
    {
    	return database.getHotelRooms();
    }

}
