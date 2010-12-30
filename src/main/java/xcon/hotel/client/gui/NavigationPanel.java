package xcon.hotel.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import xcon.hotel.client.Controller;
import xcon.hotel.client.HotelTableModel;
import xcon.hotel.db.ControllerException;
import xcon.hotel.model.SearchResult;

public class NavigationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(NavigationPanel.class.getName());

    private Controller controller;
    private int currentPage = 1;
    private JTable hotelTable;
    private ResourceBundle messages;
    private Properties hotelRoomProperties;
    private int pageSize;

    public NavigationPanel(Controller controller,
                           JTable hotelTable,
                           ResourceBundle messages,
                           Properties properties,
                           int pageSize)
    {

        this.controller = controller;
        this.hotelTable = hotelTable;
        this.messages = messages;
        this.hotelRoomProperties = properties;
        this.pageSize = pageSize;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = createBackButton();
        JButton nextButton = createNextButton();
        add(backButton);
        add(nextButton);

    }

    private JButton createNextButton() {

        JButton nextButton =
            new JButton(new ImageIcon(this.getClass().getResource("/next.gif")));

        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("next button has been clickedcurrent page is: " + currentPage );
                int numberOfPages = Integer.parseInt(hotelRoomProperties.getProperty("NumberOfPagesToShow"));
                if (currentPage < numberOfPages) {
                    displayPage(currentPage + 1);
                }
            }
        });
        return nextButton;
    }

    private JButton createBackButton() {
        logger.info("creating a backButton");
        JButton backButton =
            new JButton(new ImageIcon(this.getClass().getResource("/back.gif")));

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                logger.info("backbutton has been clicked, current page is: " + currentPage);
                
                if (currentPage > 1) {
                    displayPage(currentPage - 1);
                }
            }
        });
        return backButton;
    }

    private void displayPage(int pageNumber) {

        logger.info("displaying room for page: " + pageNumber);
        currentPage = pageNumber;
        String hotelName = hotelRoomProperties.getProperty("hotelName");
        String hotelLocation =
            hotelRoomProperties.getProperty("hotelLocation");
        
        
        try {
            SearchResult result =
                controller.search(
                        hotelName,
                        hotelLocation,
                        currentPage,
                        pageSize);

            ((HotelTableModel) hotelTable.getModel()).setHotelrooms(result.getRooms());
            ((HotelTableModel) hotelTable.getModel()).fireTableDataChanged();
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
        }
    }
}
