package test;

import main.Card;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestCard {
    private Card card;

    // Create a new Card object with a value of 1.
    @Before
    public final void setUp() {
        card = new Card(1);
    }

    // Checking that value of card object created is equal to 1.
    @Test
    public final void testCardNumber() {
        assertEquals("Card number must be 1", 1, card.getNumber());
    }
}
