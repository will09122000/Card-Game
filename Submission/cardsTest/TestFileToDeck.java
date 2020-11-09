package test;

import main.CardDeck;
import main.CardGame;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

// Tests the method 'fileToDeck' in the CardGame class.
public class TestFileToDeck {

    private CardGame cardGame;
    private CardDeck entirePack;
    private String entirePackString;
    // Run the method to be tested and assign the contents to 'entriePack'.
    @Before
    public final void testFileToDeckSetUp() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        Method fileToDeck
            = CardGame.class.getDeclaredMethod("fileToDeck", String.class);
        fileToDeck.setAccessible(true);
        entirePack = (CardDeck) fileToDeck.invoke(cardGame, "test_pack.txt");
        entirePackString = entirePack.displayCards().toString();
    }

    // Check that the contents of the pack is as expected.
    @Test
    public void testFileToDeck () {
        assertEquals("Must return a string of all card numbers with spaces between them.",
                     "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 ",
                     entirePackString);
    }
}
