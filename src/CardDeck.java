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

    public Card viewCard(int position) {
        Card card = ((Card) deck.get(position));
        return card;
    }

    public StringBuffer displayCards() {
        StringBuffer sb = new StringBuffer();
        for (Card card : deck) {
            sb.append(card.getNumber());
         }
        return sb;
    }

    // Constructor
    public CardDeck() {
        this.deck = new ArrayList<Card>();
    }
}
