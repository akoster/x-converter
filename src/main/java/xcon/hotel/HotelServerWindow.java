package xcon.hotel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import xcon.hotel.db.Data;
import xcon.hotel.db.DbAccesssInitializationException;

public class HotelServerWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static Logger logger =
        Logger.getLogger(HotelServerWindow.class.getName());

    private static final String HOTEL_APPLICATION_NAME = "Hotel Application";
    private static final String START_BUTTON_TEXT = "Start server";
    private static final String EXIT_BUTTON_TEXT = "Exit";
    private static final String EXIT_BUTTON_TOOL_TIP = "Stops the server";
    private static final String INITIAL_STATUS =
        "Enter configuration parameters and click \"" + START_BUTTON_TEXT
            + "\"";

    private HotelConfigWindowPanel hotelConfigWindowPanel =
        new HotelConfigWindowPanel(ApplicationMode.SERVER);
    private JButton startServerButton = new JButton(START_BUTTON_TEXT);
    private JButton exitButton = new JButton(EXIT_BUTTON_TEXT);
    private JLabel status = new JLabel();

    HotelConfiguration config = HotelConfiguration.getSavedConfiguration();
    private HotelApplication application;

    public HotelServerWindow(HotelApplication application) {

        super(HOTEL_APPLICATION_NAME);
        this.application = application;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 300);
        this.setResizable(false);
        this.setVisible(true);

        // Add the menu bar
        JMenu fileMenu = new JMenu("file");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        fileMenu.add(quitMenuItem);

        quitMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        this.add(hotelConfigWindowPanel, BorderLayout.NORTH);
        this.add(createCommandOptionsPanel(), BorderLayout.CENTER);

        status.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(status, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);
        status.setText(INITIAL_STATUS);

        hotelConfigWindowPanel.setPortNumberText(config.getParameter(HotelConfiguration.SERVER_PORT));

        hotelConfigWindowPanel.setDatabaseFilePathText(config.getParameter(HotelConfiguration.DATABASE_LOCATION));
    }

    private JPanel createCommandOptionsPanel() {
        JPanel thePanel = new JPanel();
        thePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        startServerButton.addActionListener(new StartServer());
        startServerButton.setEnabled(true);
        thePanel.add(startServerButton);

        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        exitButton.setToolTipText(EXIT_BUTTON_TOOL_TIP);
        thePanel.add(exitButton);
        return thePanel;
    }

    private class StartServer implements ActionListener {

        /** {@inheritDoc} */
        public void actionPerformed(ActionEvent ae) {
            hotelConfigWindowPanel.setLocationFieldEnabled(false);
            hotelConfigWindowPanel.setPortNumberEnabled(false);
            hotelConfigWindowPanel.setBrowseButtonEnabled(false);
            startServerButton.setEnabled(false);

            config.setParameter(
                    HotelConfiguration.SERVER_PORT,
                    hotelConfigWindowPanel.getPortNumberText());

            config.setParameter(
                    HotelConfiguration.DATABASE_LOCATION,
                    hotelConfigWindowPanel.getDatabaseFilePathText());

            try {
                File hotelDBFile = hotelConfigWindowPanel.getHotelDBFile();
                if (hotelDBFile == null) {
                    hotelDBFile =
                        new File(
                            hotelConfigWindowPanel.getDatabaseFilePathText());
                }
                application.dbAccessInitialized(new Data(hotelDBFile));
            }
            catch (DbAccesssInitializationException e) {
                logger.log(Level.SEVERE, "foutje", e);
            }
        }
    }
}
