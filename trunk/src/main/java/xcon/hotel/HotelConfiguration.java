package xcon.hotel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Provides read/write access to the user's saved configuration parameters on
 * disk, so that next time they connect, we can offer the same configuration
 * parameters as a default.
 */
public class HotelConfiguration {

    private static final String DEFAULT_SERVER_ADDRESS = "localhost";
    private static Logger logger =
        Logger.getLogger(HotelConfiguration.class.getName());
    /**
     * key in Properties indicating that the value will be the database
     * location.
     */
    public static final String DATABASE_LOCATION = "DatabaseLocation";

    /**
     * key in Properties indicating that the value will be the RMI address
     * server address.
     */
    public static final String SERVER_ADDRESS = "ServerAddress";

    /**
     * key in Properties indicating that the value will be the port the RMI
     * Registry listens on.
     */
    public static final String SERVER_PORT = "ServerPort";

    /**
     * The location where our configuration file will be saved.
     */
    public static final String BASE_DIRECTORY = System.getProperty("user.dir");

    /**
     * the Properties for this application.
     */
    private Properties parameters = null;

    /**
     * the name of our properties file.
     */
    private static final String OPTIONS_FILENAME = "suncertify.properties";

    /**
     * The file containing our saved configuration.
     */
    private static File savedOptionsFile =
        new File(BASE_DIRECTORY, OPTIONS_FILENAME);

    private static HotelConfiguration instance =
        new HotelConfiguration();

    /**
     * Creates a new instance of HotelConfiguration. There should only ever be
     * one instance of this class (a Singleton), so we have made it private.
     */
    private HotelConfiguration() {

        logger.info("initializing the HotelConfiguration");
        parameters = loadParametersFromFile();

        if (parameters == null) {
            parameters = new Properties();

            parameters.setProperty(SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
            parameters.setProperty(SERVER_PORT, ""
                + java.rmi.registry.Registry.REGISTRY_PORT);
        }
    }

    /**
     * return the single instance of the SavedConfiguration.
     * @return the single instance of the SavedConfiguration.
     */
    public static HotelConfiguration getInstance() {
        return instance;
    }

    /**
     * returns the value of the named parameter.<p>
     * @param parameterName the name of the parameter for which the user is
     *            requesting the value.
     * @return the value of the named parameter.
     */
    public String getParameter(String parameterName) {
        return parameters.getProperty(parameterName);
    }

    /**
     * @param parameterName the name of the parameter.
     * @param parameterValue the value to be stored for the parameter
     */
    public void setParameter(String parameterName, String parameterValue) {
        parameters.setProperty(parameterName, parameterValue);
        saveParametersToFile();
    }

    /**
     * saves the parameters to a file so that they can be used again next time
     * the application starts.
     */
    private void saveParametersToFile() {
        try {
            synchronized (savedOptionsFile) {
                if (savedOptionsFile.exists()) {
                    savedOptionsFile.delete();
                }
                savedOptionsFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(savedOptionsFile);
                parameters.store(fos, "HotelApplication configuration");
                fos.close();
            }
        }
        catch (IOException e) {
            logger.severe("Unable to save user parameters to file. "
                + "They wont be remembered next time you start.");
        }
    }

    /**
     * attempts to load the saved parameters from the file so that the user does
     * not have to reenter all the information.
     * @return Properties loaded from file or null.
     */
    private Properties loadParametersFromFile() {
        Properties loadedProperties = null;

        if (savedOptionsFile.exists() && savedOptionsFile.canRead()) {
            synchronized (savedOptionsFile) {
                try {
                    FileInputStream fis = new FileInputStream(savedOptionsFile);
                    loadedProperties = new Properties();
                    loadedProperties.load(fis);
                    fis.close();
                }
                catch (FileNotFoundException e) {
                    logger.severe("Unable to load user "
                        + "parameters. Default values will be used.\n" + e);
                }
                catch (IOException e) {
                    assert false : "Bad data in parameters file";
                    logger.severe("Unable to load user "
                        + "parameters. Default values will be used.\n" + e);
                }
            }
        }
        return loadedProperties;
    }
}
