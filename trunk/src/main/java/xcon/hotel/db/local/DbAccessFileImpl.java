package xcon.hotel.db.local;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final static String Database_Name = "urlyBird_bd.db";

    static final int DATABASE_OFFSET_LENGHT = 80;

    private static RandomAccessFile database = null;

    Map<Long, HotelRoom> hotelRooms = new HashMap<Long, HotelRoom>();

    public DbAccessFileImpl() throws IOException {
        this(System.getProperty("user.dir"));
    }

    public DbAccessFileImpl(String path) {

        String databasePath = path + "\\" + Database_Name;
        System.out.println("databasePath" + databasePath);

        // database is used for the first time
        if (database == null) {
            try {
                database = new RandomAccessFile(databasePath, "rw");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                getHotelRoomList();
            }

            catch (EOFException e) {
                System.out.println("end of file has been reached");
            }
            catch (IOException e) {

            }
            printHotelRooms();
        }

    }

    private void printHotelRooms() {

        for (Map.Entry<Long, HotelRoom> entry : hotelRooms.entrySet()) {
            long key = entry.getKey();
            HotelRoom value = entry.getValue();
        }

    }

    /**
     * get all the hotelRooms from the database file. The size of the record is
     * known, because we know the record size we can the determine the id of
     * each hotelRoom.
     * @throws IOException
     */
    private void getHotelRoomList() throws IOException {

        long id = 0;

        // XXX I am going to use an offset (database_offset_lenght)of 15 for the
        // moment.

        long locationInFile = DATABASE_OFFSET_LENGHT;
        for (; locationInFile < database.length(); locationInFile +=
            HotelRoom.getRecordLength())
        {

            HotelRoom hotelRoom = retrieveHotelRoom(locationInFile, id);


            hotelRooms.put(id, hotelRoom);
            id++;
        }

    }

    private HotelRoom retrieveHotelRoom(long locationInFile, long id)
            throws IOException
    {

        final byte[] input = new byte[HotelRoom.getRecordLength()];
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
            readRecord.read(HotelRoom.getIsValidOrDeletedRecordLength());
        String name = readRecord.read(HotelRoom.getNameLenght());
        String location = readRecord.read(HotelRoom.getLocationLenght());
        String size = readRecord.read(HotelRoom.getSizeLenght());
        String isSmokingAllowed =
            readRecord.read(HotelRoom.getIsSmokingAllowedLenght());
        String rate = readRecord.read(HotelRoom.getRateLenght());
        String date = readRecord.read(HotelRoom.getDateLenght());
        String owner = readRecord.read(HotelRoom.getOwnerLenght());
        HotelRoom returnValue =
            new HotelRoom(String.valueOf(id), isValidOrDeletedRecord, name,
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

        for (String string : criteria) {
        }

        List<Long> results = new ArrayList<Long>();
        Iterator<Map.Entry<Long, HotelRoom>> it =
            hotelRooms.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry<Long, HotelRoom> pair = it.next();
            HotelRoom room = (HotelRoom) pair.getValue();

            if (findMatch(criteria, room)) {
                results.add((Long) pair.getKey());
            }
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
        HotelRoom room = hotelRooms.get(recNo);
        if (room == null) {
            throw new RecordNotFoundException("record does not exist");
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
