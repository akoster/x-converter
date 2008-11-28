package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.CardSlotEvent;

public class StartState extends AtmState {

    public StartState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "welkom to the ml bank <br> enter your card please";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        if (atmEvent instanceof CardSlotEvent) {

            CardSlotEvent csEvent = (CardSlotEvent) atmEvent;
            if (csEvent.isOpen()) {

                atm.getSlotPanel().setCardSlotStatus(false);
                toStateSuccess();
            }
        }
    }
}
