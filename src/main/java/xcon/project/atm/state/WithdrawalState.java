package xcon.project.atm.state;

import org.apache.log4j.Logger;

import xcon.project.atm.ATM;
import xcon.project.atm.AtmSession;
import xcon.project.atm.Screen;
import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.BankUpdateCompletedEvent;
import xcon.project.atm.event.KeyPadNumberEvent;
import xcon.project.atm.event.KeyPadOkEvent;
import xcon.project.atm.event.ScreenAmountChoiceEvent;
import xcon.project.atm.event.ScreenOtherEvent;

public class WithdrawalState extends AtmState {
    private static final Logger LOG = Logger.getLogger(WithdrawalState.class);
    public WithdrawalState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "select the amount<br> of money you want to <br>withdraw <br>";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        Screen screen = atm.getScreen();
        AtmSession session = atm.getSession();
        if (atmEvent instanceof KeyPadOkEvent) {

            screen.showInfoPanel();
            try {
                int withdrawalAmount = Integer.parseInt(session.amount);
                requestWithdrawal(withdrawalAmount);
            }
            catch (NumberFormatException e) {
                // do nothing
            }
        }
        else if (atmEvent instanceof KeyPadNumberEvent) {

            KeyPadNumberEvent kpnEvent = (KeyPadNumberEvent) atmEvent;
            if (screen.isShowingInfoPanel()) {

                session.amount = session.amount + kpnEvent.getNumber();
                screen.setMessage(session.state.getStatusMessage());
                screen.addLine(session.amount);
                LOG.debug("amount" + session.amount);
            }
        }
        else if (atmEvent instanceof ScreenAmountChoiceEvent) {

            ScreenAmountChoiceEvent sacEvent =
                (ScreenAmountChoiceEvent) atmEvent;
            int number = sacEvent.getMoneyAmount();
            requestWithdrawal(number);
            screen.showInfoPanel();
        }
        else if (atmEvent instanceof ScreenOtherEvent) {

            LOG.debug("ScreenOtherEvent");
            screen.showInfoPanel();
        }
        else if (atmEvent instanceof BankUpdateCompletedEvent) {
            screen.setMessage("Your withdrawal is complete");
            double balance =
                atm.getBankDatabase().balanceInquiry(atm.getSession().account);
            screen.addLine("the balance is " + balance);
            screen.addLine("please take your card");
            toEndState();
        }
    }

    public void requestWithdrawal(int amount) {

        LOG.debug("amount" + amount);
        Screen screen = atm.getScreen();
        AtmSession session = atm.getSession();
        {
            if (!(amount % 10 == 0)) {
                screen.setMessage("the amount must be <br> dividible by 10 <br> "
                    + "please enter another amount");
                session.amount = "";
            }
            else if ((amount < session.account.getBalance())) {

                LOG.debug("your have enough money");
                screen.setMessage("please wait while <br> processing your withdrawal..");
                atm.getBankDatabase().updateBalance(-amount, session.account);
            }
            else {
                screen.setMessage("You do not have <br> sufficient money please <br>"
                    + "take your card");
                session.amount = "";
                toEndState();
            }
        }
    }
}
