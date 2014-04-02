package xcon.project.hotel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import xcon.project.hotel.client.gui.DatabaseFileLocator;

/**
 * @author Mohamed Swing components will be places on this panel depending on
 *         the mode. - on the alone mode , only a dataBaseFilePath will be
 *         placed - on the server mode, only a dataBaseFilePath and a portnumber
 *         jtextfield will be placed. - on the networked mode, a portNumber
 *         jtextfield , hostName jtextfield)will be placed
 */
public class ConfigPanel extends JPanel {

    private Logger logger = Logger.getLogger(ConfigPanel.class.getName());

    private static final long serialVersionUID = 1L;

    private static ResourceBundle messages =
        ResourceBundle.getBundle("hotel_messages", Locale.getDefault());

    private static final String DB_LOCATION_LABEL =
        messages.getString("startupwindow.label.database.location");
    private static final String SERVER_PORT_LABEL =
        messages.getString("startupwindow.label.server.port");
    private static final String SERVER_LOCATION_LABEL =
        messages.getString("startupwindow.label.server.location");
    private static final String SERVER_PORT_TOOL_TIP =
        messages.getString("startupwindow.tooltip.port");
    private static final String DB_LOCATION_TOOL_TIP =
        messages.getString("startupwindow.tooltip.database.location");
    private static final String SERVER_LOCATION_TOOL_TIP =
        messages.getString("startupwindow.tooltip.server.address");
    private static final String BROWSE_DATABASE_BOTTON_NAME =
        messages.getString("startupwindow.button.browse.name");
    private static final String BROWSE_DATABASE_BOTTON_TOOL_TIP =
        messages.getString("startupwindow.tooltip.browsebutton");

    private JTextField dataBaseFilePath = new JTextField(40);
    private JButton browseButton = new JButton(BROWSE_DATABASE_BOTTON_NAME);
    private JTextField portNumber = new JTextField(5);
    private JTextField hostName = new JTextField(25);

    private DatabaseFileLocator databaseFileLocator;
    private HotelConfiguration config = HotelConfiguration.getInstance();
    private GridBagLayout gridbag = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();

    private ApplicationMode mode;

    public ConfigPanel(ApplicationMode mode) {
        logger.info("initializing HotelConfigWindowPanel with mode: " + mode);
        this.mode = mode;
        this.setLayout(gridbag);
        // ensure there is always a gap between components
        constraints.insets = new Insets(2, 2, 2, 2);

        // Build the Data file location row if the mode is server or alone
        if (mode.needsLocalDatabaseFile()) {
            createDatabaseFileLocatorRow();
        }

        constraints.weightx = 0.0;

        // build portnumber for server or networked mode
        if (mode.needsHostName()) {
            // build hostname for the networked mode
            createServerHostNameRow();
        }

        if (mode.needsPortNumber()) {
            // build portnumber row for the server mode and networked mode
            createPortNumberRow();
        }

    }

    private void createPortNumberRow() {
        logger.info("creationg a portnumber row");
        JLabel serverPortLabel = new JLabel(SERVER_PORT_LABEL);
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(serverPortLabel, constraints);
        this.add(serverPortLabel);
        portNumber.setToolTipText(SERVER_PORT_TOOL_TIP);
        portNumber.setName(SERVER_PORT_LABEL);
        constraints.gridwidth = GridBagConstraints.REMAINDER; // end row
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(portNumber, constraints);
        this.add(portNumber);
        String portNumberValue =
            config.getParameter(HotelConfiguration.SERVER_PORT);
        setPortNumberText(portNumberValue);
        logger.info("PortNumberRow has been created. The value of PortNumber is :"
            + portNumberValue);
    }

    private void createServerHostNameRow() {
        logger.info("creationg a server hostname  row");
        JLabel serveLocationLabel = new JLabel(SERVER_LOCATION_LABEL);
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(serveLocationLabel, constraints);
        this.add(serveLocationLabel);
        hostName.setToolTipText(SERVER_LOCATION_TOOL_TIP);
        constraints.gridwidth = GridBagConstraints.REMAINDER; // end row
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(hostName, constraints);
        this.add(hostName);
        constraints.weightx = 0.0;
        String ServerAddress =
            config.getParameter(HotelConfiguration.SERVER_ADDRESS);
        setHostNameText(config.getParameter(HotelConfiguration.SERVER_ADDRESS));
        logger.info("ServerHostNameRow has been created. The value of ServerAddress is :"
            + ServerAddress);
    }

