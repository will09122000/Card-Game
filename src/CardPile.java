public class CardPile {
    private Card[] pile;
    
    // Getters
    public Card[] getPile() {
        return pile;
    }

    public void addCard(Card card) {
        // Iterate through deck of cards until there is an empty space (null) and fill it.
        for (int i = 0; i < pile.length; i++) {
            if (pile[i] == null) {
                pile[i] = card;
                break;
            }
          }
    }

    public void removeCard(Card card) {
        // Iterate through pack of cards until the card is found and remove it from the array.
        for (int i = 0; i < pile.length; i++) {
            if (pile[i] == card) {
                pile[i] = null;
                break;
            }
          }
    }

    public Card getNextCard() {
        for (int i = 0; i < pile.length; i++) {
            if (pile[i] != null) {
                Card nextCard = pile[i];
                pile[i] = null;
                return nextCard;
            }
        }
        return null;
    }

    public Card getCard(int position) {
        return pile[position];
    }

    public void displayCards() {
        for (int i = 0; i<pile.length; i++) {
            System.out.println(pile[i]);
        }
    }

    public void pileHeight() {
        System.out.println(pile.length);
    }

    // Constructor
    public CardPile(int pileHeight) {
        this.pile = new Card[pileHeight];
    }
}
