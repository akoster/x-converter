package xcon.hotel.db.network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import xcon.hotel.db.DuplicateKeyException;
import xcon.hotel.db.RecordNotFoundException;
import xcon.hotel.db.SecurityException;

public interface DbAccessNetwork extends Remote {

    public long createRecord(String[] data) throws RemoteException,
            DuplicateKeyException;

    public void deleteRecord(long recNo, long lockCookie)
            throws RemoteException, RecordNotFoundException, SecurityException;

    public long[] findByCriteria(String[] criteria) throws RemoteException;;

    public long lockRecord(long recNo) throws RemoteException,
            RecordNotFoundException;

    public String[] readRecord(long recNo) throws RemoteException,
            RecordNotFoundException;

    public void unlock(long recNo, long cookie) throws RemoteException,
            SecurityException;

    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RemoteException, RecordNotFoundException, SecurityException;
}
