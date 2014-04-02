/**
 * 
 */
package xcon.project.atm.event;

public class SlotPanelEvent extends AtmEvent{
    private final boolean isOpen;

    public SlotPanelEvent(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }
}