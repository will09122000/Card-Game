package test;

import main.Player;
import main.CardDeck;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestPlayer {
    private Player player;
    private CardDeck cardDeck;
    private ArrayList<CardDeck> decksArray;


    @Before
    public final void testPlayerIDSetUp() {
        cardDeck = new CardDeck();
        player = new Player(1, cardDeck, decksArray);
    }

    @Test
    public final void testPlayerID() {
        assertEquals("Player number must be 1", 1, player.getPlayerID());
    }

    /*
    @Before
    public final void testStopGameSetUp() {
        stop = false;
        Player.stopGame();
    }

    @Test
    public final void testStopGame() {
        assertTrue("Stop should be true.", stop);
    }
    */
}