package xcon.atm.swing;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
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

    private static final Logger LOG = Logger.getLogger(ATM.class);
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
    
    public void init() {
        initDevices();
        initGUI();
        initializeWorkflow();
        initializeState();
    }

    public void initGUI() {
        JPanel atmPanel = new JPanel();
        atmPanel.setLayout(new GridLayout(3, 1));
        atmPanel.add(screen.getJComponent());
        atmPanel.add(keyPad);
        atmPanel.add(slotPanel);
        add(atmPanel);
        setVisible(true);
        setSize(200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initDevices() {
        // example of Observer pattern
        // Screen is the observed
        if (screen == null) {
            screen = new SwingScreen();
        }
        // ATM is the Observer of Screen
        screen.addEventHandler(this);
        screen.addEventHandler(new ScreenLogger());

        if (keyPad == null) {
            keyPad = new KeyPad();
        }
        keyPad.addEventHandler(this);

        if (slotPanel == null) {
            slotPanel = new SlotPanel();
        }
        slotPanel.addEventHandler(this);

        if (bankDatabase == null) {
            bankDatabase = new BankDatabase();
        }
        bankDatabase.addEventHandler(this);
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
    public void initializeWorkflow() {

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

    public void initializeState() {

        initSession();

        slotPanel.setCardSlotStatus(true);
        slotPanel.setMoneySlotStatus(false);

        screen.showInfoPanel();
        screen.setMessage(session.state.getStatusMessage());
    }

    public void initSession() {

        session = new AtmSession();
        session.amount = "";
        session.passwordHideString = "";
        session.password = "";
        session.wrongPasswordCounter = 0;
        session.account = null;
        session.state = workflowStart;
    }

    public void handleAtmEvent(AtmEvent atmEvent) {

        LOG.debug("received atmEvent" + atmEvent);

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

    public EndState getEndState() {
        return endState;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setKeyPad(KeyPad keyPad) {
        this.keyPad = keyPad;
    }

    public void setSlotPanel(SlotPanel slotPanel) {
        this.slotPanel = slotPanel;
    }

    public void setBankDatabase(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
    }

}
