package xcon.atm;

import java.util.Scanner;

/**
 * - can display messages to the user - can detect if the user touches one of
 * six sensitive areas on the screen
 * @author loudiyimo
 */
public class Screen extends Thread {

    private String message;
    private Integer regionTouched;
    private final AtmEventHandler eventHandler;
          
    public Screen(AtmEventHandler eventHandler) {
        message = null;
        regionTouched = null;
        this.eventHandler = eventHandler;
    }

    public void setMessage(String message) {
        this.message = message;
        System.out.println(message);
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        byte input = 0;
        while (true) {
            input = scanner.nextByte();
            new ScreenEvent(input);
            try {
                Thread.currentThread().sleep(500);
            }
            catch (InterruptedException e) {}
        }
    }   
}
