package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Card;
import main.CardDeck;

public class TestCardDeck {
    private CardDeck deck1;
    private CardDeck deck2;
    private CardDeck deck3;
    private CardDeck deck4;
    private CardDeck deck4Check;
    private CardDeck deck5;
    private String displayCheck;
    private CardDeck deck6;
    private int numberCheck;

    // Create a new deck and add a card to it.
    @Before
    public final void testAddCardsetUp() {
        deck1 = new CardDeck();
        deck1.addCard(new Card(1));
    }

    // Check that the deck size increased and the correct card was added.
    @Test
    public final void testAddCard() {
        assertEquals("Length of the deck must be 1.", 1, deck1.numberOfCards());
        assertEquals("The card added must be 1.", 1, deck1.viewCard(0).getNumber());
    }


    // Create a new deck and remove a card to it.
    @Before
    public final void testRemoveCardsetUp() {
        Card cardToRemove = new Card(1);
        deck2 = new CardDeck(cardToRemove, new Card(2), new Card(3), new Card(4));
        deck2.removeCard(cardToRemove);
    }

    // Check that the deck size decreased and the correct cards still remain in the deck.
    @Test
    public final void testRemoveCard() {
        assertEquals("Length of the deck must be 3.", 3, deck2.numberOfCards());
        assertEquals("Remaining cards must be 2 3 4.", "2 3 4 ", deck2.displayCards().toString());
    }


    // Create a new deck and remove each card from it.
    @Before
    public final void testGetCardsetUp() {
        deck3 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        deck3.getCard(3);
        deck3.getCard(2);
        deck3.getCard(1);
        deck3.getCard(0);
    }

    // Check that the deck is empty as all cards should be removed.
    @Test
    public final void testGetCard() {
        assertEquals("The deck must be empty.", 0, deck3.numberOfCards());
    }


    // Create a new deck and view each card.
    @Before
    public final void testViewCardsetUp() {
        deck4 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        deck4Check = new CardDeck();
        deck4Check.addCard(deck4.viewCard(0));
        deck4Check.addCard(deck4.viewCard(1));
        deck4Check.addCard(deck4.viewCard(2));
        deck4Check.addCard(deck4.viewCard(3));
    }

    // Check that the deck size decreased and the correct cards still remain in the deck.
    @Test
    public final void testViewCard() {
        assertEquals("Length of the check deck must be 4.", 4, deck4Check.numberOfCards());
        assertEquals("Cards must be 1 2 3 4.", "1 2 3 4 ", deck4.displayCards().toString());
    }


    // Create a new deck and display all cards.
    @Before
    public final void testDisplayCardsetUp() {
        deck5 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        displayCheck = deck5.displayCards().toString();
    }

    // Check that the displayed cards is correct and in the expected format.
    @Test
    public final void testDisplayCard() {
        assertEquals("Remaining cards must be 1 2 3 4.", "1 2 3 4 ", displayCheck);
    }


    // Create a new deck with 4 cards in it.
    @Before
    public final void testNumberOfCardssetUp() {
        deck6 = new CardDeck(new Card(1), new Card(2), new Card(3), new Card(4));
        numberCheck = deck6.numberOfCards();
    }

    // Check that the number of cards in the deck is 4.
    @Test
    public final void testNumberOfCards() {
        assertEquals("Number of cards in the deck must be 4", 4, numberCheck);
    }
}
