package xcon.hotel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import xcon.hotel.client.Controller;
import xcon.hotel.client.ControllerImpl;
import xcon.hotel.client.SwingGui;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbException;
import xcon.hotel.db.local.DbAccessFileImpl;
import xcon.hotel.db.network.DbAccessNetworkClientImpl;
import xcon.hotel.db.stub.DbAccessStub;

/**
 * Startup class for Hotel application
 * @author mohamed loudiyi
 */
public class HotelApplication {

    public static final String HOTEL_APPLICATION = "hotel-application";
    private Logger logger = Logger.getLogger(HOTEL_APPLICATION);

    private enum Mode {
        NETWORKED, LOCAL, TEST
    }

    /**
     * @param args
     * @throws DbException if access to the database failed
     */
    public static void main(String[] args) throws DbException {

        Mode mode = parseArguments(args);
        DBAccess dbAccess;
        dbAccess = getDbAccess(mode);
        Controller controller = new ControllerImpl(dbAccess);
        SwingGui gui = new SwingGui(controller);
    }

    private void createLogger() {

        logger = Logger.getLogger(HOTEL_APPLICATION);
        LogManager lm = LogManager.getLogManager();
        FileHandler fh = null;
        try {
            fh = new FileHandler("hotel_log.txt");
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        lm.addLogger(logger);
        logger.setLevel(Level.INFO);
        fh.setFormatter(new SimpleFormatter());

        logger.addHandler(fh);
    }

    private static Mode parseArguments(String[] args) {

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
        return mode;
    }

    private static DBAccess getDbAccess(Mode mode) throws DbException {

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
            // dbAccess = new DbAccessStub();
            dbAccess = new DbAccessFileImpl();
            break;
        }
        return dbAccess;
    }
}
