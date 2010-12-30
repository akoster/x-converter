package xcon.hotel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import xcon.hotel.db.DBAccess;
import xcon.hotel.db.Data;
import xcon.hotel.db.DbAccesssInitializationException;
import xcon.hotel.db.network.DbAccessNetworkClientImpl;

/**
 * HotelMainWindow is a jfrmae which contain the following components
 * HotelConfigWindowPanel, a connect and stop button and a label for showing
 * information.
 */
public class StartUpWindow extends JFrame {

    private static final long serialVersionUID = 355L;
    private Logger logger = Logger.getLogger(StartUpWindow.class.getName());
    private static final String CONNECT_BUTTON_TEXT = "Connect";
    private static final String EXIT_BUTTON_TEXT = "Exit";
    private static final String EXIT_BUTTON_TOOL_TIP =
        "Stops the server as soon as it is safe to do so";
    private static final String INITIAL_STATUS =
        "Enter configuration parameters and click \"" + CONNECT_BUTTON_TEXT
            + "\"";

    private ConfigPanel configPanel;
    private JButton connectButton = new JButton(CONNECT_BUTTON_TEXT);
    private JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
    private JLabel status = new JLabel();

    private HotelApplication application;

    public StartUpWindow(HotelApplication application) {

        logger.info("initializing HotelMainWindow with mode"
            + application.getMode());
        this.application = application;
        configPanel = new ConfigPanel(application.getMode());
        this.add(configPanel, BorderLayout.NORTH);
        this.add(createCommandOptionsPanel(), BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());

        statusPanel.add(status, BorderLayout.CENTER);
        status.setText("hotel application is using:" + application.getMode()
            + " mode. " + INITIAL_STATUS);
        status.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);

        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);

            }
        });

        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
        fileMenu.add(quitMenuItem);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setSize(650, 300);

        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - this.getWidth()) / 2);
        int y = (int) ((d.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }

    private JPanel createCommandOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        connectButton.addActionListener(new ConnectAction());
        connectButton.setEnabled(true);
        panel.add(connectButton);
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        exitButton.setToolTipText(EXIT_BUTTON_TOOL_TIP);
        panel.add(exitButton);
        return panel;
    }

    private class ConnectAction implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            DBAccess dbAccess = null;

            if (application.getMode().needsLocalDatabaseFile()) {
                String databaseFilePathText =
                    configPanel.getDatabaseFilePathText();
                if (databaseFilePathText == null || databaseFilePathText == "")
                {
                    status.setText("Please specify the database file");
                    return;
                }
                File hotelDBFile = new File(databaseFilePathText);
                if (!hotelDBFile.exists()) {
                    status.setText("Please specify an EXISTING database file");
                    return;
                }
                try {
                    dbAccess = new Data(hotelDBFile);
                }
                catch (DbAccesssInitializationException e) {

                    logger.log(Level.SEVERE, "error", e);
                }

            }
            else if (application.getMode() == ApplicationMode.NETWORKED) {

                dbAccess = new DbAccessNetworkClientImpl();
            }
            else {
                logger.severe("Could not handle application mode "
                    + application.getMode());
                return;
            }

            // disable fields (window cannot be edited after connect)
            configPanel.setLocationFieldEnabled(false);
            configPanel.setPortNumberEnabled(false);
            configPanel.setBrowseButtonEnabled(false);
            connectButton.setEnabled(false);

            // the user has now entered connect. The parameters will be saved to
            // a file.
            configPanel.saveParameters();

            // call back to the application with the initialized dbAccess
            application.dbAccessInitialized(dbAccess);

        }
    }
}
