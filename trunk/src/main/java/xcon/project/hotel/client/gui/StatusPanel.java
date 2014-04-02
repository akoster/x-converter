package xcon.project.hotel.client.gui;

import java.awt.Color;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(StatusPanel.class.getName());
    
    
    private static JLabel commentLabel = new JLabel();
    public StatusPanel(ResourceBundle messages) {
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(Color.cyan);
        add(commentLabel);
        commentLabel.setText(messages.getString("info.comment.label.initial"));
        
    }
    
    public static void setCommentLabel(String text)
    {
        logger.info("setting commentlabel with following text:" + text);
        commentLabel.setText(text);
    }
}

