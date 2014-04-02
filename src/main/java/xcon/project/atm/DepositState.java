package xcon.project.atm;

import org.apache.log4j.Logger;

import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.BankUpdateCompletedEvent;
import xcon.project.atm.event.KeyPadNumberEvent;
import xcon.project.atm.event.KeyPadOkEvent;
import xcon.project.atm.state.AtmState;

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
