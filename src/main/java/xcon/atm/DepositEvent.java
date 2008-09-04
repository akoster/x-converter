package xcon.atm;

public class DepositEvent extends AtmEvent {

    private final double amount;

    public DepositEvent(double amount) {
        this.amount = amount;
    }

    public double getPinCode() {
        return amount;
    }
}
