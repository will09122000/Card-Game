package test;

import main.CardDeck;
import main.CardGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestCardGame {
    private CardGame cardGame1;
    private CardGame cardGame2;
    private CardDeck entirePack;
    private String entirePackString;
    private String entirePackTest;
    
    InputStream oldSys = System.in;
    private int checkNumPlayers;
    private String checkPackFileName;
    private String checkPackContent;
    
    @Before
    public final void testInputNumPlayersSetUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        ByteArrayInputStream input = new ByteArrayInputStream("3".getBytes());
        System.setIn(input);
        Method inputNumPlayers = CardGame.class.getDeclaredMethod("inputNumPlayers");
        inputNumPlayers.setAccessible(true);
        checkNumPlayers = (int) inputNumPlayers.invoke(cardGame1);
    }

    @Test
    public void testInputNumPlayers() {
        assertEquals("Must return 3 as 3 is a valid number of players.", 3, checkNumPlayers);
    }

    @After
    public void testInputNumPlayersTearDown() {
        System.setIn(oldSys);
    }

    /*
    @Before
    public final void testInputPackFileNameSetUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        ByteArrayInputStream input = new ByteArrayInputStream("three.txt".getBytes());
        System.setIn(input);
        Method inputPackFileName = CardGame.class.getDeclaredMethod("inputPackFileName");
        inputPackFileName.setAccessible(true);
        checkPackFileName = (String) inputPackFileName.invoke(cardGame2);
    }

    @Test
    public void testInputPackFileName() {
        assertEquals("Must return 'three.txt' as it is a known valid pack.", "three.txt", checkPackFileName);
    }

    @After
    public void testInputPackFileNameTearDown() {
        System.setIn(oldSys);
    }
    */

    /*
    @Before
    public final void testFileToDeckSetUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        Method fileToDeck = CardGame.class.getDeclaredMethod("fileToDeck", String.class);
        fileToDeck.setAccessible(true);
        entirePack = (CardDeck) fileToDeck.invoke(cardGame1, "test_text_pack.txt");
        entirePackString = entirePack.displayCards().toString();
        entirePackTest = "1 1 1 1 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 ";
    }

    @Test
    public void testFileToDeck () {
        assertEquals("Must return a string of all card numbers with spaces between them.", entirePackTest, entirePackString);
    }
    
    
    @Before
    public final void testGenerateDeckSetUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        Method FileToDeck = CardGame.class.getDeclaredMethod("fileToDeck", String.class);
        FileToDeck.setAccessible(true);
    }

    @Test
    public void testGenerateDeck () {
        assertEquals("Must return a string of all card numbers with spaces b", entirePack, entirePackTest);
    }
    */
}
