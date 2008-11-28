package xcon.atm.swing;

import org.apache.log4j.Logger;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.BankUpdateCompletedEvent;
import xcon.atm.swing.event.KeyPadNumberEvent;
import xcon.atm.swing.event.KeyPadOkEvent;
import xcon.atm.swing.state.AtmState;

public class DepositState extends AtmState {

    private static final Logger LOG = Logger.getLogger(DepositState.class);
    public DepositState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "enter the amount of money <br> you want to deposit";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        AtmSession session = atm.getSession();
        Screen screen = atm.getScreen();
        if (atmEvent instanceof KeyPadOkEvent) {
            try {
                int depositAmount = Integer.parseInt(session.amount);
                requestDeposit(depositAmount);
            }
            catch (NumberFormatException e) {
                // do nothing
            }
        }
        else if (atmEvent instanceof KeyPadNumberEvent) {

            KeyPadNumberEvent kpnEvent = (KeyPadNumberEvent) atmEvent;
            session.amount = session.amount + kpnEvent.getNumber();
            screen.setMessage(session.state.getStatusMessage());
            screen.addLine(session.amount);
            LOG.debug("amount" + session.amount);
        }
        else if (atmEvent instanceof BankUpdateCompletedEvent) {
            screen.setMessage("Your deposit is complete");
            double balance =
                atm.getBankDatabase().balanceInquiry(atm.getSession().account);
            screen.addLine("the balance is " + balance);
            screen.addLine("please take your card");
            toEndState();
        }
    }

    public void requestDeposit(int amount) {

        LOG.debug("requesting deposit");
        atm.getScreen().setMessage(
                "please wait while <br> processing your deposit..");
        atm.getBankDatabase().updateBalance(amount, atm.getSession().account);
    }
}
