package xcon.atm.swing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import xcon.atm.swing.event.AtmEvent;
import xcon.atm.swing.event.AtmEventHandler;
import xcon.atm.swing.event.ScreenAmountChoiceEvent;

public class ScreenTest {

    @Test
    public void testScreen() {
        Screen instance = new Screen();
        assertNotNull(instance);
    }

    /*
     * For tests like below it is better to use the behavior-centric approach to
     * unit testing as opposed to the state-centric (classical) approach. There
     * are frameworks for this, for example EasyMock or JMock
     */
    public class TestScreen extends Screen {

        public void clickButton(int index) {
            moneyChoiceButtons[index].doClick();
        }
    }

    @Test
    public void testClickMoneyPanel() {

        TestScreen instance = new TestScreen();
        instance.addEventHandler(new AtmEventHandler() {

            @Override
            public void handleAtmEvent(AtmEvent screenEvent) {
                ScreenAmountChoiceEvent amountEvent =
                    (ScreenAmountChoiceEvent) screenEvent;
                assertEquals(10, amountEvent.getMoneyAmount());
            }
        });
        instance.clickButton(0);
        instance = new TestScreen();
        instance.addEventHandler(new AtmEventHandler() {

            @Override
            public void handleAtmEvent(AtmEvent screenEvent) {
                ScreenAmountChoiceEvent amountEvent =
                    (ScreenAmountChoiceEvent) screenEvent;
                assertEquals(20, amountEvent.getMoneyAmount());
            }
        });
        instance.clickButton(1);
    }

    /*
     * END of behavioristic test
     */

    @Test
    public void testShowInfoPanel() {
        Screen instance = new Screen();
        instance.showInfoPanel();
        assertTrue(instance.isShowingInfoPanel());
    }

    @Test
    public void testShowMoneyPanel() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsShowingInfoPanel() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetInfoLabel() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetInfoLabel() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetMessage() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetMessage() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddLine() {
        fail("Not yet implemented");
    }

}
