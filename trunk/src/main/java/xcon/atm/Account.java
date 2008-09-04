package xcon.atm;

public class Account {

    private long id ;
    private long pin;
    private long accountNumber;
    private double balance;

    public long getPin() {
        return -1;
    }

    public void addAmount(double amount) {

    }

    public void substractAmount(double amount) {

    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

}
