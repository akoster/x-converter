package xcon.hotel.db.local;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.model.HotelRoom;

/*
 * Dit moet de DBAccess implementatie worden die de file leest en schrijft
 */
public class DbAccessFileImpl implements DBAccess {

    // HotelRoomFileAcces database = null ;

    private final static String DATABASE_NAME = "urlyBird_bd.db";
    private final static String DATABASE_DIRECTORY_NAME = "database";

    static final int DATABASE_OFFSET_LENGHT = 80;

    private static RandomAccessFile database = null;

    Map<Long, HotelRoom> hotelRooms = new HashMap<Long, HotelRoom>();

    public DbAccessFileImpl() {
        this(System.getProperty("user.dir") + File.separator
            + DATABASE_DIRECTORY_NAME);
    }

    public DbAccessFileImpl(String path) {

        System.out.println("databasePath" + path);

        // database is used for the first time
        if (database == null) {
            try {
                database =
                    new RandomAccessFile(path + File.separator + DATABASE_NAME,
                        "rw");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * get all the hotelRooms from the database file. The size of the record is
     * known, because we know the record size we can the determine the id of
     * each hotelRoom.
     * @throws IOException
     */

    private HotelRoom retrieveHotelRoom(long locationInFile, long id)
            throws IOException
    {

        final byte[] input = new byte[DatabaseData.RECORD_LENGTH];
        synchronized (database) {
            database.seek(locationInFile);
            database.readFully(input);
        }

        class RecordFieldReader {

            /** field to track the position within the byte array */
            private int offset = 0;

            /**
             * converts the required number of bytes into a String.
             * @param length the length to be converted from current offset.
             * @return the converted String
             * @throws UnsupportedEncodingException if "UTF-8" not known.
             */
            String read(int length) throws UnsupportedEncodingException {
                String str = new String(input, offset, length, "UTF-8");
                offset += length;
                return str.trim();
            }
        }

        RecordFieldReader readRecord = new RecordFieldReader();
        String isValidOrDeletedRecord =
            readRecord.read(DatabaseData.IS_VALID_OR_DELETED_RECORD_LENGTH);
        String name = readRecord.read(DatabaseData.NAME_LENGHT);
        String location = readRecord.read(DatabaseData.LOCATION_LENGHT);
        String size = readRecord.read(DatabaseData.SIZE_LENGHT);
        String isSmokingAllowed =
            readRecord.read(DatabaseData.IS_SMOKING_ALLOWED_LENGHT);
        String rate = readRecord.read(DatabaseData.RATE_LENGHT);
        String date = readRecord.read(DatabaseData.DATE_LENGHT);
        String owner = readRecord.read(DatabaseData.OWNER_LENGHT);
        /*HotelRoom returnValue =
            new HotelRoom(String.valueOf(id), isValidOrDeletedRecord, name,
                location, size, isSmokingAllowed, rate, date, owner);
                              
*/
        HotelRoom returnValue =
            new HotelRoom(String.valueOf(id), name,
                location, size, isSmokingAllowed, rate, date, owner);
        return returnValue;

    }

    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {
        return 0;
    }

    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

    }

    @Override
    public long[] findByCriteria(String[] criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria may not be null");
        }

        List<Long> results = new ArrayList<Long>();
        int locationInFile = 0;
        int id = 0;
        try {
            for (; locationInFile < database.length(); locationInFile +=
                DatabaseData.RECORD_LENGTH)
            {

                HotelRoom hotelRoom = retrieveHotelRoom(locationInFile, id);
                // System.out.println("hotelRoom" + hotelRoom.toString());

                if (findMatch(criteria, hotelRoom)) {
                    results.add(Long.valueOf(hotelRoom.getId()));
                }
                id++;
            }
        }
        // XXX Ik mag van mijn iterfaces deze exceptie niet gooien

        catch (EOFException e) {
            System.out.println("EOF has been reached");
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return copyValues(results);
    }

    private boolean findMatch(String[] criteria, HotelRoom room) {

        boolean isMatch = true;

        String hotelName = room.getName();
        String location = room.getLocation();
        String tempArray[] = {
                hotelName, location
        };

        for (int c = 0; c < criteria.length; c++) {

            if (criteria[c] == null || criteria[c].length() == 0) {
                continue;
            }

            String field = tempArray[c].toLowerCase();
            String criterium = criteria[c].toLowerCase();
            if (!field.startsWith(criterium)) {
                isMatch = false;
                break;

            }

        }
        return isMatch;

    }

    private long[] copyValues(List<Long> results) {

        // copy values from results list to rowsFound array
        // init rowsFound arrays with results.size()
        long[] returnValues = new long[results.size()];
        for (int i = 0; i < results.size(); i++) {
            returnValues[i] = results.get(i);
        }
        return returnValues;
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {
        return 0;
    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        long locationInFile = recNo * DatabaseData.RECORD_LENGTH;
        HotelRoom room = null;
        try {
            room = retrieveHotelRoom(locationInFile, recNo);
            if (room == null) {
                throw new RecordNotFoundException("record does not exist");
            }

        }

 ///       XXX Ik mag van mijn interface geen IOexcetion gooien 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return room.convertToArray();
    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

    }

    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
        System.out.println("updating the record");

    }

}
