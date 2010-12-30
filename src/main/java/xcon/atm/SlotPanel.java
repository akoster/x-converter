package xcon.atm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

import xcon.atm.event.AtmEventHandler;
import xcon.atm.event.CardSlotEvent;
import xcon.atm.event.MoneySlotEvent;
import xcon.atm.event.SlotPanelEvent;

public class SlotPanel extends JPanel implements ActionListener {

    private static final Logger LOG = Logger.getLogger(SlotPanel.class);

    
    private static final Color SLOT_OPEN_COLOR = Color.BLUE;
    private static final Color SLOT_CLOSED_COLOR = Color.RED;
    private static final long serialVersionUID = 1L;

    JButton moneySlotButton;
    JButton cardSlotButton;
    AtmEventHandler atmEventHandler ;
    
    public SlotPanel() { // XXX add parent variable
        
        setLayout(new GridLayout(2, 4));
        for (int i = 0; i <= 5; i++) {
            add(new JLabel(""));
        }

        moneySlotButton = new JButton("moneySlotButton");
        moneySlotButton.addActionListener(this);
        moneySlotButton.setBackground(SLOT_CLOSED_COLOR);
        cardSlotButton = new JButton("cardSlotButton");
        cardSlotButton.addActionListener(this);
        cardSlotButton.setBackground(SLOT_OPEN_COLOR);
        add(moneySlotButton);
        add(cardSlotButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        
        SlotPanelEvent slotPanelEvent = null ; 
        if (event.getActionCommand().equals("moneySlotButton")) {
            LOG.debug("moneySlotButton");
            slotPanelEvent = new MoneySlotEvent(false);
            
        }

        if (event.getActionCommand().equals("cardSlotButton")) {
            LOG.debug("cardSlotButton");
            slotPanelEvent = new CardSlotEvent(true);
        }
        atmEventHandler.handleAtmEvent(slotPanelEvent);

    }

    public void setCardSlotStatus(boolean isOpen) {
        if (isOpen) {
            cardSlotButton.setBackground(SLOT_OPEN_COLOR);
        }
        else {
            cardSlotButton.setBackground(SLOT_CLOSED_COLOR);
        }
    }

    public void setMoneySlotStatus(boolean isOpen) {
        if (isOpen) {
            moneySlotButton.setBackground(SLOT_OPEN_COLOR);
        }
        else {
            moneySlotButton.setBackground(SLOT_CLOSED_COLOR);
        }
    }
    
    public void addEventHandler(AtmEventHandler atmEventHandler)
    {
        this.atmEventHandler = atmEventHandler ;
    }
    

}
