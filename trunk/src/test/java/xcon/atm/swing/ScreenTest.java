package xcon.atm.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xcon.atm.Screen;
import xcon.atm.SwingScreen;
import xcon.atm.event.AtmEvent;
import xcon.atm.event.AtmEventHandler;
import xcon.atm.event.ScreenAmountChoiceEvent;

public class ScreenTest {

    @Test
    public void testScreen() {
        Screen instance = new SwingScreen();
        assertNotNull(instance);
    }

    /*
     * For tests like below it is better to use the behavior-centric approach to
     * unit testing as opposed to the state-centric (classical) approach. There
     * are frameworks for this, for example EasyMock or JMock
     */
    public class TestScreen extends SwingScreen {

		private static final long serialVersionUID = 1L;

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
        Screen instance = new SwingScreen();
        instance.showInfoPanel();
        assertTrue(instance.isShowingInfoPanel());
    }
}
