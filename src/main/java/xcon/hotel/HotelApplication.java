package xcon.hotel;

import java.io.IOException;
import java.util.logging.Logger;
import xcon.hotel.client.Controller;
import xcon.hotel.client.ControllerImpl;
import xcon.hotel.client.SwingGui;
import xcon.hotel.db.ControllerException;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.local.DbAccessFileImpl;
import xcon.hotel.db.network.DbAccessNetworkClientImpl;
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

    private enum Mode {
        NETWORKED, LOCAL, TEST
    }

    /**
     * @param args
     * @throws DbAccesssInitializationException
     * @throws DbAccesssInitializationException if access to the database failed
     * @throws ControllerException
     * @throws IOException
     * @throws SecurityException
     */
    public static void main(String[] args)
            throws DbAccesssInitializationException, ControllerException
    {

        logger.info("starting the hotel appliacation");
        Mode mode = parseArguments(args);
        logger.fine("The application has parsed mode:" + mode.toString());

        DBAccess dbAccess;
        logger.fine("getting the dbAccesMode");
        dbAccess = getDbAccess(mode);
        Controller controller = new ControllerImpl(dbAccess);
        SwingGui gui = new SwingGui(controller);
    }

    private static Mode parseArguments(String[] args) {

        logger.fine("parsing arguments!");
        Mode mode = Mode.TEST;
        if (args.length > 0) {
            if ("networked".equals(args[0])) {
                mode = Mode.NETWORKED;
            }
            else if ("local".equals(args[0])) {
                mode = Mode.LOCAL;
            }
            else {
                throw new IllegalArgumentException("Could not parse argument: "
                    + args[0]
                    + ". Allowed arguments are 'networked' and 'local'");
            }
        }
        logger.info("the hotelapplication is going to use mode: " + mode);
        return mode;
    }

    private static DBAccess getDbAccess(Mode mode)
            throws DbAccesssInitializationException
    {

        DBAccess dbAccess;
        switch (mode) {
        case NETWORKED:
            dbAccess = new DbAccessNetworkClientImpl();
            break;
        case LOCAL:
            dbAccess = new DbAccessFileImpl();
            break;
        default:
            // XXX: fix DbAccessStub
            dbAccess = new DbAccessStub();
            // dbAccess = new DbAccessFileImpl();
            // dbAccess = new DbAccessNetworkClientImpl();
            break;
        }
        return dbAccess;
    }
}
