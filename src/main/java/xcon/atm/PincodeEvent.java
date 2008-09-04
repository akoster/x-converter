package xcon.atm;


public class PincodeEvent extends AtmEvent {

    private final int pinCode;
    
    public PincodeEvent(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }   
}
