package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.CardSlotEvent;

public class EndState extends AtmState {

    public EndState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "Stopping the session <br> Please take your card";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        if (atmEvent instanceof CardSlotEvent) {

            atm.getSlotPanel().setCardSlotStatus(true);
            atm.initSession();
            toStateSuccess();
        }

    }
}
