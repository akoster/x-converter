package xcon.atm;

import javax.swing.JComponent;

import xcon.atm.event.AtmEventHandler;

public interface Screen {

    public abstract JComponent getJComponent();

    public abstract void showInfoPanel();

    public abstract void showMoneyPanel();

    public abstract boolean isShowingInfoPanel();

    public abstract void setMessage(String message);

    public abstract void addLine(String message);

    public abstract void addEventHandler(AtmEventHandler handler);

}