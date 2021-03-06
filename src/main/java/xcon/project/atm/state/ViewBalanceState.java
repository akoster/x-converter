package xcon.project.atm.state;

import xcon.project.atm.ATM;
import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.KeyPadOkEvent;

public class ViewBalanceState extends AtmState {

//    private static final Logger LOG = Logger.getLogger(ViewBalanceState.class);
    
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
