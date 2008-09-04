package xcon.atm;

public class CashDispenser {

    private boolean doorIsClose;

    public CashDispenser() {
        doorIsClose = true;
    }

    private   int availableCashAmount;

    public  void takeCash() {

    }

    public  void openDoor() {
        doorIsClose = false;
    }

    public  void closeDoor() {
        doorIsClose = true;
    }

    
    public boolean isDoorIsClose() {
        return doorIsClose;
    }

    
    public void setDoorIsClose(boolean doorIsClose) {
        this.doorIsClose = doorIsClose;
    }

}
