package xcon.urlybird.suncertify.db.local;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import xcon.urlybird.suncertify.model.HotelRoom;

public class HotelRoomFileAcces {

	/**
	 * The physical file on disk containing our hotel records.
	 */
	private RandomAccessFile database = null;

	/**
	 * The location where the database is stroed
	 */
	private String dbPath = "";

	/**
	 * Logger created for HotelRoomFileAcces
	 */
	private Logger logger = Logger
			.getLogger(HotelRoomFileAcces.class.getName());

	/**
	 * A map containing all the hote records
	 */

	private Map<Integer, Long> records = new HashMap<Integer, Long>();

	/**
	 * 
	 * @param suppliedPath
	 * @throws IOException 
	 */
	public HotelRoomFileAcces(String suppliedPath) throws IOException {
		if (database == null) {
			database = new RandomAccessFile(suppliedPath, "rw");
			dbPath = suppliedPath;
			getHotelRooms();

		} else {
			if (dbPath != suppliedPath) {
				logger.info("wrong path \n " + suppliedPath
						+ " will be ignored");
			}
		}
	}

	public  List<HotelRoom > getHotelRooms() throws IOException {
		
		int recordId = 0 ;
		List<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
		for (long location = 0; location < database.length(); location = location
				+ Fields.RECORD_LENGTH) {
			HotelRoom hotelRoom = retrieveHotelRoom(location);
			recordId++ ;
			if (hotelRoom == null)
			{
				logger.fine("ignoring. This is a deleted record");
			}
			else 
			{
				records.put(recordId, location);
				hotelRooms.add(hotelRoom);
				
			}
		}
		return hotelRooms ;
	}

	private HotelRoom retrieveHotelRoom(long dataBaseLocation) throws IOException {
		
		final byte [] input = new byte[Fields.RECORD_LENGTH];
		database.seek(dataBaseLocation);
		
		database.readFully(input);
		
		
		class RecordFieldReader 
		{
			int offset = 0 ;
			public String read(int length) throws UnsupportedEncodingException
			{
				String str =  new String(input,offset,length,"UTF-8");
				System.out.println(" str "+ str);
				str.trim();
				return str ;
			}
		}
		
		RecordFieldReader recordFieldReader = new RecordFieldReader();
		String name = recordFieldReader.read(Fields.NAME_LENGHT);
		String location  = recordFieldReader.read(Fields.LOCATION_LENGTH);
		String size = recordFieldReader.read(Fields.SIZE_LENGTH);
		String smoking = recordFieldReader.read(Fields.SMOKING_LENGHT);
		String rate = recordFieldReader.read(Fields.RATE_LENGTH);
		String date = recordFieldReader.read(Fields.DATE_LENGHT);
		String owner = recordFieldReader.read(Fields.OWNER_LENGTH);
		
		String id = "id" ;
		HotelRoom room = new HotelRoom(id, name,location,size,smoking,rate,date,owner); 
		
		return room;
	}

}
