package xcon.hotel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import xcon.hotel.client.gui.DatabaseFileLocator;

public class HotelConfigWindowPanel extends JPanel {

    public JTextField getDataBaseLocationField() {
        return dataBaseFilePath;
    }

    public void setDataBaseLocationField(JTextField dataBaseLocationField) {
        this.dataBaseFilePath = dataBaseLocationField;
    }

    private static final long serialVersionUID = 1L;
    private static final String DB_LOCATION_LABEL = "Database location: ";
    private static final String SERVER_PORT_LABEL = "Server port: ";
    private static final String SERVER_LOCATION_LABEL = "Server location: ";
    private static final String SERVER_PORT_TOOL_TIP =
        "The port number the Server uses to listens for requests";
    private static final String SERVER_LOCATION_TOOL_TIP =
        "The address of the server used for Networed mode";

    File hotelDBFile;

    private JTextField dataBaseFilePath = new JTextField(40);
    private JButton browseButton = new JButton("...");
    private JTextField portNumber = new JTextField(5);
    private JTextField hostName = new JTextField(25);

    DatabaseFileLocator databaseFileLocator;

    public HotelConfigWindowPanel(ApplicationMode mode) {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(gridbag);
        // ensure there is always a gap between components
        constraints.insets = new Insets(2, 2, 2, 2);

        // Build the Data file location row if the mode is server of alone

        if (mode == ApplicationMode.SERVER || mode == ApplicationMode.ALONE) {

            // TODO: refactor as a method
            JLabel dbLocationLabel = new JLabel(DB_LOCATION_LABEL);
            gridbag.setConstraints(dbLocationLabel, constraints);
            this.add(dbLocationLabel);
            // next-to-last in row
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            gridbag.setConstraints(dataBaseFilePath, constraints);
            this.add(dataBaseFilePath);

            databaseFileLocator = new DatabaseFileLocator(dataBaseFilePath);
            browseButton.addActionListener(databaseFileLocator);
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            gridbag.setConstraints(browseButton, constraints);
            this.add(browseButton);
        }

        // build hostname for the networked mode
        constraints.weightx = 0.0;

        // build portnumber for server or networked mode
        if (mode == ApplicationMode.SERVER || mode == ApplicationMode.NETWORKED)
        {

            if (mode == ApplicationMode.NETWORKED) {

                // TODO: refactor as a method
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
            }
            // TODO: refactor as a method
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
     * Returns the contents of the port number text field.
     * @return the contents of the port number text field.
     */

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

    /**
     * Returns the contents of the database location field.
     * @return the contents of the database location field.
     */

    public File getHotelDBFile() {
        return databaseFileLocator.getFileLocationFromUser();
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
