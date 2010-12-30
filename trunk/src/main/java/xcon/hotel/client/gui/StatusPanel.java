package xcon.hotel.client.gui;

import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(StatusPanel.class.getName());
    
    
    private static JLabel commentLabel = new JLabel();
    public StatusPanel() {
        add(commentLabel);
        commentLabel.setText("book or search rooms");
        
    }
    
    public static void setCommentLabel(String text)
    {
        logger.info("setting commentlabel with following text:" + text);
        commentLabel.setText(text);
    }
}

