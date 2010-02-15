package xcon.hotel.db.network;

import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;

public class DbAccessNetworkClientImpl implements DBAccess {

	@Override
	public long createRecord(String[] data) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteRecord(long recNo, long lockCookie)
			throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public long[] findByCriteria(String[] criteria) {
		// TODO Auto-generated method stub
		return null;
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
			throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub

	}

}
