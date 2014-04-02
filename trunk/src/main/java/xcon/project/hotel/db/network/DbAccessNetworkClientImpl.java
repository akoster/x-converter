package xcon.project.hotel.db.network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.logging.Logger;
import xcon.project.hotel.db.DBAccess;
import xcon.project.hotel.db.DuplicateKeyException;
import xcon.project.hotel.db.HotelNetworkException;
import xcon.project.hotel.db.RecordNotFoundException;
import xcon.project.hotel.db.SecurityException;

/**
 * Network implementation of the DBAcces interface
 * @author Mohamed
 */
public class DbAccessNetworkClientImpl implements DBAccess {

    private Logger logger = Logger.getLogger(DbAccessNetwork.class.getName());
    DbAccessNetwork stub;

    private DbAccessNetwork getDbAccessNetworkStub() {

        int retries = 0;
        while (stub == null && retries < 5) {

            String host = "localhost";
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(host);
                stub = (DbAccessNetwork) registry.lookup("DbAccessNetwork");
            }
            catch (Exception e) {
                logger.warning("Could not create RMI stub: " + e.getMessage());
                e.printStackTrace();
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e1) {
                    // ignore
                }
            }
            retries++;
        }
        if (stub == null) {
            throw new HotelNetworkException("Could not initialize RMI stub");
        }
        return stub;
    }

    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {

        long recordNumber = 0;
        try {
            logger.info("creating a record ");
            recordNumber = getDbAccessNetworkStub().createRecord(data);
            logger.info("record  " + recordNumber + "has been created");
            return recordNumber;

        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("Record could not be locked" + e);
        }
    }

    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

        logger.info("deleting record: " + recNo + " with lockCookie "
            + lockCookie);
        try {
            getDbAccessNetworkStub().deleteRecord(recNo, lockCookie);
            logger.info("record  " + recNo + "has been deleted");
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("record could not be locked" + e);
        }
    }

    @Override
    public long[] findByCriteria(String[] criteria) {

        long[] roomIds = null;
        try {
            logger.info("finding criteria  " + Arrays.asList(criteria));
            roomIds = getDbAccessNetworkStub().findByCriteria(criteria);
            return roomIds;
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException(
                "an error occured while finding the creteria" + e);
        }
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {

        long cookie = 0;

        try {
            logger.info("locking rocord " + recNo);
            cookie = getDbAccessNetworkStub().lockRecord(recNo);
            logger.info("cookie " + cookie + " will be returned ");
            return cookie;
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("record could not be locked" + e);
        }
    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        String[] record = null;
        try {
            logger.fine("reading record" + recNo + "on client side");
            record = getDbAccessNetworkStub().readRecord(recNo);
            return record;
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("record could not be read" + e);
        }
    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

        logger.info("unlock record " + recNo + " with cookie" + cookie
            + "on client side");
        try {
            getDbAccessNetworkStub().unlock(recNo, cookie);
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("record could not be unlocked" + e);
        }
    }

    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
        logger.info("updating " + recNo + "with data: " + Arrays.asList(data)
            + " and magic lockcookie " + lockCookie + " on client side");
        try {
            getDbAccessNetworkStub().updateRecord(recNo, data, lockCookie);
        }
        catch (RemoteException e) {
            stub = null;
            throw new HotelNetworkException("record could not be unlocked" + e);
        }
    }
}
