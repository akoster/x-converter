package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.AtmSession;
import xcon.atm.swing.Screen;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.BankUpdateCompletedEvent;
import xcon.atm.swing.event.KeyPadNumberEvent;
import xcon.atm.swing.event.KeyPadOkEvent;
import xcon.atm.swing.event.ScreenAmountChoiceEvent;
import xcon.atm.swing.event.ScreenOtherEvent;

public class WithdrawalState extends AtmState {

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
                System.out.println("amount" + session.amount);
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

            System.out.println("ScreenOtherEvent");
            screen.showInfoPanel();
        }
        else if (atmEvent instanceof BankUpdateCompletedEvent) {
            screen.setMessage("Your withdrawal is complete");
            double balance =
                atm.getBankDatabase().balanceInquiry(atm.getSession().account);
            screen.addLine("the balance is " + balance);
            screen.addLine("please take your card");
            toStateSuccess();
        }
    }

    public void requestWithdrawal(int amount) {

        System.out.println("amount" + amount);
        Screen screen = atm.getScreen();
        AtmSession session = atm.getSession();
        {
            if (!(amount % 10 == 0)) {
                screen.setMessage("the amount must be <br> dividible by 10 <br> "
                    + "please enter another amount");
                session.amount = "";
            }
            else if ((amount < session.account.getBalance())) {

                System.out.println("your have enough money");
                screen.setMessage("please wait while <br> processing your withdrawal..");
                atm.getBankDatabase().updateBalance(-amount, session.account);
            }
            else {
                screen.setMessage("You do not have <br> sufficient money please <br>"
                    + "take your card");
                session.amount = "";
                toStateFailure();
            }
        }
    }
}
