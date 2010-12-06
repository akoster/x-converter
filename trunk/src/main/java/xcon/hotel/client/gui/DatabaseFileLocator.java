package xcon.hotel.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class DatabaseFileLocator  implements ActionListener{

    private static final long serialVersionUID = 1L;
    private static Logger logger =
        Logger.getLogger(DatabaseFileLocator.class.getName());
    private String previousPath;
    File selectedFile;
    
    private JTextField locationField ;

    public DatabaseFileLocator(JTextField locationField) {

        this.locationField = locationField ;
        
    }

    public File getFileLocationFromUser() {
        return selectedFile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        previousPath = "D:\\hotel.db";
        
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(previousPath));
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            selectedFile = fc.getSelectedFile();
            locationField.setText(selectedFile.getAbsolutePath());
            // TODO: save selected file path to suncertify.properties
        }
        else {
            logger.info("Operation cancelled by user");
            selectedFile = null;
        }        
    }
}
