package xcon.project.atm.state;

import xcon.project.atm.ATM;
import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.CardSlotEvent;

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
