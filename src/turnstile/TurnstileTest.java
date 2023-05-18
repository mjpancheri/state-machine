package turnstile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnstileTest extends TurnstileFSM {
    TurnstileFSM fsm;
    String actions;

    @Before
    public void setUp() {
        fsm = this;
        fsm.setState(OneCoinTurnstileState.LOCKED); // establish the logic
        actions = "";
    }

    private void assertActions(String expected) {
        assertEquals(expected, actions);
    }

    @Test
    public void normalOperation() throws Exception {
        coin();
        assertActions("U");
        pass();
        assertActions("UL");
    }

    @Test
    public void forcedEntry() throws Exception {
        pass();
        assertActions("A");
    }

    @Test
    public void doublePayment() throws Exception {
        coin();
        coin();
        assertActions("UT");
    }

    @Test
    public void manyCoins() throws Exception {
        for (int i=0; i<5; i++) {
            coin();
        }
        assertActions("UTTTT");
    }

    @Test
    public void manyCoinsThenPass() throws Exception {
        for (int i=0; i<5; i++) {
            coin();
        }
        pass();
        assertActions("UTTTTL");
    }

    @Override
    public void alarm() {
        actions += "A";
    }

    @Override
    public void lock() {
        actions += "L";
    }

    @Override
    public void unlock() {
        actions += "U";
    }

    @Override
    public void thankyou() {
        actions += "T";
    }
}