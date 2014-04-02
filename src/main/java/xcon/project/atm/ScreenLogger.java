package xcon.project.atm;

import org.apache.log4j.Logger;

import xcon.project.atm.event.AtmEvent;
import xcon.project.atm.event.AtmEventHandler;
import xcon.project.atm.event.ScreenAmountChoiceEvent;
import xcon.project.atm.event.ScreenOtherEvent;

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