package xcon.atm.swing.state;

import org.apache.log4j.Logger;
import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.KeyPadOkEvent;

public class ViewBalanceState extends AtmState {

    private static final Logger LOG = Logger.getLogger(ViewBalanceState.class);
    
    public ViewBalanceState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {

        double balance =
            atm.getBankDatabase().balanceInquiry(atm.getSession().account);
        return "your balance is <br>" + balance;
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        if (atmEvent instanceof KeyPadOkEvent) {
            toStateSuccess();
        }
    }

}
