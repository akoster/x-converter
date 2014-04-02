package xcon.project.hotel.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import xcon.project.hotel.client.Controller;
import xcon.project.hotel.client.HotelTableModel;
import xcon.project.hotel.db.ControllerException;

public class NavigationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(NavigationPanel.class.getName());

    private Controller controller;
    private HotelTableModel hotelTableModel;
    private ResourceBundle messages;

    public NavigationPanel(Controller controller,
                           ResourceBundle messages,
                           HotelTableModel hotelTableModel)
    {
        this.controller = controller;
        this.messages = messages;
        this.hotelTableModel = hotelTableModel;

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
                int currentPage =
                    hotelTableModel.getHotelRoomSearch().getPage();
                if (currentPage < calculateMaxPageNumber()) {
                    displayPage(currentPage + 1);
                }
            }
        });
        return nextButton;
    }

    private int calculateMaxPageNumber() {
        double totalRooms =
            hotelTableModel.getHotelRoomSearch().getTotalRooms();
        double pageSize = hotelTableModel.getHotelRoomSearch().getPageSize();
        return (int) Math.ceil(totalRooms / pageSize);
    }

    private JButton createBackButton() {

        JButton backButton =
            new JButton(new ImageIcon(this.getClass().getResource("/back.gif")));
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                int currentPage =
                    hotelTableModel.getHotelRoomSearch().getPage();
                if (currentPage > 1) {
                    displayPage(currentPage - 1);
                }
            }
        });
        return backButton;
    }

    private void displayPage(int newPageToDisplay) {

        logger.info("displaying room for page: " + newPageToDisplay);
        hotelTableModel.getHotelRoomSearch().setPage(newPageToDisplay);

        try {
            controller.search(hotelTableModel.getHotelRoomSearch());
            hotelTableModel.fireTableDataChanged();
        }
        catch (ControllerException e) {
            StatusPanel.setCommentLabel(messages.getString(e.getMessageKey()));
        }
    }
}
