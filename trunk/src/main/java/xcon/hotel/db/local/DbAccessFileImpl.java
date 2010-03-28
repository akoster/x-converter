package xcon.hotel.db.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
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

    private static Logger logger = Logger.getLogger("hotel-application");

    private static final String ENCODING = "UTF-8";
    private static final int VALID_OR_DELETED_FLAG_LENGTH = 1;
    private static final int ONE_BYTE = 1;

    private String[] columnNames;
    private int[] fieldLengths;
    private long dataStartOffset;
    private int record_length;
    private  RandomAccessFile database;

    public DbAccessFileImpl() throws DbException {

        URL resourceUrl = this.getClass().getResource("/hotel.db");
        logger.info("resourceUrl" + resourceUrl);

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
            initDbMetaData();
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
    private void initDbMetaData() throws IOException {

        long locationInFile = 4;
        database.seek(locationInFile);

        byte[] input;
        input = new byte[2];
        database.readFully(input);
        int numberOfFieldsInDatabase = unsignedShortToInt(input);
        logger.info("numberOfFieldsInDatabase" + numberOfFieldsInDatabase);
        locationInFile += input.length;

        columnNames = new String[numberOfFieldsInDatabase];
        fieldLengths = new int[numberOfFieldsInDatabase];
        for (int i = 0; i < numberOfFieldsInDatabase; i++) {

            database.seek(locationInFile);
            byte columnLengthInBytes = database.readByte();
            int columnLength = unsignedByteToInt(columnLengthInBytes);

            logger.info("columnLength" + columnLength);

            locationInFile = locationInFile + ONE_BYTE;
            byte[] columnBytes = new byte[columnLength];

            database.seek(locationInFile);
            database.read(columnBytes);

            String columnName = new String(columnBytes, ENCODING);
            logger.info("columnName" + i + " : " + columnName);
            columnNames[i] = columnName;

            locationInFile = locationInFile + columnLength;
            database.seek(locationInFile);
            byte fieldLengthInBytes = database.readByte();
            int fieldLength = unsignedByteToInt(fieldLengthInBytes);
            fieldLengths[i] = fieldLength;
            logger.info("fieldLength" + fieldLength);
            locationInFile = locationInFile + ONE_BYTE;
        }

        dataStartOffset = locationInFile;

        for (int i = 0; i < fieldLengths.length; i++) {
            record_length += fieldLengths[i];
        }
        record_length += VALID_OR_DELETED_FLAG_LENGTH;
        System.out.println("record length :" + record_length);
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
        long locationInFile = id * record_length + dataStartOffset;
        final byte[] input = new byte[record_length];
        synchronized (database) {
            database.seek(locationInFile);
            database.readFully(input);
            logger.info("input:" + new String(input));
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
                offset += length;
                return str;
            }
        }

        RecordFieldReader readRecord = new RecordFieldReader();
        String validOrDeleted = readRecord.read(1);
        boolean isInvalidOrDeletedRecord = parseDeletedFlag(validOrDeleted);

        if (isInvalidOrDeletedRecord) {
            throw new RecordNotFoundException("Record is invalid or deleted: "
                + id);
        }
        int i = 0;
        String name = readRecord.read(fieldLengths[i++]).trim();
        String location = readRecord.read(fieldLengths[i++]).trim();
        int size = Integer.parseInt(readRecord.read(fieldLengths[i++]).trim());
        String isSmokingAllowed = readRecord.read(fieldLengths[i++]).trim();
        String rate = readRecord.read(fieldLengths[i++]).trim();
        String date = readRecord.read(fieldLengths[i++]).trim();
        String owner = readRecord.read(fieldLengths[i++]).trim();
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

        if (read == null || read.length() != 1) {
            throw new RecordNotFoundException("Illegal isDeleted flag read");
        }
        byte value = read.getBytes()[0];
        if (value == 0xFF) {
            logger.info("flag value is 0xFF, record is deleted");
            return true;
        }
        if (value == 0x00) {
            logger.info("flag value is 0x00, record is valid");
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
                record_length)
            {
                try {

                    HotelRoom hotelRoom = retrieveHotelRoom(id);
                    logger.info("hotelRoom" + hotelRoom.toString());

                    if (findMatch(criteria, hotelRoom)) {
                        results.add(Long.valueOf(hotelRoom.getId()));
                    }
                }
                catch (IOException e) {
                    // XXX: add logging
                    logger.warning("Could not read record from datafile");
                }
                catch (RecordNotFoundException e) {
                    logger.warning("Error parsing data" + e.getMessage());
                }
                id++;
            }
        }
        catch (IOException e1) {
            // XXX: add logging
            logger.warning("Could not determine database length");
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
        logger.warning("updating the record with data" + Arrays.asList(data));
        String emptyRecordString = new String(new byte[record_length]);
        final StringBuilder out = new StringBuilder(emptyRecordString);

        /** assists in converting Strings to a byte[] */
        class RecordFieldWriter {

            /** current position in byte[] */
            private int currentPosition = 0;

            /**
             * converts a String of specified length to byte[]
             * @param data the String to be converted into part of the byte[].
             * @param length the maximum size of the String
             */
            void write(String data, int length) {
                out.replace(
                        currentPosition,
                        currentPosition + data.length(),
                        data);
                currentPosition += length;
            }
        }
        RecordFieldWriter writeRecord = new RecordFieldWriter();

        // when you are in the update methode, it means that the record is
        // valid.
        byte deletedOrInvalidRecord = 0x00;

        // i is the index of the data
        // j is the index of the fieldlenth;
        int i = 1;
        int j = 0;
        writeRecord.write(Byte.toString(deletedOrInvalidRecord), 1);
        String hotelName = data[i];

        logger.warning("hotelName:" + hotelName + " fieldLengths[i]:"
            + fieldLengths[j]);
        writeRecord.write(hotelName, fieldLengths[j++]);
        i++;

        String hotelLocation = data[i];
        logger.warning("hotelLocation:" + hotelLocation + " fieldLengths[i]:"
            + fieldLengths[j]);
        writeRecord.write(hotelLocation, fieldLengths[j++]);
        i++;

        String size = data[i];
        logger.warning("size:" + size + " fieldLengths[i]:" + fieldLengths[j]);
        writeRecord.write(size, fieldLengths[j++]);
        i++;

        String isSmokingAllowed = data[i];
        logger.warning("isSmokingAllowed:" + isSmokingAllowed
            + " fieldLengths[i]:" + fieldLengths[j]);
        writeRecord.write(isSmokingAllowed, fieldLengths[j++]);
        i++;

        String rate = data[i];
        logger.warning("rate:" + rate + "fieldLengths[i]:" + fieldLengths[j]);
        writeRecord.write(rate, fieldLengths[j++]);
        i++;

        String date = data[i];
        logger.warning("date:" + date + "fieldLengths[i]:" + fieldLengths[j]);
        writeRecord.write(date, fieldLengths[j++]);
        i++;

        String owner = data[i];
        logger.warning("owner:" + owner + "fieldLengths[i]:" + fieldLengths[j]);
        writeRecord.write(owner, fieldLengths[j++]);
        i++;

        // now that we have everything ready to go, we can go into our
        // synchronized block & perform our operations as quickly as possible
        // ensuring that we block other users for as little time as possible.

        synchronized (database) {
            int offset =
                (int) (Integer.parseInt(data[0]) * record_length + dataStartOffset);
            logger.warning("offset:" + offset);
            try {
                
                System.out.println("database" + database);
                database.seek(offset);
                logger.warning("out.toString()" + out.toString());
                database.write(out.toString().getBytes());
                //database.close();

            }
            catch (IOException e) {
                logger.warning("error occured while writing data to the database file");
            }
        }

    }
}
