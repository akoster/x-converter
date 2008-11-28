package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.KeyPadNumberEvent;
import xcon.atm.swing.event.KeyPadOkEvent;

public class AuthenticateState extends AtmState {

    public AuthenticateState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "enter your passWord";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {
        if (atmEvent instanceof KeyPadNumberEvent) {
            KeyPadNumberEvent kpnEvent = (KeyPadNumberEvent) atmEvent;
            enterPassword(kpnEvent);
        }
        else if (atmEvent instanceof KeyPadOkEvent) {
            validatePassword();
        }
    }

    private void enterPassword(KeyPadNumberEvent kpnEvent) {

        // check event type
        System.out.println("in authenticate status ");

        // handle event
        if (atm.getSession().password.length() < 4) {

            atm.getSession().password += kpnEvent.getNumber();
            System.out.println("password " + atm.getSession().password);
            atm.getSession().passwordHideString =
                atm.getSession().passwordHideString + "*";

            atm.getScreen().setMessage(getStatusMessage());
            atm.getScreen().addLine(atm.getSession().passwordHideString);
        }
        if (atm.getSession().password.length() == 4) {

            atm.getScreen().setMessage("please press ok");
            atm.getScreen().addLine(atm.getSession().passwordHideString);
        }
    }

    private void validatePassword() {

        if (atm.getSession().password.length() == 4) {

            int pinCode = Integer.parseInt(atm.getSession().password);
            if (authenticate(pinCode)) {
                toStateSuccess();
            }
            else {
                atm.getSession().password = "";
                atm.getSession().wrongPasswordCounter++;
                atm.getSession().passwordHideString = "";
                if (atm.getSession().wrongPasswordCounter >= 3) {
                    atm.getScreen().setMessage(
                            "you entered three <br> "
                                + "times the wrong password <br>"
                                + "please take your card");
                    toStateFailure();
                }
                else {
                    atm.getScreen().setMessage(
                            "Wrong password,<br> please try again");
                }
            }
        }
    }

    public boolean authenticate(int pinNumber) {

        System.out.println("authenticating the pin number");
        atm.getBankDatabase().getConnection();

        atm.getSession().account = atm.getBankDatabase().findAccount(pinNumber);
        if (atm.getSession().account != null) {

            System.out.println("account is found");
            return true;
        }
        else {
            System.out.println("wrong pincode");
            return false;
        }
    }

}
