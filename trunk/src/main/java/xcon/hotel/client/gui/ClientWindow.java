package xcon.hotel.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import xcon.hotel.client.Controller;

public class ClientWindow extends JFrame {

    private static final long serialVersionUID = 5165L;

    private Logger logger = Logger.getLogger(ClientWindow.class.getName());
    private ResourceBundle messages;

    // HOME_WORK :
    //

    public ClientWindow(Controller controller) {

        logger.info("getting messages from resourceBundle");
        messages =
            ResourceBundle.getBundle("hotel_messages", Locale.getDefault());

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("hotel.properties"));
        }
        catch (IOException e) {}

        int pageSize = Integer.parseInt(properties.getProperty("pageSize"));
        logger.info("setting the title of the hotelapplication to: "
            + messages.getString("gui.frame.application.name"));
        setTitle(messages.getString("gui.frame.application.name"));

        logger.fine("getting hotel properties  from resourceBundle");

        setDefaultCloseOperation(ClientWindow.EXIT_ON_CLOSE);

        // initialize status bar so it shows up

        // SwingGuiOutput swingGuiOutput = new
        // SwingGuiOutput(controller,messages);
        // initialize the TableModel
        /*
         * hotelTableModel = new HotelTableModel(controller.getColumnNames(),
         * messages); // 1: search for all rooms
         */
        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        logger.fine("menubar has been created");
        // add
        JMenu fileMenu = new JMenu(messages.getString("gui.jmenu.file.name"));
        logger.fine("fileMenu has been created");

        JMenuItem quitMenuItem =
            new JMenuItem(messages.getString("gui.jmenu.item.quit"));
        logger.fine("quitMenuItem has been created");

        quitMenuItem.addActionListener(new QuitApplication());
        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
        fileMenu.add(quitMenuItem);
        logger.fine("quitMenuItem has been added to the fileMenu");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        TablePanel tablePanel =
            new TablePanel(controller, messages, properties, pageSize);

        //  the actionPanel needs the tabelmodel. The class wil supply the
        // tabelmodel
        // to the actionPanel class

        ActionPanel actionPanel =
            new ActionPanel(controller, messages, properties,
                tablePanel.getHotelTable(), pageSize);
        // actionPanel.setBorder(BorderFactory.createEtchedBorder());

        StatusPanel statusPanel = new StatusPanel();
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.setBackground(Color.cyan);
        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(actionPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setSize(750, 600);

        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - getWidth()) / 2);
        int y = (int) ((d.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setVisible(true);

    }
}