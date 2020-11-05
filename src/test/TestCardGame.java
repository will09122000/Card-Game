package test;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jdk.jfr.Timestamp;
import main.CardGame;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import main.CardGame;
import main.CardDeck;
import main.Card;
import main.Player;

public class TestCardGame {
    private CardGame cardGame1;
    private CardGame cardGame2;
    private CardDeck entirePack;
    private String entirePackString;
    private String entirePackTest;
    
    /*
    private int checkNumPlayers;
    private String testPackFileName;
    private String checkPackContent;
    */
    
    /*
    @Before
    public final void testInputNumPlayersSetUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        Method inputNumPlayers = CardGame.class.getDeclaredMethod("inputNumPlayers", int.class);
        inputNumPlayers.setAccessible(true);
        checkNumPlayers = (int) inputNumPlayers.invoke(cardGame1, 5);
    }

    @Test
    public void testInputNumPlayers () {
        assertEquals("Must return true as 5 is a valid number of players.", 5, checkNumPlayers);
    }


    @Before
    public final void testInputPackFileNameSetUp() {
        testPackFileName = "test_text_pack.txt";
        Method inputPackFileName = CardGame.class.getDeclaredMethod("inputPackFileName");
        inputPackFileName.setAccessible(true);
        checkPackContent = (String) inputPackFileName.invoke(cardGame2);
    }

    @Test
    public void testInputPackFileNamePlayers () {
        //CardGame tester = new CardGame();
        //assertEquals();
    }
    */

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
    
    
    /*@Before
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
