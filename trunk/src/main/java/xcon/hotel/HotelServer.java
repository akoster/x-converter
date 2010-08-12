package xcon.hotel;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Logger;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.db.local.DbAccessFileImpl;
import xcon.hotel.db.network.DbAccessNetwork;

public class HotelServer implements DbAccessNetwork {

    private static final Logger logger =
        Logger.getLogger(HotelServer.class.getName());

    static Registry registry = null;
    static DbAccessNetwork stub = null;
    static DbAccessFileImpl dbAccessFileImpl;

    public static void main(String[] args) throws RemoteException,
            DbAccesssInitializationException, AlreadyBoundException,
            InterruptedException
    {

        dbAccessFileImpl = new DbAccessFileImpl();

        stub =
            (DbAccessNetwork) UnicastRemoteObject.exportObject(new HotelServer(),0);

        registry = java.rmi.registry.LocateRegistry.createRegistry(1099);

        Thread.sleep(5000);

        registry.rebind("DbAccessNetwork", stub);

        logger.info("Server started.");
    }

    // TODO this method need to be implemented
    @Override
    public long createRecord(String[] data) throws RemoteException,
            DuplicateKeyException
    {
        dbAccessFileImpl.createRecord(data);
        return 0;
    }

    // TODO the method need to be implemented
    @Override
    public void deleteRecord(long recNo, long lockCookie)

    throws RemoteException, RecordNotFoundException, SecurityException
    {
        dbAccessFileImpl.deleteRecord(recNo, lockCookie);
    }

    @Override
    public long[] findByCriteria(String[] criteria) throws RemoteException {
        long[] roomIds;
        roomIds = dbAccessFileImpl.findByCriteria(criteria);
        return roomIds;
    }

    @Override
    public long lockRecord(long recNo) throws RemoteException,
            RecordNotFoundException
    {

        logger.fine("locking record " + recNo + "on server side");
        long cookie = 0;
        cookie = dbAccessFileImpl.lockRecord(recNo);
        return cookie;
    }

    @Override
    public String[] readRecord(long recNo) throws RemoteException,
            RecordNotFoundException
    {

        logger.fine(" reading record" + recNo + "on server side");

        String[] record = null;
        record = dbAccessFileImpl.readRecord(recNo);
        logger.fine("the record that is  read on server side is:"
            + Arrays.asList(record));
        return record;

    }

    @Override
    public void unlock(long recNo, long cookie) throws RemoteException,
            SecurityException
    {

        logger.fine("unlock record " + recNo + " with cookie" + cookie
            + "on server side ");
        dbAccessFileImpl.unlock(recNo, cookie);

    }

    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RemoteException, RecordNotFoundException, SecurityException
    {
        logger.fine("updating " + recNo + "with data: " + Arrays.asList(data)
            + " and magic lockcookie " + lockCookie + " on client side");

        dbAccessFileImpl.updateRecord(recNo, data, lockCookie);

    }
}
