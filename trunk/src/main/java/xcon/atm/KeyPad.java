package xcon.atm;

import java.util.Scanner;

/**
 * entering digits
 * @author loudiyimo
 */
public class KeyPad {

    public int enterPinCode()

    {

        Scanner input = new Scanner(System.in);
        int pinCode = input.nextInt();
        return pinCode;
    }

    public int enterOperation() {
        int choice;
        do {
            Scanner input = new Scanner(System.in);
            choice = input.nextInt();
        }
        while (choice > 3);
        return choice ;
    }

    public int enterAmount(Screen screen) {
        int amount = 0 ; 
        Scanner input = new Scanner(System.in);
        while (true)
        {
            amount = input.nextInt();
            if ((amount %10 )!= 0)
            {
                screen.setMessage("the amount must be dividabele by 10");
            }
            else 
            {
                break;
            }
        }
        return amount;
    }
    
    public int enterDepositAmount(Screen screen) {
        int amount = 0 ; 
        Scanner input = new Scanner(System.in);
        while (true)
        {
            amount = input.nextInt();
            if ((amount %10 )!= 0)
            {
                screen.setMessage("the amount to be deposited must " +
                		"be dividabele by 10");
            }
            else 
            {
                break;
            }
        }
        return amount;
    }
}
