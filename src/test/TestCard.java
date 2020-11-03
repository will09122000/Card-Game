package test;
import main.Card;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestCard {
    private Card card;

    @Before
    public final void setUp() {
        card = new Card(1);
    }

    @Test
    public final void testCardNumber() {
        assertEquals("Card number must be 1", 1, card.getNumber());
    }
}
