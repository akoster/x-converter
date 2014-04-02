package xcon.project.atm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

import xcon.project.atm.event.AtmEventHandler;
import xcon.project.atm.event.KeyPadEvent;
import xcon.project.atm.event.KeyPadNumberEvent;
import xcon.project.atm.event.KeyPadOkEvent;
import xcon.project.atm.event.KeyPadStopEvent;

/**
 * entering digits
 * @author loudiyimo
 */
public class KeyPad extends JPanel implements ActionListener {

    private static final Logger LOG = Logger.getLogger(KeyPad.class);
    private static final long serialVersionUID = 1L;
    private static final String BUTTON_TEXT_STOP = "stop";
    private static final String BUTTON_TEXT_OK = "ok";
    private JButton[] numberButtons;
    private AtmEventHandler atmEventHandler;

    public KeyPad() {

        numberButtons = new JButton[12];
        setLayout(new GridLayout(4, 3, 2, 2));
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new KeyPadNumberButton(String.valueOf(i), i);
            add(numberButtons[i]);
        }
        numberButtons[0] = new KeyPadNumberButton("0", 0);
        numberButtons[10] = new JButton(BUTTON_TEXT_OK);
        numberButtons[11] = new JButton(BUTTON_TEXT_STOP);

        add(numberButtons[0]);
        add(numberButtons[10]);
        add(numberButtons[11]);

        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i].addActionListener(this);
        }
    }

    private class KeyPadNumberButton extends JButton {

        private static final long serialVersionUID = 1L;
        private int number;

        KeyPadNumberButton(String text, int number) {
            super(text);
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        KeyPadEvent kpEvent = null;

        Object source = event.getSource();
        if (source instanceof KeyPadNumberButton) {

            KeyPadNumberButton kpnButton = (KeyPadNumberButton) source;
            kpEvent = new KeyPadNumberEvent(kpnButton.getNumber());
        }
        else if (source instanceof JButton) {
            JButton jButton = (JButton) source;
            if (BUTTON_TEXT_OK.equals(jButton.getText())) {
                kpEvent = new KeyPadOkEvent();
            }
            else if (BUTTON_TEXT_STOP.equals(jButton.getText())) {
                kpEvent = new KeyPadStopEvent();
            }
        }

        if (kpEvent == null) {
            LOG.debug("Unrecognized source: " + source);
        }
        else {
            atmEventHandler.handleAtmEvent(kpEvent);
        }
    }

    public void addEventHandler(AtmEventHandler atmEventHandler) {
        this.atmEventHandler = atmEventHandler;
    }

}
