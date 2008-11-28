package xcon.atm.swing;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.AtmEventHandler;
import xcon.atm.swing.event.KeyPadStopEvent;
import xcon.atm.swing.state.AtmState;
import xcon.atm.swing.state.AuthenticateState;
import xcon.atm.swing.state.EndState;
import xcon.atm.swing.state.StartState;
import xcon.atm.swing.state.UserChoicesState;
import xcon.atm.swing.state.ViewBalanceState;
import xcon.atm.swing.state.WithdrawalState;

public class ATM extends JFrame implements AtmEventHandler {

    private static final long serialVersionUID = 1L;

    // frontend
    private Screen screen;
    private KeyPad keyPad;
    private SlotPanel slotPanel;

    // backend
    private BankDatabase bankDatabase;

    private AtmSession session;

    private AtmState workflowStart;
    private EndState endState;

    public ATM() {

        JPanel atmPanel = new JPanel();

        // example of Observer pattern
        // Screen is the observed
        screen = new Screen();
        // ATM is the Observer of Screen
        screen.addEventHandler(this);
        screen.addEventHandler(new ScreenLogger());

        keyPad = new KeyPad();
        keyPad.addEventHandler(this);

        slotPanel = new SlotPanel();
        slotPanel.addEventHandler(this);
        bankDatabase = new BankDatabase();
        bankDatabase.addEventHandler(this);
        atmPanel.setLayout(new GridLayout(3, 1));
        atmPanel.add(screen);
        atmPanel.add(keyPad);
        atmPanel.add(slotPanel);

        add(atmPanel);
        setVisible(true);
        setSize(200, 400);

        initializeWorkflow();
        initializeAtm();
    }

    // : start
    // insert pass
    // enter pincode
    // click ok -> authenticate
    // :authenticate
    // ? failed -> go to end
    // ? success -> go to user_choices
    // user_choices: 1, 2 or 3
    // ? 1 = show_balance
    // ? 2 -> withdrawal
    // ? 3 -> deposit
    // show_balance
    // system shows balance
    // click ok
    private void initializeWorkflow() {

        workflowStart = new StartState(this);
        AuthenticateState authenticate = new AuthenticateState(this);
        UserChoicesState choices = new UserChoicesState(this);
        ViewBalanceState viewBalance = new ViewBalanceState(this);
        WithdrawalState withdrawal = new WithdrawalState(this);
        DepositState deposit = new DepositState(this);
        endState = new EndState(this);

        workflowStart.setNextStateOnSuccess(authenticate);
        workflowStart.setNextStateOnFailure(workflowStart);
        authenticate.setNextStateOnSuccess(choices);
        authenticate.setNextStateOnFailure(endState);
        // XXX: slight hack
        choices.setNextStateOnSuccess(viewBalance);
        choices.setNextStateTwo(withdrawal);
        choices.setNextStateThree(deposit);
        viewBalance.setNextStateOnSuccess(choices);
        withdrawal.setNextStateOnSuccess(endState); 
        withdrawal.setNextStateOnFailure(endState);
        deposit.setNextStateOnSuccess(endState);
        endState.setNextStateOnSuccess(workflowStart);
    }

    private void initializeAtm() {

        initSession();
        
        slotPanel.setCardSlotStatus(true);
        slotPanel.setMoneySlotStatus(false);

        screen.showInfoPanel();
        screen.setMessage(session.state.getStatusMessage());
    }
    
    public void initSession() {
        
        AtmSession session = new AtmSession();
        session.amount = "";
        session.passwordHideString = "";
        session.password = "";
        session.wrongPasswordCounter = 0;
        session.account = null;
        session.state = workflowStart;
    }

    @Override
    public void handleAtmEvent(AtmEvent atmEvent) {

        System.out.println("received atmEvent");
        
        if (atmEvent instanceof KeyPadStopEvent
            && !session.state.equals(workflowStart))
        {
            session.state = endState;
            screen.setMessage(session.state.getStatusMessage());                      
            screen.showInfoPanel();
        }

        // pass the event to the current workflow state
        session.state.handleAtmEvent(atmEvent);
    }

    public Screen getScreen() {
        return screen;
    }

    public KeyPad getKeyPad() {
        return keyPad;
    }

    public SlotPanel getSlotPanel() {
        return slotPanel;
    }

    public BankDatabase getBankDatabase() {
        return bankDatabase;
    }

    public AtmSession getSession() {
        return session;
    }

}
