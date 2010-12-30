package xcon.hotel.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import xcon.hotel.model.HotelRoom;

/*
 * Dit moet de DBAccess implementatie worden die de file leest en schrijft
 */

/**
 * This class is reads from the database and writes to it 
 */
public class Data implements DBAccess {

    private Logger logger = Logger.getLogger(Data.class.getName());

    private static final String ENCODING = "UTF-8";
    private static final int VALID_OR_DELETED_FLAG_LENGTH = 1;
    private static final int ONE_BYTE = 1;

    private String[] columnNames;
    private int[] fieldLengths;
    private long dataStartOffset;
    private int record_length;
    private RandomAccessFile database;
    private Map<Long, Long> locks;

    // XXX I have chosen for 1000 as a start value and the increment it by one
    // to get the magiccookie
    private long magicCookie = 1000;

    public Data(File hotelDBFile) throws DbAccesssInitializationException {

        locks = new HashMap<Long, Long>();
        if (hotelDBFile == null) {
            throw new DbAccesssInitializationException("File cannot be null");
        }
        try {
            database = new RandomAccessFile(hotelDBFile, "rw");
        }
        catch (FileNotFoundException e) {
            throw new DbAccesssInitializationException("Could not access file "
                + hotelDBFile, e);
        }
        try {
            initDbMetaData();
        }
        catch (IOException e) {
            throw new DbAccesssInitializationException(
                "Could init Database meta data", e);
        }

    }

