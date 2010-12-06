package xcon.hotel.client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.client.gui.screen.HotelRoomScreen;
import xcon.hotel.client.gui.screen.QuitApplication;

public class SwingGui extends JFrame {

    private static final long serialVersionUID = 5165L;

    private Logger logger = Logger.getLogger(SwingGui.class.getName());
    private ResourceBundle messages;
    private HotelTableModel hotelTableModel;

    // HOME_WORK :
    //

    public SwingGui(Controller controller) {

        logger.fine("getting messages from resourceBundle");
        messages =
            ResourceBundle.getBundle("hotel_messages", Locale.getDefault());

        logger.fine("setting the title of the hotelapplication to: "
            + messages.getString("gui.frame.application.name"));
        setTitle(messages.getString("gui.frame.application.name"));

        logger.fine("getting hotel properties  from resourceBundle");

        setDefaultCloseOperation(SwingGui.EXIT_ON_CLOSE);

        // initialize status bar so it shows up

        // initialize the TableModel
        hotelTableModel =
            new HotelTableModel(controller.getColumnNames(), messages);
        // 1: search for all rooms

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

        add(new HotelRoomScreen(controller, hotelTableModel, messages));

        pack();
        setSize(750, 300);

        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - getWidth()) / 2);
        int y = (int) ((d.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setVisible(true);

    }

}