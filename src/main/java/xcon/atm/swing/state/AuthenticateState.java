package xcon.atm.swing.state;

import org.apache.log4j.Logger;
import xcon.atm.swing.ATM;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.KeyPadNumberEvent;
import xcon.atm.swing.event.KeyPadOkEvent;

public class AuthenticateState extends AtmState {
    private static final Logger LOG = Logger.getLogger(AuthenticateState.class);
    
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
        LOG.debug("in authenticate status ");

        // handle event
        if (atm.getSession().password.length() < 4) {

            atm.getSession().password += kpnEvent.getNumber();
            LOG.debug("password " + atm.getSession().password);
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
                    toEndState();
                }
                else {
                    atm.getScreen().setMessage(
                            "Wrong password,<br> please try again");
                }
            }
        }
    }

    public boolean authenticate(int pinNumber) {

        LOG.debug("authenticating the pin number");
        atm.getBankDatabase().getConnection();

        atm.getSession().account = atm.getBankDatabase().findAccount(pinNumber);
        if (atm.getSession().account != null) {

            LOG.debug("account is found");
            return true;
        }
        else {
            LOG.debug("wrong pincode");
            return false;
        }
    }

}
