/**
 * 
 */
package xcon.atm.swing.event;


public class KeyPadNumberEvent extends KeyPadEvent {

    private final int number;

    public KeyPadNumberEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}