package xcon.hotel.db.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xcon.hotel.client.DataBaseInformation;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbException;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.model.HotelRoom;

/*
 * Dit moet de DBAccess implementatie worden die de file leest en schrijft
 */
public class DbAccessFileImpl implements DBAccess {

    // HotelRoomFileAcces database = null ;
    private String[] columnNames;
    private int[] fieldLengths;
    private long dataStartOffset;

    private RandomAccessFile database;

    Map<Long, HotelRoom> hotelRooms = new HashMap<Long, HotelRoom>();

    static DataBaseInformation dataBaseInformation = new DataBaseInformation();

    private static final String ENCODING = "UTF-8";

    public DbAccessFileImpl() throws DbException {

        URL resourceUrl = this.getClass().getResource("/hotel.db");
        System.out.println("resourceUrl" + resourceUrl);

        File resourceFile;
        try {
            resourceFile = new File(resourceUrl.toURI());
        }
        catch (URISyntaxException e) {
            throw new DbException("Could parse URL " + resourceUrl, e);
        }
        try {
            database = new RandomAccessFile(resourceFile, "rw");
        }
        catch (FileNotFoundException e) {
            throw new DbException("Could not acces file " + resourceFile, e);
        }
        try {
            initDatabaseInformation();
        }
        catch (IOException e) {
            throw new DbException("Could init Database meta data", e);
        }
    }

    /**
     * get all the hotelRooms from the database file. The size of the record is
     * known, because we know the record size we can the determine the id of
     * each hotelRoom.
     * @throws IOException
     * @throws IOException
     */
    private void initDatabaseInformation() throws IOException {

        long locationInFile = 4;
        database.seek(locationInFile);

        byte[] input;
        input = new byte[2];
        database.readFully(input);
        int numberOfFieldsInDatabase = unsignedShortToInt(input);
        System.out.println("numberOfFieldsInDatabase"
            + numberOfFieldsInDatabase);
        locationInFile += input.length;

        columnNames = new String[numberOfFieldsInDatabase];
        fieldLengths = new int[numberOfFieldsInDatabase];
        for (int i = 0; i < numberOfFieldsInDatabase; i++) {

            database.seek(locationInFile);
            byte columnLengthInBytes = database.readByte();
            int columnLength = unsignedByteToInt(columnLengthInBytes);

            System.out.println("columnValue" + columnLength);
            locationInFile = locationInFile + 1;
            byte[] columnBytes = new byte[columnLength];

            database.seek(locationInFile);
            database.read(columnBytes);

            String columnName = new String(columnBytes, ENCODING);
            System.out.println("columnName:" + columnName);
            columnNames[i] = columnName;

            locationInFile = locationInFile + columnLength;
            database.seek(locationInFile);
            byte fieldLengthInBytes = database.readByte();
            int fieldLength = unsignedByteToInt(fieldLengthInBytes);
            fieldLengths[i] = fieldLength;
            System.out.println("fieldLength" + fieldLength);
            System.out.println();
            locationInFile = locationInFile + 1;
        }
        dataStartOffset = locationInFile;
    }

    /**
     * Converts a 4 byte array of unsigned bytes to an long
     * @param b an array of 4 unsigned bytes
     * @return a long representing the unsigned int
     */
    public static final long unsignedIntToLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        l <<= 8;
        l |= b[3] & 0xFF;
        return l;
    }

    /**
     * Converts a two byte array to an integer
     * @param b a byte array of length 2
     * @return an int representing the unsigned short
     */
    public static final int unsignedShortToInt(byte[] b) {
        int i = 0;
        i |= b[0] & 0xFF;
        i <<= 8;
        i |= b[1] & 0xFF;
        return i;
    }

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

    private HotelRoom retrieveHotelRoom(long id) throws IOException,
            RecordNotFoundException
    {
        long locationInFile =
            id * DataFileFormatConstants.RECORD_LENGTH + dataStartOffset;
        System.out.println("in retrieveHotelRoom locationInfile "
            + locationInFile);
        final byte[] input = new byte[DataFileFormatConstants.RECORD_LENGTH];
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
                String str = new String(input, offset, length, ENCODING);
                System.out.println("string read" + str);
                offset += length;
                return str.trim();
            }
        }

        RecordFieldReader readRecord = new RecordFieldReader();
        boolean isInvalidOrDeletedRecord =
            parseDeletedFlag(readRecord.read(DataFileFormatConstants.IS_VALID_OR_DELETED_RECORD_LENGTH));

        if (isInvalidOrDeletedRecord) {
            throw new RecordNotFoundException("Record is invalid or deleted: "
                + id);
        }
        int i = 0;
        String name = readRecord.read(fieldLengths[i++]);
        String location = readRecord.read(fieldLengths[i++]);
        int size = Integer.parseInt(readRecord.read(fieldLengths[i++]));
        String isSmokingAllowed = readRecord.read(fieldLengths[i++]);
        String rate = readRecord.read(fieldLengths[i++]);
        String date = readRecord.read(fieldLengths[i++]);
        String owner = readRecord.read(fieldLengths[i++]);
        /*
         * HotelRoom returnValue = new HotelRoom(String.valueOf(id),
         * isValidOrDeletedRecord, name, location, size, isSmokingAllowed, rate,
         * date, owner);
         */
        HotelRoom returnValue =
            new HotelRoom(id, name, location, size, isSmokingAllowed, rate,
                date, owner);
        return returnValue;
    }

    private boolean parseDeletedFlag(String read)
            throws RecordNotFoundException
    {

        System.out.println("in parseDeletedFlag read" + read + "read.length"
            + read.length());
        if (read == null || read.length() != 1) {
            throw new RecordNotFoundException("Illegal isDeleted flag read");
        }
        byte value = read.getBytes()[0];
        if (value == 0xFF) {
            return true;
        }
        if (value == 0x00) {
            return false;
        }
        throw new RecordNotFoundException("Illegal value for isDeleted flag: "
            + value);
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
        long databaseLength;
        try {
            databaseLength = database.length();

            // XXX: nicer to start id at 1
            int id = 0;

            for (int locationInFile = (int) dataStartOffset; locationInFile < databaseLength; locationInFile +=
                DataFileFormatConstants.RECORD_LENGTH)
            {
                try {

                    HotelRoom hotelRoom = retrieveHotelRoom(id);
                    System.out.println("hotelRoom" + hotelRoom.toString());

                    if (findMatch(criteria, hotelRoom)) {
                        results.add(Long.valueOf(hotelRoom.getId()));
                    }
                }
                catch (IOException e) {
                    // XXX: add logging
                    System.err.println("Could not read record from datafile");
                }
                catch (RecordNotFoundException e) {
                    System.err.println("Error parsing data" + e.getMessage());
                }
                id++;
            }
        }
        catch (IOException e1) {
            // XXX: add logging
            System.err.println("Could not determine database length");
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
    public String[] readRecord(long id) throws RecordNotFoundException {

        String[] result;
        if (id == -1) {
            result = columnNames;
        }
        else {
            HotelRoom room = null;
            try {
                room = retrieveHotelRoom(id);
                if (room == null) {
                    throw new RecordNotFoundException("record does not exist");
                }
            }
            catch (IOException e) {
                throw new RecordNotFoundException(
                    "Problem accessing data file", e);
            }
            result = room.convertToArray();
        }
        return result;
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

    public DataBaseInformation getInformationData() {
        return null;
    }

}
