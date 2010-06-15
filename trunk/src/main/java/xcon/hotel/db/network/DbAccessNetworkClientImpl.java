package xcon.hotel.db.network;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.logging.Logger;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.HotelNetworkException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.RmiConnectionException;
import xcon.hotel.db.SecurityException;

/**
 * Network implementation of the DBAcces interface
 * @author Mohamed
 */
public class DbAccessNetworkClientImpl implements DBAccess {

    private Logger logger = Logger.getLogger(DbAccessNetwork.class.getName());
    DbAccessNetwork stub;
    Registry registry;

    public DbAccessNetworkClientImpl() throws DbAccesssInitializationException {

        String host = "localhost";
        try {
            registry = LocateRegistry.getRegistry(host);
        }
        catch (RemoteException e) {
            logger.warning("an exception occured while initializing the rmi host"
                + e);
        }
        try {
            stub = (DbAccessNetwork) registry.lookup("DbAccessNetwork");
        }
        catch (AccessException e) {
            throw new DbAccesssInitializationException(
                "could not access interface", e);
        }
        catch (RemoteException e) {
            throw new DbAccesssInitializationException(
                "Remote Exception occurred", e);
        }
        catch (NotBoundException e) {
            throw new DbAccesssInitializationException(
                "could not bind the rmi interface", e);
        }
    }

    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {

        long recordNumber = 0;

        try {
            logger.info("creating a record ");
            recordNumber = stub.createRecord(data);
            logger.info("record  " + recordNumber + "has been created");
            return recordNumber;

        }

        catch (RemoteException e) {

            // try 5 time to make an rmi connection
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    recordNumber = stub.createRecord(data);
                    return recordNumber;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("failed to create a record durig attempt"
                        + i);
                    continue;
                }

            }
            throw new HotelNetworkException("record could not be locked" + e);

        }

    }

    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {

        logger.info("deleting record: " + recNo + " with lockCookie "
            + lockCookie);
        try {
            stub.deleteRecord(recNo, lockCookie);
            logger.info("record  " + recNo + "has been deleted");
        }

        catch (RemoteException e) {

            // try 5 time to make an rmi connection
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    stub.deleteRecord(recNo, lockCookie);
                    return;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("failed to create a record durig attempt"
                        + i);
                    continue;
                }

            }
            throw new HotelNetworkException("record could not be locked" + e);

        }

    }

    @Override
    public long[] findByCriteria(String[] criteria) {

        /*
         * try { roomIds = stub.findByCriteria(criteria); catch (RemoteException
         * e) { // /TODO e.printStackTrace(); } return roomIds;
         */

        long[] roomIds = null;
        try {
            logger.info("finding creteria  " + Arrays.asList(criteria));
            roomIds = stub.findByCriteria(criteria);

            return roomIds;

        }
        catch (RemoteException e) {

            // try 5 time to make an rmi connection
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    roomIds = stub.findByCriteria(criteria);
                    return roomIds;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("an exception has occured while findByCriteria the creteria durig attempt"
                        + i);
                    continue;
                }

            }
            throw new HotelNetworkException(
                "an error occured while finding the creteria" + e);

        }

    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {

        long cookie = 0;

        try {
            logger.info("locking rocord " + recNo);
            cookie = stub.lockRecord(recNo);
            logger.info("cookie " + cookie + " will be returned ");
            return cookie;

        }
        catch (RemoteException e) {

            // try 5 time to make an rmi connection
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    cookie = stub.lockRecord(recNo);
                    return cookie;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("an exception has occured while reading the record durig attempt"
                        + i);
                    continue;
                }

            }
            throw new HotelNetworkException("record could not be locked" + e);

        }

    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        String[] record = null;
        try {
            logger.info("reading record" + recNo + "on client side");
            record = stub.readRecord(recNo);
            return record;
        }
        catch (RemoteException e) {

            // try 5 time to make an rmi connection
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    record = stub.readRecord(recNo);
                    return record;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("an exception has occured while reading the record durig attempt"
                        + i);
                    continue;
                }

            }
            throw new HotelNetworkException("record could not be read" + e);

        }

    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

        logger.info("unlock record " + recNo + " with cookie" + cookie
            + "on client side");

        try {
            stub.unlock(recNo, cookie);
        }
        catch (RemoteException e) {

            // try 5 time to make an rmi connection,
            for (int i = 0; i < 5; i++) {
                try {
                    retryRmiConnection();
                    stub.unlock(recNo, cookie);
                    return;
                }
                catch (RmiConnectionException e1) {
                    logger.warning("failed to create an rmi stub for attempt "
                        + i);
                    continue;
                }
                catch (RemoteException e1) {
                    logger.warning("an exception has occured while unlocking the record durin attempt"
                        + i);
                    continue;
                }

            }
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
            stub.updateRecord(recNo, data, lockCookie);
        }
        catch (RemoteException e) {

            Throwable cause = e.getCause();
            if (cause instanceof RecordNotFoundException) {
                throw (RecordNotFoundException) cause;
            }
            else if (cause instanceof SecurityException) {
                throw (SecurityException) cause;
            }
            else {

                // try 5 time to make an rmi connection
                for (int i = 0; i < 5; i++) {
                    try {
                        retryRmiConnection();
                        stub.updateRecord(recNo, data, lockCookie);
                        return;
                    }
                    catch (RmiConnectionException e1) {
                        logger.warning("failed to create an rmi stub for attempt "
                            + i);
                        continue;
                    }
                    catch (RemoteException e1) {
                        logger.warning("an exception has occured wihile update the record durin attempt"
                            + i);
                        continue;
                    }

                }
                throw new HotelNetworkException("record could not be updated"
                    + e);

            }

        }

    }

    private void retryRmiConnection() throws RmiConnectionException {

        String host = "localhost";
        try {
            registry = LocateRegistry.getRegistry(host);
        }
        catch (RemoteException e) {
            logger.warning("an exception occured while initializing the rmi host"
                + e);
        }
        try {
            stub = (DbAccessNetwork) registry.lookup("DbAccessNetwork");
        }

        catch (AccessException e) {
            throw new RmiConnectionException("could not access interface", e);
        }
        catch (RemoteException e) {
            throw new RmiConnectionException("Remote Exception occurred", e);
        }
        catch (NotBoundException e) {
            throw new RmiConnectionException("could not bind the rmi interface",
                e);
        }
    }

}
