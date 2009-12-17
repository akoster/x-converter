package xcon.urlybird.suncertify.db.stub;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xcon.urlybird.suncertify.db.DBAccess;
import xcon.urlybird.suncertify.db.DuplicateKeyException;
import xcon.urlybird.suncertify.db.RecordNotFoundException;
import xcon.urlybird.suncertify.db.SecurityException;

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

		hotelRooms.put(new Long(1), new String[] { "best resort", "rotterdam",
				"2", "y", "129", "12-12-2008", "11111111" });

		hotelRooms.put(new Long(2), new String[] { "hilton", "amterdam", "4",
				"n", "350", "12-12-2009", "2222222" });

		hotelRooms.put(new Long(3), new String[] { "golden tulip", "amterdam",
				"2", "n", "120", "12-09-2009", null });

		locks = new HashMap<Long, Long>();

		customerIds = new HashMap<Long, String>();
	}

	@Override
	public long createRecord(String[] data) throws DuplicateKeyException {

		long id = getNextId();
		bookRoom(id, data);
		return id;
	}

	@Override
	public long lockRecord(long recNo) throws RecordNotFoundException {

		// XXX hier staat. at most one pogramm is accesting de database file.
		// mogen we op de database lock. Ik weet dat dit niet effecient is, maar
		// mag het ?

		/*
		 * Locking Your server must be capable of handling multiple concurrent
		 * requests, and as part of this capability, must provide locking
		 * functionality as specified in the interface provided above. You may
		 * assume that at any moment, at most one program is accessing the
		 * database file; therefore your locking system only needs to be
		 * concerned with multiple concurrent clients of your server.
		 */

		// XXX wordt hiermee bedoeld thread.wait.
		/*
		 * Any attempt to lock a resource that is already locked should cause
		 * the current thread to give up the CPU, consuming no CPU cycles until
		 * the desired resource becomes available.
		 */

		Long cookie = locks.get(recNo);
		if (cookie != null) {
			// pre conditie: cookie != null
			// There is a cookie for this record, means this is used by another
			// user.

			// XXX Ik wist niet op welke object ik moet synchroniseren. Ik zou
			// eerder deze methode in een synchronized block willen zetten, maar
			// dat lijkt me niet slim
			// locken op de map hotelrooms lijks me ook niet slim
			// ik lock nu op recordNr deze waarde haal ik uit de keys van de map
			// via de methode getRecordNumberObject
			// ik mag namelijk geen primitieve waarde in een sysnchronized blok
			// zetten
			Long recordNr = getRecordNumberObject(recNo);
			synchronized (recordNr) {

				// wachten tot de lock is removed
				while (locks.get(recNo) != null) {
					try {
						recordNr.wait();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 
			}
			// TODO pause this thread until lock is removed

			// post conditie: cookie == null
		}
		// post conditie: cookie == null

		cookie = getNewMagicCookie();
		locks.put(recNo, cookie);
		return cookie;
	}

	private Long getRecordNumberObject(long recNo)
			throws RecordNotFoundException {
		for (Iterator<Long> iterator = hotelRooms.keySet().iterator(); iterator
				.hasNext();) {
			Long recordNumber = iterator.next();
			if (recordNumber.longValue() == recNo) {
				return recordNumber;
			}
		}
		throw new RecordNotFoundException("record not found");
	}

	@Override
	public void unlock(long recNo, long cookie) throws SecurityException {

		// XXX Ik heb een methode getRecordNumberObject, deze methode kan een
		// recordNotFound exceptie gooien.
		// Ik catch hem. doe ik het goed.?
		if (locks.get(recNo) == null) {
			throw new SecurityException("record already unlocked");
		}
		if (cookie != locks.get(recNo)) {
			throw new SecurityException("record is locked with another cookie");
		}

		try {
			Long recordNr = getRecordNumberObject(recNo);
			synchronized (recordNr) {
				locks.put(recordNr, null);
				// notify all thread. there is now no lock for this
				recordNr.notifyAll();
			}
		} catch (RecordNotFoundException e) {
			throw new SecurityException("record recNo does not exist", e);
		}
	}

	@Override
	public void deleteRecord(long recNo, long lockCookie)
			throws RecordNotFoundException, SecurityException {

		// // wordt niet geist door de sun opdraracht.

		/*
		 * Long cookie = locks.get(recNo); if (cookie != lockCookie) { throw new
		 * SecurityException(); } else { String[] data = hotelRooms.get(recNo);
		 * if (data == null) { throw new RecordNotFoundException(); } else {
		 * hotelRooms.remove(recNo); } }
		 */

	}

	@Override
	public long[] findByCriteria(String[] criteria) {

		Long[] returnValues;

		if (criteria == null) {
			returnValues = (Long[]) hotelRooms.keySet().toArray();
		} else {
			int i = 0;
			returnValues = new Long[hotelRooms.size()];

			Iterator<Entry<Long, String[]>> it = hotelRooms.entrySet()
					.iterator();
			while (it.hasNext()) {
				
				Map.Entry<?, ?> pairs = it.next();
				String[] room = (String[]) pairs.getValue();
				String roomData = Arrays.toString(room);

				long id = (Long) pairs.getKey();
				Pattern p = Pattern.compile(criteria[0]);
				Matcher m = p.matcher(roomData);
				if (m.find()) {
					System.out.println("id found" + id);
					returnValues[i] = id;
					i++;
					continue;
				}
				p = Pattern.compile(criteria[1]);
				m = p.matcher(roomData);
				if (m.find()) {
					System.out.println("id found" + id);
					returnValues[i] = id;
					i++;
				}
			}
		}
		long[] rowsFound = new long[returnValues.length];

		for (int i = 0; i < returnValues.length; i++) {
			rowsFound[i] = returnValues[i].longValue();
		}
		return rowsFound;
	}

	@Override
	public String[] readRecord(long recNo) throws RecordNotFoundException {
		return null;
	}

	// the problem with this method is that you have to update and book the
	// room. both in one method

	@Override
	public void updateRecord(long recNo, String[] data, long lockCookie)
			throws RecordNotFoundException, SecurityException {

		// XXX this methode does not have to be sysnchronized, because the lock
		// method sysnchronzed on an row.
		// the lock on the row get released only after the unlock method

		if (locks.get(recNo) == null) {
			throw new SecurityException("record is not locked");
		}
		if (locks.get(recNo) != lockCookie) {
			throw new SecurityException("record" + recNo
					+ " is locked with other cookie");
		}
		String customerId = getCustomerId(recNo);

		// updating the customer holding this record
		// customerId is '
		// customerId == null, means that the room is free and can be booked

		if (customerId == null) {

			System.out.println("room is empty and can be booked");
			customerId = generateCustumerId(recNo);
			// update the cutomer holding this id
			data[data.length - 1] = customerIds.get(recNo);
			bookRoom(recNo, data);
			System.out.println("Room has been booked, This is your id: "
					+ customerId + "\n please do not loose you cutomerId");
		}
		// update the rest of the value
		// deze functionliteit wordt niet geeist
		else {
			// throw new
			// BookNotAllowedException("record is booked. You are not allowed to book this room");
			System.out
					.println("record is already booked. You are not allowed to book this room");
			// TODO not in this assingment
		}
		System.out.println("hotelrooms" + hotelRooms.toString());
	}

	// booking a room is symply updating the Custumer Field a proposed by the
	// assignment
	private void bookRoom(long recNo, String[] data) {
		hotelRooms.put(recNo, data);
	}

	private String generateCustumerId(long recNo) {
		Random random = new Random(99999999);
		String customerId = String.valueOf(random.nextLong());
		// ik stop dit in een map omdat ik door de random alleen maar unieke
		// waardes wil hebben. Dit is nog niet geimplemteerd
		customerIds.put(recNo, customerId);
		return customerId;
	}

	private String getCustomerId(long recordNr) {
		String[] hotelRoomData = hotelRooms.get(recordNr);
		String customerId = hotelRoomData[hotelRoomData.length - 1];
		return customerId;
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

	// XXX deze gevens komt niet overeen met database file die ik van een
	// collega gekregen heb.
	// bovendien lijkt de eeste zin niet op de derde zin
	/*
	 * Schema description section. Repeated for each field in a record: 1- 1
	 * byte numeric, length in bytes of field name 2- n bytes (defined by
	 * previous entry), field name 3- 1 byte numeric, field length in bytes end
	 * of repeating block
	 */

	/*
	 * Data section. Repeat to end of file: 1 byte flag. 00 implies valid
	 * record, 0xFF implies deleted record Record containing fields in order
	 * specified in schema section, no separators between fields, each field
	 * fixed length at maximum specified in schema information
	 */
}
