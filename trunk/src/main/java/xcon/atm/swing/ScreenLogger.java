package xcon.atm.swing;

import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.AtmEventHandler;
import xcon.atm.swing.event.ScreenAmountChoiceEvent;
import xcon.atm.swing.event.ScreenOtherEvent;

public class ScreenLogger implements AtmEventHandler {


    @Override
    public void handleAtmEvent(AtmEvent screenEvent) {
        String msg = "ScreenEvent ";
        if (screenEvent instanceof ScreenAmountChoiceEvent) {
            ScreenAmountChoiceEvent screenAmountChoiceEvent =
                (ScreenAmountChoiceEvent) screenEvent;
            msg +=
                "selected moneyAmount="
                    + screenAmountChoiceEvent.getMoneyAmount();
        }
        else if (screenEvent instanceof ScreenOtherEvent) {
            msg += "other key pressed";
        }
        System.out.println(msg);
    }
}