    /**
     * get all the hotelRooms from the database file. The size of the record is
     * known, because we know the record size we can the determine the id of
     * each hotelRoom.
     * @throws IOException
     */
    private void initDbMetaData() throws IOException {

        long locationInFile = 4;
        database.seek(locationInFile);
        byte[] input;
        input = new byte[2];
        database.readFully(input);
        int numberOfFieldsInDatabase = unsignedShortToInt(input);
        logger.fine("numberOfFieldsInDatabase" + numberOfFieldsInDatabase);
        locationInFile += input.length;

        columnNames = new String[numberOfFieldsInDatabase];
        fieldLengths = new int[numberOfFieldsInDatabase];
        for (int i = 0; i < numberOfFieldsInDatabase; i++) {

            database.seek(locationInFile);
            byte columnLengthInBytes = database.readByte();
            int columnLength = unsignedByteToInt(columnLengthInBytes);

            logger.fine("columnLength" + columnLength);

            locationInFile = locationInFile + ONE_BYTE;
            byte[] columnBytes = new byte[columnLength];

            database.seek(locationInFile);
            database.read(columnBytes);

            String columnName = new String(columnBytes, ENCODING);
            logger.fine("columnName" + i + " : " + columnName);
            columnNames[i] = columnName;

            locationInFile = locationInFile + columnLength;
            database.seek(locationInFile);
            byte fieldLengthInBytes = database.readByte();
            int fieldLength = unsignedByteToInt(fieldLengthInBytes);
            fieldLengths[i] = fieldLength;
            logger.fine("fieldLength" + fieldLength);
            locationInFile = locationInFile + ONE_BYTE;
        }

        dataStartOffset = locationInFile;

        for (int i = 0; i < fieldLengths.length; i++) {
            record_length += fieldLengths[i];
        }
        record_length += VALID_OR_DELETED_FLAG_LENGTH;
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

    private synchronized HotelRoom retrieveHotelRoom(long recNo)
            throws IOException, RecordNotFoundException
    {
        long locationInFile = recNo * record_length + dataStartOffset;
        final byte[] input = new byte[record_length];
        synchronized (database) {
            database.seek(locationInFile);
            database.readFully(input);
            logger.fine("input:" + new String(input));
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
        String invalidOrDeleted = readRecord.read(1);
        boolean isInvalidOrDeletedRecord = parseDeletedFlag(invalidOrDeleted);

        if (isInvalidOrDeletedRecord) {
            throw new RecordNotFoundException("Record is invalid or deleted: "
                + recNo);
        }

        int i = 0;
        String[] roomFields = new String[7];
        // hotelname
        roomFields[0] = readRecord.read(fieldLengths[i++]).trim();
        // location
        roomFields[1] = readRecord.read(fieldLengths[i++]).trim();
        // size
        roomFields[2] = readRecord.read(fieldLengths[i++]).trim();
        // isSmokingAllowed
        roomFields[3] = readRecord.read(fieldLengths[i++]).trim();
        // rate
        roomFields[4] = readRecord.read(fieldLengths[i++]).trim();
        // date
        roomFields[5] = readRecord.read(fieldLengths[i++]).trim();
        // owner
        roomFields[6] = readRecord.read(fieldLengths[i++]).trim();

        return new HotelRoom(recNo, roomFields);
    }

    private boolean parseDeletedFlag(String read)
            throws RecordNotFoundException
    {

        if (read == null || read.length() != 1) {
            throw new RecordNotFoundException("Illegal isDeleted flag read");
        }
        byte value = read.getBytes()[0];
        if (value == 0xFF) {
            logger.fine("flag value is 0xFF, record is deleted");
            return true;
        }
        if (value == 0x00) {
            logger.fine("flag value is 0x00, record is valid");
            return false;
        }
        throw new RecordNotFoundException("Illegal value for isDeleted flag: "
            + value);
    }

    // TODO can be implemented
    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {
        return 0;
    }

    // TODO can be implemented
    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

    }

    @Override
    public long[] findByCriteria(String[] criteria) {

        List<Long> results = new ArrayList<Long>();
        // TODO: pass magic cookie for caching
        // results.add(magicCookie);
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
                    logger.fine("hotelRoom" + hotelRoom.toString());

                    if (findMatch(criteria, hotelRoom)) {
                        results.add(Long.valueOf(hotelRoom.getId()));
                    }
                }
                catch (IOException e) {
                    logger.severe("Could not read record from datafile");
                }
                catch (RecordNotFoundException e) {
                    logger.severe("Error parsing data" + e.getMessage());
                }
                id++;
            }
        }
        catch (IOException e1) {
            logger.severe("Could not determine database length");
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

        long[] returnValues = new long[results.size()];
        for (int i = 0; i < results.size(); i++) {
            returnValues[i] = results.get(i);
        }
        return returnValues;
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {

        logger.info("locking record " + recNo);
        Long cookie = locks.get(recNo);
        logger.info("cookie:" + cookie + "will be used");
        if (cookie != null) {

            // synchronize on a record.
            synchronized (cookie) {

                while (locks.get(recNo) != null) {
                    try {
                        // wait till the lock on the record is released
                        cookie.wait();
                    }
                    catch (InterruptedException e) {}
                }
            }
        }
        // post conditie: cookie == null
        cookie = getNewMagicCookie();
        logger.info("record :" + recNo + "has been locked with cookie" + cookie);
        locks.put(recNo, cookie);
        return cookie;
    }

    private synchronized long getNewMagicCookie() {

        return magicCookie++;
    }

    public long getMagicCookie() {
        return magicCookie;
    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

        Long existingCookie = locks.get(recNo);
        if (existingCookie == null) {
            // do nothing, record is already unlocked
        }
        else if (!existingCookie.equals(cookie)) {
            throw new SecurityException("record is locked with other cookie");
        }
        else {
            synchronized (existingCookie) {
                locks.remove(recNo);
                // notify all threads. there is now no lock for this
                existingCookie.notifyAll();
            }
        }
    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        String[] result;
        if (recNo == -1) {
            result = columnNames;
        }
        else {
            HotelRoom room = null;
            try {
                room = retrieveHotelRoom(recNo);
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
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

        if (lockCookie != locks.get(recNo)) {
            throw new SecurityException("failed updating record: " + recNo
                + "wrong lockCookie");
        }
        logger.info("updating the record with data" + Arrays.asList(data)
            + "and lockCookie: " + lockCookie);
        // final StringBuilder out = new StringBuilder(record_length);
        byte[] emptyData = new byte[record_length];
        Arrays.fill(emptyData, (byte) ' ');
        String emptyRecordString = new String(emptyData);
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
                if (data.length() <= length) {
                    out.replace(

                    currentPosition, currentPosition + data.length(), data);
                    currentPosition += length;
                }
                else {
                    String stringTruncated = data.substring(0, length - 1);
                    out.replace(

                            currentPosition,
                            currentPosition + data.length(),
                            stringTruncated);
                    currentPosition += length;
                }
            }
        }
        RecordFieldWriter writeRecord = new RecordFieldWriter();

        int i = 0;
        String recordNotDeletedNorInvalid = "\u0000";
        writeRecord.write(recordNotDeletedNorInvalid, 1);
        String hotelName = data[i];

        logger.fine("the following data wil be written to the file");
        logger.fine("hotelName:" + hotelName + " fieldLengths[i]:"
            + fieldLengths[i]);
        writeRecord.write(hotelName, fieldLengths[i]);
        i++;

        String hotelLocation = data[i];
        logger.fine("hotelLocation:" + hotelLocation + " fieldLengths[i]:"
            + fieldLengths[i]);
        writeRecord.write(hotelLocation, fieldLengths[i]);
        i++;

        String size = data[i];
        logger.fine("size:" + size + " fieldLengths[i]:" + fieldLengths[i]);
        writeRecord.write(size, fieldLengths[i]);
        i++;

        String isSmokingAllowed = data[i];
        logger.fine("isSmokingAllowed:" + isSmokingAllowed
            + " fieldLengths[i]:" + fieldLengths[i]);
        writeRecord.write(isSmokingAllowed, fieldLengths[i]);
        i++;

        String rate = data[i];
        logger.fine("rate:" + rate + "fieldLengths[i]:" + fieldLengths[i]);
        writeRecord.write(rate, fieldLengths[i]);
        i++;

        String date = data[i];
        logger.fine("date:" + date + "fieldLengths[i]:" + fieldLengths[i]);
        writeRecord.write(date, fieldLengths[i]);
        i++;

        String owner = data[i];
        logger.fine("owner:" + owner + "fieldLengths[i]:" + fieldLengths[i]);
        writeRecord.write(owner, fieldLengths[i]);
        i++;

        // now that we have everything ready to go, we can go into our
        // synchronized block & perform our operations as quickly as possible
        // ensuring that we block other users for as little time as possible.

        synchronized (database) {
            long offset = recNo * record_length + dataStartOffset;
            logger.fine("offset:" + offset);
            try {

                database.seek(offset);
                logger.fine("the record data that is going to be written to a file"
                    + out.toString());
                database.write(out.toString().getBytes());

            }
            catch (IOException e) {
                logger.severe("error occured while writing data to the database file");
            }
        }

    }
}
