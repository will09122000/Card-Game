import java.util.ArrayList; 

public class CardPile {
    ArrayList<Card> pile = new ArrayList<Card>();
    
    // Getters
    public ArrayList<Card> getPile() {
        return pile;
    }

    // Adds the card object to the pile ArrayList.
    public void addCard(Card card) {
        pile.add(card);
    }

    public void removeCard(Card card) {
        // Removes the card object from the pile ArrayList.
        pile.remove(card);
    }

    public Card getCard(int position) {
        // Returns the card object from pile at the index given and removes that card object from the pile ArrayList.
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
