package main;

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
    public static ArrayList<Integer> winner = new ArrayList<Integer>(5);
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

    // Method for checking if a player's hand is a winning hand.
    private synchronized boolean isWinningHand(int firstCardNumber) {
        // A for loop that iterates through the cards in a player's hand and returns false if any of the cards are not the same as the first card in that player's hand.
        for (int i = 0; i < 4; i++) {
            if (firstCardNumber != this.getPlayerHand().viewCard(i).getNumber()) {
                return false;
            }
        }
        // It otherwise returns true as all cards in the hand are the same meaning that the player ahs won the game.
        return true;
    }

    // Method for drawing a card from the top of the deck to the player's left.
    private synchronized void drawCard(File outputFile) {
        // The current deck is the player's hand.
        CardDeck currentDeck = this.getPlayerHand();
        // The player draws from the deck that has an id of 1 less than the players id (decks have ids starting from 0).
        CardDeck drawCardDeck = decksArray.get(this.getPlayerID()-1);
        // The new card is the first card in the deck the player draws from.
        Card newCard = drawCardDeck.getCard(0);
        // The new card is added to the current deck.
        currentDeck.addCard(newCard);
        // The player's hand is set to be the current deck.
        this.setPlayerHand(currentDeck);

        try {
            FileWriter writeFile = new FileWriter(outputFile.getName(), true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            // Prints which player drew and what card they drew to their specific player output file.
            print.println("player " + this.getPlayerID() + " draws a " + newCard.getNumber() + " from deck " + this.getPlayerID());
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for discading a random card that is not of the player's preference to the bottom of the deck to the player's right.
    private synchronized void discardCard(File outputFile) {
        // The current deck is the player's hand.
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
            print.println("player " + this.getPlayerID() + " discards a " + oldCard.getNumber() + " to deck " + deckNumber);
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

     // Writes the last lines of each player's file when a player wins.
     private synchronized void writeEndHand() {
        try {
            FileWriter writeFile = new FileWriter(outputFile.getName(), true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            if (winner.get(0) == this.getPlayerID()) {
                print.println("player " + this.getPlayerID() + " wins");
                print.println("player " + this.getPlayerID() + " exits");
                print.println("player " + this.getPlayerID() + " final hand: " + this.getPlayerHand().displayCards());
            } else {
                print.println("player " + winner.get(0) + " has informed player " + this.getPlayerID() + " that player " + winner.get(0) + " has won");
                print.println("player " + this.getPlayerID() + " exits");
                print.println("player " + this.getPlayerID() + " hand: " + this.getPlayerHand().displayCards());
            }
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        writeInitialHand();
        while(!stop) {
            //System.out.println("Player" + this.getPlayerID() + " " + this.getPlayerHand().displayCards());
            if (isWinningHand(this.getPlayerHand().viewCard(0).getNumber())) {
                winner.add(this.getPlayerID());
                stopGame();
            } else {
                this.drawCard(outputFile);
                this.discardCard(outputFile);
                writeCurrentHand();
            }
        }
        writeEndHand();
    }
}
