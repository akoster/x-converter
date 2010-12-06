package xcon.hotel;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.swing.JPanel;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;
import xcon.hotel.db.network.DbAccessNetwork;

public class HotelServer extends JPanel implements DbAccessNetwork {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger =
        Logger.getLogger(HotelServer.class.getName());

    private Registry registry;
    private DbAccessNetwork stub;
    private DBAccess dataFile;

    public HotelServer(DBAccess dataFile)
            throws DbAccesssInitializationException, RemoteException
    {
        this.dataFile = dataFile;

        stub = (DbAccessNetwork) UnicastRemoteObject.exportObject(this, 0);

        registry = java.rmi.registry.LocateRegistry.createRegistry(1099);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            // ignore
        }

        registry.rebind("DbAccessNetwork", stub);

        logger.info("Server started.");
    }

    // TODO this method need to be implemented
    @Override
    public long createRecord(String[] data) throws DuplicateKeyException {
        dataFile.createRecord(data);
        return 0;
    }

    // TODO the method need to be implemented
    @Override
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
        dataFile.deleteRecord(recNo, lockCookie);
    }

    @Override
    public long[] findByCriteria(String[] criteria) {
        long[] roomIds;
        roomIds = dataFile.findByCriteria(criteria);
        return roomIds;
    }

    @Override
    public long lockRecord(long recNo) throws RecordNotFoundException {

        logger.fine("locking record " + recNo + "on server side");
        long cookie = 0;
        cookie = dataFile.lockRecord(recNo);
        return cookie;
    }

    @Override
    public String[] readRecord(long recNo) throws RecordNotFoundException {

        logger.fine(" reading record" + recNo + "on server side");

        String[] record = null;
        record = dataFile.readRecord(recNo);
        logger.fine("the record that is  read on server side is:"
            + Arrays.asList(record));
        return record;

    }

    @Override
    public void unlock(long recNo, long cookie) throws SecurityException {

        logger.fine("unlock record " + recNo + " with cookie" + cookie
            + "on server side ");
        dataFile.unlock(recNo, cookie);

    }

    @Override
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException
    {
        logger.fine("updating " + recNo + "with data: " + Arrays.asList(data)
            + " and magic lockcookie " + lockCookie + " on client side");

        dataFile.updateRecord(recNo, data, lockCookie);

    }
}
