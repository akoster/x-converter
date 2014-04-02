package xcon.project.atm.swing;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xcon.project.atm.Account;


public class AccountTest {

    private Account account;
    
    @Before
    public void setUp() {
        account = new Account();
    }
    
    @After
    public void tearDown() {
        account = null;
    }

    @Test
    public void testAccount() {
        
        long accountNumber = 12345L;
        account.setAccountNumber(accountNumber);
        assertEquals(accountNumber, account.getAccountNumber());
        
        double balance = 123.3;
        account.setBalance(balance);
        assertEquals(balance, account.getBalance());
        
        long id = 98749874L;
        account.setId(id);
        assertEquals(id, account.getId());
        
        String fullname = "testname";
        account.setFullName(fullname);
        assertEquals(fullname, account.getFullName());
    }

}
