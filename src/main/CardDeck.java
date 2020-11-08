package main;

import java.util.ArrayList;

public class CardDeck {
    ArrayList<Card> deck = new ArrayList<Card>();
    
    // Getters
    public ArrayList<Card> getDeck() {
        return deck;
    }

    // Adds the card object to the deck ArrayList.
    public void addCard(Card card) {
        deck.add(card);
    }

    public void removeCard(Card card) {
        // Removes the card object from the deck ArrayList.
        deck.remove(card);
    }

    public Card getCard(int position) {
        // Returns the card object from deck at the index given and removes that card object from the deck ArrayList.
        Card nextCard = ((Card) deck.get(position));
        deck.remove(position);
        return nextCard;
    }
    
    // Used to view a single card in a players hand or in a deck.
    public Card viewCard(int position) {
        // Takes an int as input and gets the card at that position within the deck.
        Card card = ((Card) deck.get(position));
        // Returns the resulting card.
        return card;
    }

    // Used to display the cards that a player or deck has.
    public String displayCards() {
        StringBuffer sb = new StringBuffer();
        for (Card card : deck) {
            sb.append(card.getNumber() + " ");
         }
        return sb.toString();
    }

    // Returns the number of cards in the card deck.
    public int numberOfCards() {
        return deck.size();
    }

    // Constructors
    public CardDeck() {
        this.deck = new ArrayList<Card>();
    }

    public CardDeck(Card card1, Card card2, Card card3, Card card4) {
        this.deck = new ArrayList<Card>();
        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
    }

    // Constructors used for testing
    public CardDeck(Card card1, Card card2, Card card3, Card card4, Card card5) {
        this.deck = new ArrayList<Card>();
        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
        deck.add(card5);
    }

    public CardDeck(Card card1, Card card2, Card card3, Card card4, Card card5, Card card6, Card card7, Card card8, Card card9, Card card10, Card card11, Card card12) {
        this.deck = new ArrayList<Card>();
        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
        deck.add(card5);
        deck.add(card6);
        deck.add(card7);
        deck.add(card8);
        deck.add(card9);
        deck.add(card10);
        deck.add(card11);
        deck.add(card12);
    }
}
