package xcon.atm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * <pre>
 * Responsabilities:
 *   starting the system 
 *   to initialize all the other subsystems
 *   sending and receives signals from the subsystems ; 
 *   determining wich data should be read and written to the BankDatabase
 *   
 * Lifecycle:
 * - initialization of subsystems
 * - endless iteration:
 * -   wait for input
 * -   handle input
 * 
 * </pre>
 * 
 * @author loudiyimo
 */
public class Atm implements AtmEventHandler {

    private static final String WELCOME_MSG = "WELCOME TO THE ML BANK";

    // input/output devices
    private KeyPad keyPad;
    private Screen screen;
    private DepositSlot depositSlot;
    private CashDispenser cashDispenser;
    private CardReader cardReader;

    // persistent storage
    private BankDatabase bankDatabase;
    private Account currentAccount;

    private CardEvent cardEvent;
    private PincodeEvent pincodevent;
    private StopEvent stopEvent;
    private BalanceEvent balanceEvent;
    private DepositEvent depositEvent;
    private WithdrawalEvent withdrawalEvent;

    public Atm() {

        bankDatabase = new BankDatabase();
        currentAccount = null;
        screen = new Screen(this);
        screen.setMessage(WELCOME_MSG);
        screen.start();
        keyPad = new KeyPad();
        depositSlot = new DepositSlot();
        cashDispenser = new CashDispenser();
    }

    public static void main(String[] args) {
        Atm atm = new Atm();        
    }


    @Override
    public void handleEvent(AtmEvent event) {
        // TODO Auto-generated method stub
        
    }
    
    private void checkForAccountCard() {
        screen.setMessage("enter your card");
        Scanner input = new Scanner(System.in);
        int checkForCard = 0;
        do {
            try {
                checkForCard = input.nextInt();
            }
            catch (Exception e) {
                System.out.println("an exception has occured" + e);
                System.exit(1); // ik weet niet hoe ik
                // dit zou op lossen
                input.nextLine();
            }

        }
        while (checkForCard != 1);

        screen.setMessage("the card has been detected");
        keyPad = new KeyPad();
        int i = 0;
        for (i = 1; i <= 3; i++) {
            screen.setMessage("enter your Pincode");
            int pinNumber = keyPad.enterPinCode();
            boolean authenticated = authenticate(pinNumber);
            if (authenticated) {

                screen.setMessage("choosen one operation\n 1:view balace\t "
                    + "2:withrawel\t 3:deposit");
                int choice = keyPad.enterOperation();
                handleChoice(choice);
                break;
            }
        }
        if (i == 4) {
            screen.setMessage("you entered three times the wrong password, "
                + "your card has been blocked");
            System.exit(1);
        }

    }

    private void handleChoice(int choice) {
        if (choice == 1) {
            screen.setMessage("your balance is " + inquireBalnce());
        }
        else if (choice == 2) {
            screen.setMessage("enter the amount you want to withrawl, divideble by ten");
            requestWithrawal();

        }
        else if (choice == 3) {
            requestDeposit();
        }
        else {
            System.exit(1);

        }

    }

    public boolean authenticate(int pinNumber) {

        BankDatabase.getConnection();
        bankDatabase.validatePinNumber(pinNumber);
        currentAccount = bankDatabase.getAccount();
        if (currentAccount != null) {
            return true;

        }
        else {
            ;
            System.out.println(" wrong pincode  please try again");
            System.out.println("try again");
            return false;
        }

    }

    public double inquireBalnce() {
        double balance = bankDatabase.balanceInquiry(currentAccount);
        BankDatabase.closeConnection();
        return balance;
    }

    public void requestWithrawal() {
        int amount = keyPad.enterAmount(screen);
        if (currentAccount.getBalance() - amount > 0) {
            screen.setMessage("please wait while processing the data");
            BankDatabase.updateBalance(amount, currentAccount);
            screen.setMessage("please take your money");
            // cashDispenser.openDoor();
            /*
             * try { Thread.sleep(10000); cashDispenser.closeDoor();
             * System.out.println("closing the door"); } catch
             * (InterruptedException e) { // TODO Auto-generated catch block
             * e.printStackTrace(); }
             */
        }
        else {
            System.out.println(" the balance is negatief ");
            System.exit(1);
        }
        BankDatabase.closeConnection();
    }

    public void requestDeposit() {
        screen.setMessage("enter the amount of money you want to deposit ");
        double amount = keyPad.enterDepositAmount(screen);
        BankDatabase.updateDepositBalance(amount, currentAccount);
        BankDatabase.closeConnection();
        screen.setMessage("your account has been updated");

    }


}
