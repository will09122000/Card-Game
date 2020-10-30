import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable
{
    private int playerID;
    private CardDeck playerHand;
    private static ArrayList<CardDeck> decksArray;
    private boolean stop = false;

    // Setters
    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public void setPlayerHand(CardDeck newHand) {
        this.playerHand = newHand;
    }

    // Getters
    public int getPlayerID() {
        return playerID;
    }

    public CardDeck getPlayerHand() {
        return playerHand;
    }

    // Constructor
    public Player(int playerID, CardDeck playerHand, ArrayList<CardDeck> decksArray) {
        this.playerID = playerID;
        this.playerHand = playerHand;
        Player.decksArray = decksArray;
    }

    public synchronized void stop() {
        this.stop = true;
    }

    private synchronized boolean running() {
        return this.stop == false;
    }

    private synchronized boolean isWinningHand() {
        int cardNumber = this.getPlayerHand().viewCard(0).getNumber();
        for (int i = 0; i < 4; i++) {
            if (cardNumber != this.getPlayerHand().viewCard(i).getNumber()) {
                return false;
            }
        }
        return true;
    }

    private synchronized void drawCard() {
        CardDeck currentDeck = this.getPlayerHand();
        CardDeck drawCardDeck = decksArray.get(this.getPlayerID()-1);

        System.out.println(this.getPlayerID() + " Draw Deck: " + drawCardDeck.displayCards());

        Card newCard = drawCardDeck.getCard(0);

        System.out.println(this.getPlayerID() + " Card Drawn: " + newCard.getNumber());

        currentDeck.addCard(newCard);
        this.setPlayerHand(currentDeck);
        
        System.out.println(this.getPlayerID() + " Player Deck: " + this.getPlayerHand().displayCards());
    }

    private synchronized void discardCard() {
        CardDeck currentDeck = this.getPlayerHand();
        CardDeck discardCardDeck;
        if (this.getPlayerID() == decksArray.size()) {
            discardCardDeck = decksArray.get(0);
        } else {
            discardCardDeck = decksArray.get(this.getPlayerID());
        }
        Random rand = new Random();
        Card oldCard;
        do {
            int randomNumber = rand.nextInt(4);
            oldCard = currentDeck.getCard(randomNumber);
        }
        while (oldCard.getNumber() == this.getPlayerID());
        
        currentDeck.removeCard(oldCard);
        discardCardDeck.addCard(oldCard);
        this.setPlayerHand(currentDeck);
    }

    public void run()
    {
        System.out.println(this.getPlayerID() + " " + this.getPlayerHand().displayCards());
        while(running()) {
            //System.out.println(Thread.currentThread().getName() + ": " + this.getPlayerHand().displayCards());
            this.drawCard();
            //this.discardCard();
            if (isWinningHand()) {
                stop();
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
