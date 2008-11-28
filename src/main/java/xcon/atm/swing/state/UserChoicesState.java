package xcon.atm.swing.state;

import xcon.atm.swing.ATM;
import xcon.atm.swing.Screen;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.KeyPadNumberEvent;

public class UserChoicesState extends AtmState {

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

        System.out.println("in handleAtmEvent");
        Screen screen = atm.getScreen();
        if (atmEvent instanceof KeyPadNumberEvent) {

            KeyPadNumberEvent kpnEvent = (KeyPadNumberEvent) atmEvent;
            int number = kpnEvent.getNumber();
            if (number == 1) {

                System.out.println("User choice: view balance");
                toStateSuccess();
            }
            if (number == 2) {

                System.out.println("User choice: withdrawal");
                toStateTwo();
                screen.showMoneyPanel();
            }
            if (number == 3) {

                System.out.println("User choice: deposit");
                toStateThree();
            }
        }

    }
}
