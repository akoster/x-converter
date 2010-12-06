package xcon.hotel;

import java.rmi.RemoteException;
import java.util.logging.Logger;
import xcon.hotel.client.Controller;
import xcon.hotel.client.ControllerImpl;
import xcon.hotel.client.gui.SwingGui;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.stub.DbAccessStub;

/**
 * Startup class for Hotel application
 * @author mohamed loudiyi
 */
/**
 * @author Mohamed
 */
public class HotelApplication {

    private static Logger logger =
        Logger.getLogger(HotelApplication.class.getName());

    private ApplicationMode mode;

    public static void main(String[] args)
            throws DbAccesssInitializationException, ControllerException,
            RemoteException, InterruptedException
    {
        new HotelApplication(ApplicationMode.parseArguments(args));
    }

    public HotelApplication(ApplicationMode mode) {
        logger.info("Started the hotel application in mode " + mode);
        this.mode = mode;

        switch (mode) {
        case SERVER:
            new HotelServerWindow(this);
            break;
        case STUB:
            // XXX: fix DbAccessStub
            this.dbAccessInitialized(new DbAccessStub());
            break;

        default:
            new HotelMainWindow(this);
            break;
        }
    }

    public void dbAccessInitialized(DBAccess dbAccess) {
        try {
            if (mode == ApplicationMode.SERVER) {
                new HotelServer(dbAccess);
            }
            else {
                Controller controller = new ControllerImpl(dbAccess);
                new SwingGui(controller);
            }
        }
        catch (DbAccesssInitializationException e) {
            logger.severe(e.getMessage());
        }
        catch (RemoteException e) {
            logger.severe(e.getMessage());
        }
        catch (ControllerException e) {
            logger.severe(e.getMessage());
        }
    }

    public ApplicationMode getMode() {
        return mode;
    }
}
