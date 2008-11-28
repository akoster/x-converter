/**
 * 
 */
package xcon.atm.swing.event;


public class ScreenAmountChoiceEvent extends ScreenEvent {

    private final int moneyChoiceAmount;

    public ScreenAmountChoiceEvent(int amount) {
        moneyChoiceAmount = amount;
    }

    public int getMoneyAmount() {
        return moneyChoiceAmount;
    }
}