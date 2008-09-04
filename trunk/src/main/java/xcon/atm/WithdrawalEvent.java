package xcon.atm;

public class WithdrawalEvent extends AtmEvent {

    private final double amount;

    public WithdrawalEvent(double amount) {
        this.amount = amount;
    }

    public double getPinCode() {
        return amount;
    }
}
