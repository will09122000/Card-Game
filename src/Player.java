import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class Player implements Runnable
{
    private int playerID;
    private CardDeck playerHand;
    private static ArrayList<CardDeck> decksArray;
    private static boolean stop = false;
    // Winner is an array list in case multiple players win on the same round.
    private static ArrayList<Integer> winner = new ArrayList<Integer>(5);
    private File outputFile;

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

    // Method for changing the flag to tell all player threads to stop running.
    public static void stopGame() {
        stop = true;
    }

    // Method for checking that each thread can continue running each time a round has finished.
    private synchronized boolean running() {
        return Player.stop == false;
    }

    // Method for checking if a player's hand is a winning hand.
    private synchronized boolean isWinningHand() {
        int cardNumber = this.getPlayerHand().viewCard(0).getNumber();
        for (int i = 0; i < 4; i++) {
            if (cardNumber != this.getPlayerHand().viewCard(i).getNumber()) {
                return false;
            }
        }
        return true;
    }

    // Method for drawing a card from the top of the deck to the player's left.
    private synchronized void drawCard() {
        CardDeck currentDeck = this.getPlayerHand();
        CardDeck drawCardDeck = decksArray.get(this.getPlayerID()-1);
        Card newCard = drawCardDeck.getCard(0);
        currentDeck.addCard(newCard);
        this.setPlayerHand(currentDeck);

        try {
            FileWriter writeFile = new FileWriter(outputFile.getName(), true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            print.println("player " + this.getPlayerID() + " draws a " + newCard.getNumber() + " from deck " + this.getPlayerID());
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for discading a random card that is not of the player's preference to the bottom of the deck to the player's right.
    private synchronized void discardCard() {
        CardDeck currentDeck = this.getPlayerHand();
        CardDeck discardCardDeck;
        int deckNumber;
        if (this.getPlayerID() == decksArray.size()) {
            discardCardDeck = decksArray.get(0);
            deckNumber = 1;
        } else {
            discardCardDeck = decksArray.get(this.getPlayerID());
            deckNumber = this.getPlayerID() + 1;
        }
        Random rand = new Random();
        Card oldCard;
        do {
            int randomNumber = rand.nextInt(4);
            oldCard = currentDeck.viewCard(randomNumber);
        }
        while (oldCard.getNumber() == this.getPlayerID());
        currentDeck.removeCard(oldCard);
        discardCardDeck.addCard(oldCard);
        this.setPlayerHand(currentDeck);

        try {
            FileWriter writeFile = new FileWriter(outputFile.getName(), true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            print.println("player " + this.getPlayerID() + " discards a " + oldCard.getNumber() + " from deck " + deckNumber);
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creates the output text file for the player and writes the initial hand to it.
    private synchronized void writeInitialHand() {
        outputFile = new File("player" + this.getPlayerID() + "_output.txt");
        try {
            FileWriter writeFile = new FileWriter(outputFile.getName());
            writeFile.write("player " + this.getPlayerID() + " initial hand " + this.getPlayerHand().displayCards());
            writeFile.write(System.getProperty("line.separator"));
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes the player's current hand to their output text file.
    private synchronized void writeCurrentHand() {
        try {
            FileWriter writeFile = new FileWriter(outputFile.getName(), true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            print.println("player " + this.getPlayerID() + " current hand is " + this.getPlayerHand().displayCards());
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        writeInitialHand();
        while(running()) {
            //System.out.println(Thread.currentThread().getName() + ": " + this.getPlayerHand().displayCards());
            System.out.println("Player" + this.getPlayerID() + " " + this.getPlayerHand().displayCards());
            //System.out.println(decksArray.get(0).displayCards() + " " + decksArray.get(1).displayCards() + " " + decksArray.get(2).displayCards());
            if (isWinningHand()) {
                System.out.println("Player " + this.getPlayerID() + " has won");
                winner.add(this.getPlayerID());
                stopGame();
            } else {
                this.drawCard();
                this.discardCard();
                writeCurrentHand();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(winner);
    }
}
