package xcon.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import xcon.atm.event.AtmEventHandler;
import xcon.atm.event.BankUpdateCompletedEvent;

public class BankDatabase {

    private static final Logger LOG = Logger.getLogger(BankDatabase.class); 
    private Connection conn;
    private AtmEventHandler atmEventHandler;

    public BankDatabase() {}

    // get the connetion for the database
    public Connection getConnection() {

        if (conn == null) {
            ResourceBundle atm = ResourceBundle.getBundle("atm");
            try {
                conn =
                    DriverManager.getConnection(
                            atm.getString("DB_URL"),
                            atm.getString("DB_USERNAME"),
                            atm.getString("DB_PASSWORD"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public Account findAccount(int pinNumber) {

        Account account = null;
        ResultSet resultSet;
        int i = 1;
        PreparedStatement statement;
        try {
            statement =
                conn.prepareStatement("select * from customers where pin=?");
            statement.setInt(i++, pinNumber);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = createAccount(resultSet);
            }

            else {
                LOG.debug("wrong pin code");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    private Account createAccount(ResultSet rs) {
        Account account = new Account();

        try {
            account.setId(rs.getLong("id"));
            account.setBalance(rs.getDouble("balance"));
            account.setAccountNumber(rs.getLong("accountNumber"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return account;

    }

    public double balanceInquiry(Account account) {
        ResultSet resultSet;
        PreparedStatement statement;
        double balance = 0;
        int i = 1;
        try {
            statement =
                conn.prepareStatement("select balance from customers where"
                    + " id = ?");
            statement.setDouble(i++, account.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void updateBalance(final int amount, final Account account) {

        Thread thread = new Thread() {

            public void run() {
                try {
                    int i = 1;
                    try {
                        PreparedStatement statement =
                            getConnection().prepareStatement(
                                    "UPDATE `customers` "
                                        + "SET balance = ? WHERE id = ?");
                        statement.setDouble(
                                i++,
                                (account.getBalance() + amount));
                        statement.setLong(i++, account.getId());
                        statement.executeUpdate();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    // simulate communication with backend server
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    // ignore
                }
                atmEventHandler.handleAtmEvent(new BankUpdateCompletedEvent());
            }

        };
        thread.start();

    }

    public void addEventHandler(AtmEventHandler atmEventHandler) {
        this.atmEventHandler = atmEventHandler;
    }

}
