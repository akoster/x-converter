package xcon.atm.swing;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import xcon.atm.swing.event.AtmEventHandler;
import xcon.atm.swing.event.ScreenAmountChoiceEvent;
import xcon.atm.swing.event.ScreenEvent;
import xcon.atm.swing.event.ScreenOtherEvent;

public class Screen extends JPanel implements ActionListener {

    private static final Logger LOG = Logger.getLogger(Screen.class);
    
    private static final String MONEY_PANEL = "moneyPanel";
    private JPanel moneyChoicePanel;
    protected JButton[] moneyChoiceButtons;

    private static final String INFO_PANEL = "infoPanel";
    private JPanel infoPanel;
    private JLabel infoLabel;

    private CardLayout layout;
    private boolean isShowingInfoPanel;

    private List<AtmEventHandler> handlers;

    public Screen() {

        handlers = new ArrayList<AtmEventHandler>();

        // this panel for showing the money the user can choose
        moneyChoicePanel = new JPanel();
        moneyChoicePanel.setLayout(new GridLayout(3, 2, 2, 2));
        moneyChoiceButtons = new JButton[6];

        moneyChoiceButtons[0] = new ScreenChoiceButton("10", 10);
        moneyChoiceButtons[1] = new ScreenChoiceButton("20", 20);
        moneyChoiceButtons[2] = new ScreenChoiceButton("50", 50);
        moneyChoiceButtons[3] = new ScreenChoiceButton("100", 100);
        moneyChoiceButtons[4] = new ScreenChoiceButton("250", 250);
        moneyChoiceButtons[5] = new ScreenOtherButton("overige");

        for (int i = 0; i < moneyChoiceButtons.length; i++) {
            moneyChoiceButtons[i].addActionListener(this);
            moneyChoicePanel.add(moneyChoiceButtons[i]);
        }
        // this panel is for showing information to the user ;
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoLabel = new JLabel();
        infoPanel.add(infoLabel);

        layout = new CardLayout();
        setLayout(layout);
        add(infoPanel, INFO_PANEL); // this is visible initially
        isShowingInfoPanel = true;
        add(moneyChoicePanel, MONEY_PANEL);
    }

    public void showInfoPanel() {
        LOG.debug("showInfoPanel");
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, INFO_PANEL);
        isShowingInfoPanel = true;
    }

    public void showMoneyPanel() {
        LOG.debug("showMoneyPanel");
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, MONEY_PANEL);
        isShowingInfoPanel = false;
    }

    public boolean isShowingInfoPanel() {
        return isShowingInfoPanel;
    }

    public class ScreenChoiceButton extends JButton {

        private int amount;
        private static final long serialVersionUID = 1L;

        public ScreenChoiceButton(String text, int amount) {
            super(text);
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

    public class ScreenOtherButton extends JButton {

        private static final long serialVersionUID = 1L;

        public ScreenOtherButton(String text) {
            super(text);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        LOG.debug("actionPerformed");
        Object source = event.getSource();
        ScreenEvent screenEvent = null;

        if (source instanceof ScreenChoiceButton) {
            ScreenChoiceButton screenChoiceButton = (ScreenChoiceButton) source;
            screenEvent =
                new ScreenAmountChoiceEvent(screenChoiceButton.getAmount());
        }
        else if (source instanceof ScreenOtherButton) {
            screenEvent = new ScreenOtherEvent();
        }

        // the Screen device can have multiple eventHandlers which should
        // ALL be notified when an event has occurred
        for (AtmEventHandler screenEventHandler : handlers) {
            screenEventHandler.handleAtmEvent(screenEvent);
        }
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(JLabel infoLabel) {
        this.infoLabel = infoLabel;
    }

    public String getMessage() {
        return infoLabel.getText();
    }

    public void setMessage(String message) {
        infoLabel.setText("<html>" + message);
    }

    public void addLine(String message) {
        infoLabel.setText(infoLabel.getText() + "<br>" + message);
    }

    public void addEventHandler(AtmEventHandler handler) {
        this.handlers.add(handler);
    }
}
