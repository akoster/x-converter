package xcon.project.atm.state;

import org.apache.log4j.Logger;

import xcon.project.atm.ATM;
import xcon.project.atm.Screen;
import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.KeyPadNumberEvent;

public class UserChoicesState extends AtmState {

    private static final Logger LOG = Logger.getLogger(UserChoicesState.class);
    
    public UserChoicesState(ATM atm) {
        super(atm);
    }

    @Override
    public String getStatusMessage() {
        return "choose one operation<br> 1:view balace<br> "
            + "2:withdrawal<br> 3:deposit";
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        LOG.debug("in handleAtmEvent");
        Screen screen = atm.getScreen();
        if (atmEvent instanceof KeyPadNumberEvent) {

            KeyPadNumberEvent kpnEvent = (KeyPadNumberEvent) atmEvent;
            int number = kpnEvent.getNumber();
            if (number == 1) {

                LOG.debug("User choice: view balance");
                toStateSuccess();
            }
            if (number == 2) {

                LOG.debug("User choice: withdrawal");
                toStateTwo();
                screen.showMoneyPanel();
            }
            if (number == 3) {

                LOG.debug("User choice: deposit");
                toStateThree();
            }
        }

    }
}
