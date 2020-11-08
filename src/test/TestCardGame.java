package test;

import main.Card;
import main.CardDeck;
import main.CardGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//Tests the CardGame class.
public class TestCardGame {

    private CardGame cardGame;
    private InputStream oldSys = System.in;
    private int checkNumPlayers;
    // Simulate an input stream on input '3' and run the method.
    @Before
    public final void testInputNumPlayersSetUp() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        ByteArrayInputStream input = new ByteArrayInputStream("3".getBytes());
        System.setIn(input);
        Method inputNumPlayers
            = CardGame.class.getDeclaredMethod("inputNumPlayers");
        inputNumPlayers.setAccessible(true);
        checkNumPlayers = (int) inputNumPlayers.invoke(cardGame);
    }

    // Check that the simulated input is equal to the method return.
    @Test
    public void testInputNumPlayers() {
        assertEquals("Must return 3 as 3 is a valid number of players.",
                     3, checkNumPlayers);
    }

    // Restore original System.in.
    @After
    public void testInputNumPlayersTearDown() {
        System.setIn(oldSys);
    }

    private String checkPackFileName;
    // Simulate an input stream on input 'three.txt' and run the method.
    @Before
    public final void testInputPackFileNameSetUp() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        ByteArrayInputStream input
            = new ByteArrayInputStream("three.txt".getBytes());
        System.setIn(input);
        Method inputPackFileName
            = CardGame.class.getDeclaredMethod("inputPackFileName");
        inputPackFileName.setAccessible(true);
        checkPackFileName = (String) inputPackFileName.invoke(cardGame);
    }

    // Check that the simulated input is equal to the method return.
    @Test
    public void testInputPackFileName() {
        assertEquals("Must return 'three.txt' as it is a known valid pack.",
                     "three.txt", checkPackFileName);
    }

    // Restore original System.in.
    @After
    public void testInputPackFileNameTearDown() {
        System.setIn(oldSys);
    }


    private ArrayList<CardDeck> distributedDecks;
    private CardDeck entirePack;
    // Create a pack to be distributed and run the method to distribute them.
    @Before
    public final void testGenerateDeckSetUp() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException {
        CardDeck entirePack = new CardDeck(new Card(1), new Card(2), new Card(3),
                                           new Card(4), new Card(5), new Card(6),
                                           new Card(7), new Card(8), new Card(9),
                                           new Card(10), new Card(11), new Card(12));
        Method generateDecks = CardGame.class.getDeclaredMethod(
                                                                "generateDecks",
                                                                int.class,
                                                                CardDeck.class);
        generateDecks.setAccessible(true);
        distributedDecks
            = (ArrayList<CardDeck>) generateDecks.invoke(cardGame, 3, entirePack);
    }

    // Check the correct number of decks were created, check they were distributed
    // in the correct order and check the entire pack has been distributed.
    @Test
    public void testGenerateDeck () {
        assertEquals("Must return an ArrayList of length 3.",
                     3, distributedDecks.size());
        assertEquals("Cards must be distributed in a round-robin method.",
                     "1 4 7 10 2 5 8 11 3 6 9 12 ",
                     distributedDecks.get(0).displayCards().toString() +
                     distributedDecks.get(1).displayCards().toString() +
                     distributedDecks.get(2).displayCards().toString());
        assertNull("Input pack must now be empty when its length = n*4",
                   entirePack);
    }

    ArrayList<CardDeck> decksArray = new ArrayList<CardDeck>();
    // Add a deck of 4 cards to an ArrayList of decks.
    @Before
    public final void testWriteEndDeckSetUp() {
        CardDeck cardDeck = new CardDeck(new Card(1), new Card(2), new Card(3),
                                         new Card(4));
        decksArray.add(cardDeck);
    }

    // Write the cotents of the above deck to a file and check that the
    // contents of the file is as expected.
    @Test
    public final void testWriteEndDeck() throws NoSuchMethodException,
    SecurityException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, FileNotFoundException {
        Method writeEndDeck = CardGame.class.getDeclaredMethod("writeEndDeck",
                                                               int.class,
                                                               ArrayList.class);
        writeEndDeck.setAccessible(true);
        writeEndDeck.invoke(cardGame, 0, decksArray);

        String firstLine = "";
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File packFile = new File(parentDir, "deck1_output.txt");
        Scanner reader = new Scanner(packFile);
        // Read each line of the text file
        while (reader.hasNextLine()) {
            firstLine = reader.nextLine();
        }
        reader.close();

        assertEquals("First line of the text file should match excpected.",
                     "deck1 contents: 1 2 3 4 ", firstLine);
    }
}
