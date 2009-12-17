package xcon.atm;

import org.apache.log4j.Logger;

import xcon.atm.event.AtmEvent;
import xcon.atm.event.AtmEventHandler;
import xcon.atm.event.ScreenAmountChoiceEvent;
import xcon.atm.event.ScreenOtherEvent;

public class ScreenLogger implements AtmEventHandler {

    private static final Logger LOG = Logger.getLogger(ScreenLogger.class);

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
        LOG.debug(msg);
    }
}