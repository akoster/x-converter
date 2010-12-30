package xcon.hotel;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import xcon.hotel.client.Controller;
import xcon.hotel.client.ControllerImpl;
import xcon.hotel.client.gui.ClientWindow;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.SwingGuiException;

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
        new StartUpWindow(this);
    }

    /*
     * Callback, called by StartUpWindow after the user has entered startup
     * parameters
     */
    public void dbAccessInitialized(DBAccess dbAccess) {

        if (mode == ApplicationMode.SERVER) {
            createServerApplication(dbAccess);
        }
        else {
            createClientApplication(dbAccess);
        }
    }

    private void createClientApplication(DBAccess dbAccess) {
        try {
            Controller controller = new ControllerImpl(dbAccess);
            new ClientWindow(controller);
        }
        catch (ControllerException e) {
            logger.log(Level.SEVERE, "Could not initialize controller", e);
        }
        catch (SwingGuiException e) {
            logger.log(Level.SEVERE, "Could not initialize Swing gui", e);
        }
    }

    private void createServerApplication(DBAccess dbAccess) {
        try {
            new HotelServer(dbAccess);
        }
        catch (RemoteException e) {
            logger.log(Level.SEVERE, "error 2", e);
        }
        catch (DbAccesssInitializationException e) {
            logger.log(Level.SEVERE, "error 1", e);
        }
    }

    /**
     * Accessor for the startup mode of this application
     * @return
     */
    public ApplicationMode getMode() {
        return mode;
    }
}
