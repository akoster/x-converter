package xcon.hotel;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import xcon.hotel.client.Controller;
import xcon.hotel.client.ControllerImpl;
import xcon.hotel.client.SwingGui;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.DbException;
import xcon.hotel.db.local.DbAccessFileImpl;
import xcon.hotel.db.network.DbAccessNetworkClientImpl;

/**
 * Startup class for Hotel application
 * @author mohamed loudiyi
 */
public class HotelApplication {

    private static Logger logger = Logger.getLogger("hotel-application");

    static {
        try {
            ResourceBundle props = ResourceBundle.getBundle("hotel_log");
            String levelName = props.getString("level");
            // default
            Level level = Level.INFO;
            if (levelName != null) {
                try {
                    level = Level.parse(levelName);
                }
                catch (IllegalArgumentException e) {
                    // ignore, fall back to default
                }
            }
            logger.setLevel(level);

            Formatter formatter = new Formatter() {

                @Override
                public String format(LogRecord arg0) {
                    StringBuilder b = new StringBuilder();
                    b.append(new Date());
                    b.append(" ");
                    b.append(arg0.getSourceClassName());
                    b.append(" ");
                    b.append(arg0.getSourceMethodName());
                    b.append(" ");
                    b.append(arg0.getLevel());
                    b.append(" ");
                    b.append(arg0.getMessage());
                    b.append(System.getProperty("line.separator"));
                    return b.toString();
                }

            };

            Handler fh = new FileHandler("hotel_log.txt");
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            Handler ch = new ConsoleHandler();
            ch.setFormatter(formatter);
            logger.addHandler(ch);

            LogManager lm = LogManager.getLogManager();
            lm.addLogger(logger);
            
            logger.setUseParentHandlers(false);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private enum Mode {
        NETWORKED, LOCAL, TEST
    }

    /**
     * @param args
     * @throws DbException if access to the database failed
     * @throws IOException
     * @throws SecurityException
     */
    public static void main(String[] args) throws Exception {

        Mode mode = parseArguments(args);
        DBAccess dbAccess;
        dbAccess = getDbAccess(mode);
        Controller controller = new ControllerImpl(dbAccess);
        SwingGui gui = new SwingGui(controller);
    }

    private static Mode parseArguments(String[] args) {

        logger.info("parsing arguments!");
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
