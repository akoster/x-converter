package xcon.project.hotel.client.gui;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import xcon.project.hotel.client.Controller;
import xcon.project.hotel.client.HotelTableModel;
import xcon.project.hotel.db.SwingGuiException;

public class ClientWindow extends JFrame {

    private static final long serialVersionUID = 5165L;

    private Logger logger = Logger.getLogger(ClientWindow.class.getName());

    public ClientWindow(Controller controller) throws SwingGuiException {

        ResourceBundle messages =
            ResourceBundle.getBundle("hotel_messages", Locale.getDefault());
        setLayout(new BorderLayout());
        setTitle(messages.getString("gui.frame.application.name"));
        setDefaultCloseOperation(ClientWindow.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(messages));
        
        HotelTableModel hotelTableModel =
            new HotelTableModel(controller.getColumnNames(), messages);

        TablePanel tablePanel =
            new TablePanel(controller, messages, hotelTableModel);
        add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel =
            createBottomPanel(controller, messages, hotelTableModel);
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

    private JMenuBar createMenuBar(ResourceBundle messages) {
        JMenuBar menuBar = new JMenuBar();
        logger.fine("menubar has been created");
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
        return menuBar;
    }

    private JPanel createBottomPanel(Controller controller,
                                     ResourceBundle messages,
                                     HotelTableModel hotelTableModel)
    {
        ActionPanel actionPanel =
            new ActionPanel(controller, messages, hotelTableModel);
        StatusPanel statusPanel = new StatusPanel(messages);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(actionPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);        
        return bottomPanel;
    }
}