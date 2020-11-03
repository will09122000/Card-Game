import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import jdk.jfr.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

public class TestCard {
    private Card card;

    @Before
    public final void setUp() throws Exception {

        card = new Card();
        card.setNumber(5);       
    }

    @Test
    public final void testSetNumber() {
        assertEquals(5, card.getNumber(), "Card number must be 5");
    }
}
