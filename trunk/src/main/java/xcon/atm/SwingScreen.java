package xcon.atm;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

import xcon.atm.event.AtmEventHandler;
import xcon.atm.event.ScreenAmountChoiceEvent;
import xcon.atm.event.ScreenEvent;
import xcon.atm.event.ScreenOtherEvent;

public class SwingScreen extends JPanel implements ActionListener, Screen {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(SwingScreen.class);
    
    private static final String MONEY_PANEL = "moneyPanel";
    private JPanel moneyChoicePanel;
    protected JButton[] moneyChoiceButtons;

    private static final String INFO_PANEL = "infoPanel";
    private JPanel infoPanel;
    private JLabel infoLabel;

    private CardLayout layout;
    private boolean isShowingInfoPanel;

    private List<AtmEventHandler> handlers;

    /* @see xcon.atm.swing.IScreen#getJComponent() */
    public JComponent getJComponent() {
        return (JComponent) this;
    }
    
    public SwingScreen() {

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

    /* @see xcon.atm.swing.IScreen#showInfoPanel() */
    public void showInfoPanel() {
        LOG.debug("showInfoPanel");
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, INFO_PANEL);
        isShowingInfoPanel = true;
    }

    /* @see xcon.atm.swing.IScreen#showMoneyPanel() */
    public void showMoneyPanel() {
        LOG.debug("showMoneyPanel");
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, MONEY_PANEL);
        isShowingInfoPanel = false;
    }

    /* @see xcon.atm.swing.IScreen#isShowingInfoPanel() */
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

    /* @see xcon.atm.swing.IScreen#setMessage(java.lang.String) */
    public void setMessage(String message) {
        infoLabel.setText("<html>" + message);
    }

    /* @see xcon.atm.swing.IScreen#addLine(java.lang.String) */
    public void addLine(String message) {
        infoLabel.setText(infoLabel.getText() + "<br>" + message);
    }

    /* @see xcon.atm.swing.IScreen#addEventHandler(xcon.atm.swing.event.AtmEventHandler) */
    public void addEventHandler(AtmEventHandler handler) {
        this.handlers.add(handler);
    }
}
