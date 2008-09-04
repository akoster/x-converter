package xcon.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDatabase {

    private static Connection conn;
    private Account account = null;

    // get the connetion for the database 
    public static Connection getConnection() {
        
        String dbURL = "jdbc:mysql://localhost/banktest";
        String dbUsername = "root";
        String dbPassword = "root";
        try {
             conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn ;
    }

    public void validatePinNumber(int pinNumber) {

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
                System.out.println("wrong pin code");
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Account createAccount(ResultSet rs) {
        Account account = new Account();

        try {
            account.setId(rs.getLong("id"));
            account.setBalance(rs.getDouble("balance"));
            account.setAccountNumber(rs.getLong("accountNumber"));
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return account;

    }

    public Account findAccount(int pinNumber) {
        return null;
    }

    public double balanceInquiry(Account account) {
        ResultSet resultSet ; 
        PreparedStatement statement ; 
        double balance = 0 ; 
        int i= 1; 
        try {
            statement = conn.prepareStatement("select balance from customers where" +
            		" id = ?");
            statement.setDouble(i++, account.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                balance = resultSet.getDouble("balance");
            }
            
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return balance;
    }

    public boolean deposit(int amount) {
        return false;
    }

    public boolean withrawal(int amount) {
       // deze methode is niet nodig
    return false ; 
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static  void closeConnection() {
        try {
            conn.close();
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
        
    }
    
    

    public static void updateBalance(int amount , Account account) {
        Connection conn = getConnection();
        int i = 1 ; 
        try {
            PreparedStatement statement =
                conn.prepareStatement("UPDATE `customers` SET "
                    + "balance = ?  WHERE id = ?");
            statement.setDouble(i++, (account.getBalance() - amount));
            statement.setLong(i++, account.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void updateDepositBalance(double amount,
                                            Account account)
    {
        Connection conn = getConnection();
        int i = 1 ; 
        try {
            PreparedStatement statement =
                conn.prepareStatement("UPDATE `customers` SET "
                    + "balance = ?  WHERE id = ?");
            statement.setDouble(i++, (account.getBalance() + amount));
            statement.setLong(i++, account.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
