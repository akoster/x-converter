package xcon.atm;

/**
 */
public class Account {

    private long id;
    private long accountNumber;
    private double balance;
    private String fullname; 

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
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

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }

    public Object getFullName() {
        return fullname;
    }

}
