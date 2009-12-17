package xcon.atm.state;

import xcon.atm.ATM;
import xcon.atm.event.AtmEventHandler;

public abstract class AtmState implements AtmEventHandler {

    protected ATM atm;
    private AtmState nextStateTwo;
    private AtmState nextStateThree;
    private AtmState nextStateOnSuccess;
    private AtmState nextStateOnFailure;

    public AtmState(ATM atm) {
        this.atm = atm;
    }

    public void toStateSuccess() {
        atm.getSession().state = nextStateOnSuccess;
        setStatusMessage();
    }

    public void toEndState() {
        atm.getSession().state = atm.getEndState();
    }
    
    public void toStateFailure() {
        atm.getSession().state = nextStateOnFailure;
        setStatusMessage();
    }

    public void toStateTwo() {
        atm.getSession().state = nextStateTwo;
        setStatusMessage();
    }

    public void toStateThree() {
        atm.getSession().state = nextStateThree;
        setStatusMessage();
    }

    private void setStatusMessage() {
        atm.getScreen().setMessage(atm.getSession().state.getStatusMessage());
    }

    public void setNextStateOnSuccess(AtmState nextStateOnSuccess) {
        this.nextStateOnSuccess = nextStateOnSuccess;
    }

    public void setNextStateOnFailure(AtmState nextStateOnFailure) {
        this.nextStateOnFailure = nextStateOnFailure;
    }

    public void setNextStateTwo(AtmState nextStateTwo) {
        this.nextStateTwo = nextStateTwo;
    }

    public void setNextStateThree(AtmState nextStateThree) {
        this.nextStateThree = nextStateThree;
    }

    public abstract String getStatusMessage();

}