    private void createDatabaseFileLocatorRow() {
        logger.info("creationg a dabases location row");
        JLabel dbLocationLabel = new JLabel(DB_LOCATION_LABEL);
        dbLocationLabel.setToolTipText(DB_LOCATION_TOOL_TIP);
        gridbag.setConstraints(dbLocationLabel, constraints);
        this.add(dbLocationLabel);
        // next-to-last in row
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(dataBaseFilePath, constraints);
        this.add(dataBaseFilePath);

        databaseFileLocator = new DatabaseFileLocator(dataBaseFilePath);
        browseButton.addActionListener(databaseFileLocator);
        browseButton.setToolTipText(BROWSE_DATABASE_BOTTON_TOOL_TIP);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(browseButton, constraints);
        this.add(browseButton);
        // set the dabase location path
        String dbFilePath =
            config.getParameter(HotelConfiguration.DATABASE_LOCATION);
        setDatabaseFilePathText(dbFilePath);
        logger.info("DatabasesLocationRow has been created. The value of dbFilePath is :"
            + dbFilePath);
    }

    /**
     * save the parameters of the gui to a file
     */
    public void saveParameters() {
        logger.info("saving parameters to propertyfile \n"
            + "the followning parameters will be savend \n"
            + "DATABASE_LOCATION " + getDatabaseFilePathText()
            + "\n  SERVER_ADDRESS" + getHostNameText() + "\nSERVER_PORT "
            + getPortNumberText());

        if (mode.needsLocalDatabaseFile()) {
            logger.info("saving parameter to propertyfile \n"
                + "the followning parameter will be savend \n"
                + "DATABASE_LOCATION " + getDatabaseFilePathText());
            config.setParameter(
                    HotelConfiguration.DATABASE_LOCATION,
                    getDatabaseFilePathText());
        }
        if (mode.needsHostName()) {
            config.setParameter(
                    HotelConfiguration.SERVER_ADDRESS,
                    getHostNameText());
            logger.info("saving parameter to propertyfile \n"
                + "the followning parameter will be savend \n"
                + "SERVER_ADDRESS" + getHostNameText());
        }
        if (mode.needsPortNumber()) {
            logger.info("saving parameter to propertyfile \n"
                + "the followning parameters will be savend  \n"
                + "SERVER_PORT " + getPortNumberText());
            config.setParameter(
                    HotelConfiguration.SERVER_PORT,
                    getPortNumberText());
        }
    }

    /**
     * Configures whether the location field is enabled or not.
     * @param enabled true if the location field is enabled.
     */
    public void setLocationFieldEnabled(boolean enabled) {
        this.dataBaseFilePath.setEnabled(enabled);
    }

    /**
     * Configures whether the browse button is enabled or not.
     * @param enabled true if the browse button is enabled.
     */
    public void setBrowseButtonEnabled(boolean enabled) {
        this.browseButton.setEnabled(enabled);
    }

    /**
     * Configures whether the port number field is enabled or not.
     * @param enabled true if the port number field is enabled.
     */
    public void setPortNumberEnabled(boolean enabled) {
        this.portNumber.setEnabled(enabled);
    }

    /**
     * Returns the contents of the port number text field.
     * @return the contents of the port number text field.
     */
    public String getPortNumberText() {
        return portNumber.getText();
    }

    public void setPortNumberText(String parameter) {
        portNumber.setText(parameter);
    }

    public void setHostNameText(String parameter) {
        hostName.setText(parameter);
    }

    public String getHostNameText() {
        return hostName.getText();
    }

    public String getDatabaseFilePathText() {
        return dataBaseFilePath.getText();
    }

    public void setDatabaseFilePathText(String parameter) {
        dataBaseFilePath.setText(parameter);
    }

}
