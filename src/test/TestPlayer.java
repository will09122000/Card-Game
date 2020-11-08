package test;

import main.Player;
import main.Card;
import main.CardDeck;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Tests the Player class.
public class TestPlayer {
    private ArrayList<CardDeck> decksArray = new ArrayList<CardDeck>();

    private Player player1;
    // Runs stopGame.
    @Before
    public final void testStopGamesetUp() {
        Player.stopGame();
    }

    // Check that the stop boolean is now set to true.
    @Test
    public final void testStopGame() {
        assertTrue("Stop game must be true.", Player.stop);
    }
    
    
    //Create a new player object with an ID of 1.
    @Before
    public final void testPlayerIDsetUp() {
        CardDeck cardDeck1 = new CardDeck(new Card(1), new Card(2),
                                          new Card(3), new Card(4));
        player1 = new Player(1, cardDeck1, decksArray);
    }

    // Check that the player's ID is equal to 1.
    @Test
    public final void testPlayerID() {
        assertEquals("Player number must be 1.", 1, player1.getPlayerID());
    }

    private Player player2;
    // Create a new player with a winning hand.
    @Before
    public final void testIsWinningHandTrueSetUp() {
        CardDeck cardDeck2 = new CardDeck(new Card(1), new Card(1),
                                          new Card(1), new Card(1));
        player2 = new Player(1, cardDeck2, decksArray);
    }

    // Check that the method 'isWinningHand' detects a winning hand.
    @Test
    public final void testIsWinningHandTrue() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        Method isWinningHand
            = Player.class.getDeclaredMethod("isWinningHand", int.class);
        isWinningHand.setAccessible(true);
        boolean returnValue = (boolean) isWinningHand.invoke(player2, 1);
        assertTrue("Method should return true as the player's cards are all equal.",
                   returnValue);
    }

    private Player player3;
    // Create a new player with a hand that cannot win.
    @Before
    public final void testIsWinningHandFalseSetUp() {
        CardDeck cardDeck3 = new CardDeck(new Card(1), new Card(2),
                                          new Card(3), new Card(4));
        player3 = new Player(1, cardDeck3, decksArray);
    }

    // Check that the method 'isWinningHand' rejects the hand as a winning hand.
    @Test
    public final void testIsWinningHandFalse() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        Method isWinningHand
            = Player.class.getDeclaredMethod("isWinningHand", int.class);
        isWinningHand.setAccessible(true);
        boolean returnValue = (boolean) isWinningHand.invoke(player3, 1);
        assertFalse("Method should return false as the player's cards are not all equal.",
                    returnValue);
    }

    private Player player4;
    // Create a new player with 4 cards and a deck to draw from.
    @Before
    public final void testDrawCardSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2),
                                          new Card(3), new Card(4));
        decksArray.add(cardDeck4);
        player4 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the player has 5 cards now they have drawn a card.
    @Test
    public final void testDrawCard() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        Method drawCard = Player.class.getDeclaredMethod("drawCard");
        drawCard.setAccessible(true);
        drawCard.invoke(player4);
        assertEquals("Player must now have 5 cards.",
                     5, player4.getPlayerHand().numberOfCards());
    }

    private Player player5;
    // Create a new player with 5 cards and a deck to draw from.
    @Before
    public final void testDiscardCardSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2), new Card(1),
                                          new Card(1), new Card(1));
        decksArray.add(cardDeck4);
        decksArray.add(new CardDeck());
        player5 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the player now has 4 cards, the card they discarded was
    // correct and the deck they discarded to now has that card.
    @Test
    public final void testDiscardCard() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        Method discardCard = Player.class.getDeclaredMethod("discardCard");
        discardCard.setAccessible(true);
        discardCard.invoke(player5);
        assertEquals("Player must now have 4 cards.",
                     4, player5.getPlayerHand().numberOfCards());
        assertEquals("Player must have discarded a 2.",
                     "1 1 1 1 ",
                     player5.getPlayerHand().displayCards().toString());
        assertEquals("Correct deck to discard to must have the discarded card.",
                     "2 ", decksArray.get(1).displayCards().toString());
    }

    private Player player6;
    // Create a new player with 4 cards.
    @Before
    public final void testWriteInitialHandSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2),
                                          new Card(3), new Card(4));
        player6 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the first line of the ouput file is as expected after the
    // player's initial hand has been written to it.
    @Test
    public final void testWriteInitialHand() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, FileNotFoundException {
        Method writeInitialHand
            = Player.class.getDeclaredMethod("writeInitialHand");
        writeInitialHand.setAccessible(true);
        writeInitialHand.invoke(player6);

        String firstLine = "";
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File packFile = new File(parentDir, "player1_output.txt");

        Scanner reader = new Scanner(packFile);
        // Read each line of the text file
        while (reader.hasNextLine()) {
            firstLine = reader.nextLine();
        }
        reader.close();

        assertEquals("Player 1's initial hand must be: '1 2 3 4'",
                     "player 1 initial hand 1 2 3 4 ", firstLine);
    }

    // Create a new player with 4 cards.
    @Before
    public final void testWriteCurrentHandSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2),
                                          new Card(3), new Card(4));
        player6 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the first line of the ouput file is as expected after the
    // player's current hand has been written to it.
    @Test
    public final void testWriteCurrentHand() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, FileNotFoundException {
        Method writeCurrentHand
            = Player.class.getDeclaredMethod("writeCurrentHand");
        writeCurrentHand.setAccessible(true);
        writeCurrentHand.invoke(player6);

        String firstLine = "";
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File packFile = new File(parentDir, "player1_output.txt");

        Scanner reader = new Scanner(packFile);
        // Read each line of the text file
        while (reader.hasNextLine()) {
            firstLine = reader.nextLine();
        }
        reader.close();

        assertEquals("Player 1's current hand must be: '1 2 3 4'",
                     "player 1 current hand is 1 2 3 4 ", firstLine);
    }
}