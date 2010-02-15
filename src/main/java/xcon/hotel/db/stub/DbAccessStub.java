package xcon.hotel.db.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;

/*
 * In-memory implementatie
 */
public class DbAccessStub implements DBAccess {

    // map of ID to HotelRoom
    private Map<Long, String[]> hotelRooms;

    // map of ID to magicCookie
    private Map<Long, Long> locks;

    private Map<Long, String> customerIds;

    private Long recordNrSequence;

    public DbAccessStub() {

        hotelRooms = new HashMap<Long, String[]>();
        // TODO insert here some test data.

        hotelRooms.put(new Long(1), new String[] {
                "best resort", "rotterdam", "2", "y", "129", "12-12-2008",
                "11111111"
        });

        hotelRooms.put(new Long(2), new String[] {
                "hilton", "amsterdam", "4", "n", "350", "12-12-2009", "2222222"
        });

        hotelRooms.put(new Long(3), new String[] {
                "golden tulip", "amsterdam", "2", "n", "120", "12-09-2009",
                null
        });

        locks = new HashMap<Long, Long>();

        customerIds = new HashMap<Long, String>();
    }

    public void setHotelRooms(Map<Long, String[]> hotelRooms) {
        this.hotelRooms = hotelRooms;
    }

    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {

        // XXX to be implemented
        long id = getNextId();
        return id;
    }

    // returns an Long Object of a primiteive value
    private Long getRecordNumberObject(long recNo)
            throws RecordNotFoundException
    {
        for (Iterator<Long> iterator = hotelRooms.keySet().iterator(); iterator.hasNext();)
        {
            Long recordNumber = iterator.next();
            if (recordNumber.longValue() == recNo) {
                return recordNumber;
            }
        }
        throw new RecordNotFoundException("record not found");
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {

        Long cookie = locks.get(recNo);
        if (cookie != null) {

            // synchronize on a record.
            Long recordNr = getRecordNumberObject(recNo);
            synchronized (recordNr) {

                while (locks.get(recNo) != null) {
                    try {
                        // wait till the lock on the record is released
                        recordNr.wait();

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // post conditie: cookie == null
        cookie = getNewMagicCookie();
        locks.put(recNo, cookie);
        return cookie;
    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

        if (cookie != locks.get(recNo)) {
            throw new SecurityException("record is locked");
        }
        else {
            try {
                Long recordNr = getRecordNumberObject(recNo);
                synchronized (recordNr) {
                    locks.put(recordNr, null);
                    // notify all thread. there is now no lock for this
                    recordNr.notifyAll();
                }

            }
            catch (RecordNotFoundException e) {
                System.out.println("record Not foud");
            }
        }
    }

    @Override
    // XXX to be implemented, not necessary according to the assignment
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

    }

    @Override
    public long[] findByCriteria(String[] criteria) {

        System.out.println("criteria " + criteria.toString());
        if (criteria == null) {
            throw new IllegalArgumentException("criteria may not be null");
        }

        // for (String string : criteria) {
        // System.out.println("criteria" + string);
        // }

        List<Long> results = new ArrayList<Long>();
        Iterator<Map.Entry<Long, String[]>> it =
            hotelRooms.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry<Long, String[]> pair = it.next();
            String[] room = (String[]) pair.getValue();
            
            if (findMatch(criteria, room)) {
                results.add((Long) pair.getKey());
            }
        }
        return copyValues(results);
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

    private boolean findMatch(String[] criteria, String[] room) {

        boolean isMatch = true;
        for (int c = 0; c < criteria.length; c++) {

            if (criteria[c] == null || criteria[c].length() == 0) {
                continue;
            }
            String field = room[c].toLowerCase();
            System.out.println("field " + field + "c: "+ c );
            String criterium = criteria[c].toLowerCase();
            if (!field.startsWith(criterium)) {

                isMatch = false;
                break;
            }
        }
        return isMatch;
    }

    @Override
    // // XXX to be implemented, not necessary according to the assignment
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        String[] record = hotelRooms.get(recNo);
        if (record == null) {
            throw new RecordNotFoundException("record does not exist");
        }

        return record;
    }

    // the problem with this method is that you have to update and book the
    // room. both in one method
    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

        if (locks.get(recNo) == null) {
            throw new SecurityException("record is not locked");
        }
        if (locks.get(recNo) != lockCookie) {
            throw new SecurityException("record" + recNo
                + " is locked with other cookie");
        }
        if (hotelRooms.get(recNo) == null) {
            throw new RecordNotFoundException("there is no such record" + recNo);
        }
        hotelRooms.put(recNo, data);
    }

  

    // synchronized to avoid concurrent access problems
    private synchronized long getNextId() {
        return ++recordNrSequence;
    }

    private synchronized Long getNewMagicCookie() {

        Long magicCookie = (long) (Math.random() * 999999999) + 1;
        return magicCookie;
    }

    public Map<Long, String[]> getHotelRooms() {
        return hotelRooms;
    }

   
}
