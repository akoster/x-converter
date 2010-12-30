package xcon.hotel.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import xcon.hotel.HotelConfiguration;

public class DatabaseFileLocator implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static Logger logger =
        Logger.getLogger(DatabaseFileLocator.class.getName());
    private JTextField locationField;

    public DatabaseFileLocator(JTextField locationField) {
        this.locationField = locationField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(HotelConfiguration.BASE_DIRECTORY, ""));
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            locationField.setText(fc.getSelectedFile().getAbsolutePath());
        }
        else {
            logger.info("Operation cancelled by user");
        }
    }
}
