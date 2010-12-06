package xcon.hotel.client.gui.screen;

import javax.swing.JButton;


public class NavigationButton extends JButton {

    private int pageNumber;
    private static final long serialVersionUID = 2L;

    public NavigationButton(int pageNumber) {
        super(String.valueOf(pageNumber));
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
