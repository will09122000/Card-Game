//import java.util.Arrays;
import java.util.*; 

public class CardPile {
    //private Card[] pile;
    ArrayList<Card> pile = new ArrayList<Card>();
    
    // Getters
    public ArrayList<Card> getPile() {
        return pile;
    }

    public void addCard(Card card) {
        // Iterate through deck of cards until there is an empty space (null) and fill it.
        pile.add(card);
    }

    public void removeCard(Card card) {
        // Iterate through pack of cards until the card is found and remove it from the array.
        for (int i = 0; i < pile.size(); i++) {
            if (pile.get(i) == card) {
                pile.add(null);
                break;
            }
          }
    }

    public Card getNextCard() {
        for (int i = 0; i < pile.size(); i++) {
            if (pile.get(i) != null) {
                Card nextCard = pile.get(i);
                pile.add(null);
                return nextCard;
            }
        }
        return null;
    }

    public Card getCard(int position) {
        Card nextCard = ((Card) pile.get(position));
        pile.remove(position);
        return nextCard;
    }

    public void displayCards() {
        for (int i = 0; i<pile.size(); i++) {
            if (pile.get(i) != null) {
                System.out.println(pile.get(i).getNumber());
            }
        }
    }

    // Constructor
    public CardPile() {
        this.pile = new ArrayList<Card>();
    }
}
