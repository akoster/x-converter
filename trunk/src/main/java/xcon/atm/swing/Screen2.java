package xcon.atm.swing;

import javax.swing.JComponent;
import xcon.atm.swing.event.AtmEventHandler;

public interface Screen2 {

    public abstract JComponent getJComponent();

    public abstract void showInfoPanel();

    public abstract void showMoneyPanel();

    public abstract boolean isShowingInfoPanel();

    public abstract void setMessage(String message);

    public abstract void addLine(String message);

    public abstract void addEventHandler(AtmEventHandler handler);

}