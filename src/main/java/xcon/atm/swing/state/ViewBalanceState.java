package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.KeyPadOkEvent;

public class ViewBalanceState extends AtmState {

    public ViewBalanceState(ATM atm) {
        super(atm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getStatusMessage() {

        return "your balance is <br>";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        if (atmEvent instanceof KeyPadOkEvent) {
            toStateSuccess();
        }
    }

}
