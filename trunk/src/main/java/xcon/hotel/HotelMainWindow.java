package xcon.hotel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
 * components of the alone mode and the networked mode will be placed on this
 * class (jframe).
 */
public class HotelMainWindow extends JFrame {

    private static final long serialVersionUID = 355L;
    private Logger logger = Logger.getLogger(HotelMainWindow.class.getName());
    private static final String CONNECT_BUTTON_TEXT = "Connect";
    private static final String EXIT_BUTTON_TEXT = "Exit";
    private static final String EXIT_BUTTON_TOOL_TIP =
        "Stops the server as soon as it is safe to do so";
    private static final String INITIAL_STATUS =
        "Enter configuration parameters and click \"" + CONNECT_BUTTON_TEXT
            + "\"";

    HotelConfigWindowPanel hotelConfigWindowPanel;
    private JButton connectButton = new JButton(CONNECT_BUTTON_TEXT);
    private JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
    private JLabel status = new JLabel();

    private HotelConfiguration config =
        HotelConfiguration.getSavedConfiguration();
    private HotelApplication application;;

    public HotelMainWindow(HotelApplication application) {

        this.application = application;

        logger.info("initializing HotelMainWindow with mode"
            + application.getMode());

        hotelConfigWindowPanel =
            new HotelConfigWindowPanel(application.getMode());
        this.add(hotelConfigWindowPanel, BorderLayout.NORTH);
        this.add(createCommandOptionsPanel(), BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(status, BorderLayout.CENTER);
        status.setText(INITIAL_STATUS);
        status.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);

        if (application.getMode() == ApplicationMode.ALONE) {
            hotelConfigWindowPanel.setDatabaseFilePathText(config.getParameter(HotelConfiguration.DATABASE_LOCATION));

        }
        // the mode is not alone, so the mode is NETWORKED
        else {
            hotelConfigWindowPanel.setHostNameText(config.getParameter(HotelConfiguration.SERVER_ADDRESS));
            hotelConfigWindowPanel.setPortNumberText(config.getParameter(HotelConfiguration.SERVER_PORT));
        }

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
        connectButton.addActionListener(new ConnectServer());
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

    private class ConnectServer implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            hotelConfigWindowPanel.setLocationFieldEnabled(false);
            hotelConfigWindowPanel.setPortNumberEnabled(false);
            hotelConfigWindowPanel.setBrowseButtonEnabled(false);
            connectButton.setEnabled(false);

            // both networked mode en alone mode have a database locationField.
            // That's why we need this check.

            // TODO remove if statement and move config object to
            // hotelConfigWindowPanel
            config.setParameter(
                    HotelConfiguration.DATABASE_LOCATION,
                    hotelConfigWindowPanel.getDatabaseFilePathText());

            DBAccess dbAccess;
            if (application.getMode() == ApplicationMode.ALONE) {

                config.setParameter(
                        HotelConfiguration.DATABASE_LOCATION,
                        hotelConfigWindowPanel.getDatabaseFilePathText());
                try {
                    dbAccess =
                        new Data(hotelConfigWindowPanel.getHotelDBFile());
                }
                catch (DbAccesssInitializationException e) {
                    logger.severe(e.getMessage());
                    return;
                }
            }
            else if (application.getMode() == ApplicationMode.NETWORKED) {
                config.setParameter(
                        HotelConfiguration.SERVER_ADDRESS,
                        hotelConfigWindowPanel.getHostNameText());
                config.setParameter(
                        HotelConfiguration.SERVER_PORT,
                        hotelConfigWindowPanel.getPortNumberText());
                dbAccess = new DbAccessNetworkClientImpl();
            }
            else {
                logger.severe("Could not handle application mode "
                    + application.getMode());
                return;
            }

            application.dbAccessInitialized(dbAccess);

        }
    }
}
