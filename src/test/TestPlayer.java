package test;

import main.Player;
import main.Card;
import main.CardDeck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestPlayer {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;
    private ArrayList<CardDeck> decksArray = new ArrayList<CardDeck>();

    // Runs stopGame.
    @Before
    public final void testStopGamesetUp() {
        Player.stopGame();
    }

    // Check that the stop boolean is set to true.
    @Test
    public final void testStopGame() {
        assertTrue("Stop game must be true", Player.stop);
    }
    
    
    //Create a new player object with an ID of 1.
    @Before
    public final void testPlayerIDsetUp() {
        CardDeck cardDeck1 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        player1 = new Player(1, cardDeck1, decksArray);
    }

    // Check that the player's ID is equal to 1.
    @Test
    public final void testPlayerID() {
        assertEquals("Player number must be 1", 1, player1.getPlayerID());
    }

    // Create a new player with a winning hand.
    @Before
    public final void testIsWinningHandTrueSetUp() {
        CardDeck cardDeck2 = new CardDeck(new Card(1), new Card(1), new Card(1), new Card(1));
        player2 = new Player(1, cardDeck2, decksArray);
    }

    // Check that the method isWinningHand detects a winning hand.
    @Test
    public final void testIsWinningHandTrue() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method isWinningHand = Player.class.getDeclaredMethod("isWinningHand", int.class);
        isWinningHand.setAccessible(true);
        boolean returnValue = (boolean) isWinningHand.invoke(player2, 1);
        assertTrue("Method should return true as the player's cards are all equal.", returnValue);
    }

    // Create a new player with a hand that cannot win.
    @Before
    public final void testIsWinningHandFalseSetUp() {
        CardDeck cardDeck3 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        player3 = new Player(1, cardDeck3, decksArray);
    }

    // Check that the method isWinningHand rejects the hand as a winning hand.
    @Test
    public final void testIsWinningHandFalse() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method isWinningHand = Player.class.getDeclaredMethod("isWinningHand", int.class);
        isWinningHand.setAccessible(true);
        boolean returnValue = (boolean) isWinningHand.invoke(player3, 1);
        assertFalse("Method should return false as the player's cards are not all equal.", returnValue);
    }

    // Create a new player with 4 cards and a deck to draw from.
    @Before
    public final void testDrawCardSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        decksArray.add(cardDeck4);
        player4 = new Player(1, cardDeck4, decksArray);
    }

    //
    @Test
    public final void testDrawCard() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method drawCard = Player.class.getDeclaredMethod("drawCard", File.class);
        drawCard.setAccessible(true);
        drawCard.invoke(player4, new File("Test_Output.txt"));
        assertEquals("Player must now have 5 cards.", 5, player4.getPlayerHand().numberOfCards());
    }

    // Create a new player with 4 cards and a deck to draw from.
    @Before
    public final void testDiscardCardSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2), new Card(1), new Card(1), new Card(1));
        decksArray.add(cardDeck4);
        decksArray.add(new CardDeck());
        player5 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the method isWinningHand rejects the hand as a winning hand.
    @Test
    public final void testDiscardCard() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method discardCard = Player.class.getDeclaredMethod("discardCard", File.class);
        discardCard.setAccessible(true);
        discardCard.invoke(player5, new File("TestDiscard_Output.txt"));
        assertEquals("Player must now have 4 cards.", 4, player5.getPlayerHand().numberOfCards());
        assertEquals("Player must have discarded a 2.", "1 1 1 1 ", player5.getPlayerHand().displayCards().toString());
        assertEquals("Correct deck to discard to must have the discarded card.", "2 ", decksArray.get(1).displayCards().toString());
    }

    /*
    // Create a new player with 4 cards and a deck to draw from.
    @Before
    public final void testWriteInitialHandSetUp() {
        CardDeck cardDeck4 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        player6 = new Player(1, cardDeck4, decksArray);
    }

    // Check that the method isWinningHand rejects the hand as a winning hand.
    @Test
    public final void testWriteInitialHand() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, FileNotFoundException {
        Method writeInitialHand = Player.class.getDeclaredMethod("writeInitialHand", File.class);
        writeInitialHand.setAccessible(true);
        final File outputFile;
        writeInitialHand.invoke(player5, outputFile);

        String firstLine = "";
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File packFile = new File(parentDir, "TestInitial_Output.txt");

        Scanner reader = new Scanner(packFile);
        // Read each line of the text file
        while (reader.hasNextLine()) {
            firstLine = reader.nextLine();
        }
        reader.close();

        assertEquals("", "player 1 initial hand 1 2 3 4 ", firstLine);
    }
    */

}