package main;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList; 
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class CardGame extends Player {

    // Constructor
    public CardGame(int playerID, CardDeck playerHand,
                    ArrayList<CardDeck> decksArray)
    {
        super(playerID, playerHand, decksArray);
    }

    static int numPlayers;
    static String packFileName;
    static CardDeck entirePack = new CardDeck();
    static ArrayList<CardDeck> playersArray = new ArrayList<CardDeck>();
    static ArrayList<CardDeck> decksArray = new ArrayList<CardDeck>();
    static ArrayList<Thread> playerThreads = new ArrayList<Thread>();

    // Method for retrieving the number of players in this game.
    private static int inputNumPlayers() {
        Scanner scan = new Scanner(System.in);
        // Keep asking for a number until the input is an integer greater
        // than 1.
        do {
            System.out.print("Please enter the number of players: ");
            while (!scan.hasNextInt()) {
                System.out.print("Please enter the number of players:(a positive integer greater than 1) ");
                scan.next();
            }
            numPlayers = scan.nextInt();
        } while (numPlayers < 2);
        // A successful input will close the scanner and return the value
        // to the main method.
        return numPlayers;
    }

    // Method for retrieving the name of the pack text file and verifying it
    // to be used in this game.
    private static String inputPackFileName() {
        boolean isValidPack;
        Scanner scan = new Scanner(System.in);
        // Keep asking for the file name until the pack has been identifed as
        // valid.
        do {
            isValidPack = true;
            System.out.print("Please enter the pack file name: ");
            try {
                packFileName = scan.nextLine();
                File currentDir = new File(".");
                File parentDir = currentDir.getParentFile();
                File packFile = new File(parentDir, packFileName);
                Scanner reader = new Scanner(packFile);
                ArrayList<Integer> cards = new ArrayList<Integer>();
                while (reader.hasNextLine()) {
                    // Read the next line.
                    String cardNumber = reader.nextLine();
                    // Try converting the line to an integer an add it to
                    // the end of the ArrayList.
                    try {
                        cards.add(Integer.parseInt(cardNumber));
                        // If the number is less than 1 it is an invalid pack.
                        if (Integer.parseInt(cardNumber) < 1) {
                            isValidPack = false;
                            throw new NumberFormatException();
                        }
                        // If it's not a number the pack is invalid.
                    } catch (NumberFormatException e) {
                        isValidPack = false;
                        System.out.println("Invalid pack contents, please make sure the numbers are positive integers and there are no empty lines.");
                        break;
                    }
                }
                reader.close();
                // If the pack doesn't have 8n numbers the pack is invalid.
                if (cards.size() != numPlayers * 8) {
                    isValidPack = false;
                    System.out.println("Invalid pack length, please make sure there are 8n positive integers on each line.");
                }
                // If it is impossible for any player to win the game, the
                // pack is rejected.
                boolean possibleWinner = false;
                for (int i = 1; i <= numPlayers; i++) {
                    int countCards = 0;
                    for (Integer num : cards) {
                        if (i == num)
                            countCards++;
                    }
                    if (countCards > 3) {
                        possibleWinner = true;
                        break;
                    }
                }
                if (!possibleWinner) {
                    isValidPack = false;
                    System.out.println("Impossible game to win, please edit the pack or choose another one.");
                }
            } catch (FileNotFoundException e) {
                isValidPack = false;
                System.out.println("Cannot find file, please type the file name as fileName.txt");
            }
        // The user is asked for the file name again if it is not found, if
        // the pack is found to be invalid or it is an impossible game to be
        // won by any player.
        } while (!isValidPack);
        // A valid pack will close the scanner and return the value to the main
        // method.
        scan.close();
        return packFileName;
    }

    // Method for creating an array of Card objects from all the cards in the
    // text file.
    private static CardDeck fileToDeck(String packFileName) {
        // Reads the parent directory and finds the deck file.
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File packFile = new File(parentDir, packFileName);
        try {
            Scanner reader = new Scanner(packFile);
            // Read each line of the text file
            while (reader.hasNextLine()) {
                // Convert line to an integer and create a card object with
                // that integer.
                int cardNumber = Integer.parseInt(reader.nextLine());
                Card newCard = new Card(cardNumber);
                // Add the card object to the CardDeck object.
                entirePack.addCard(newCard);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return entirePack;
    }

    // Method for distrbuting the each half of the cards to the players and
    // the decks that players draw from repectively.
    private static ArrayList<CardDeck> generateDecks(int numPlayers,
                                                     CardDeck entirePack)
    {
        // Creates an ArrayList of decks
        ArrayList<CardDeck> newDecks = new ArrayList<CardDeck>();
        // cardDiff used to figure out which card to distrbute when doing
        // round-robin.
        int cardDiff = numPlayers - 1;
        for (int i = 0; i < numPlayers; i++) {
            CardDeck deck = new CardDeck();
            int cardNumber = 0;
            int counter = 0;
            // While fewer than 4 cards has been distributed.
            while (cardNumber < 4) {
                // Get the correct card and move it to the deck.
                Card card = entirePack.getCard(counter);
                deck.addCard(card);
                cardNumber++;
                counter += cardDiff;
            }
            // Add the deck just created to the decks array.
            newDecks.add(deck);
            cardDiff -= 1;
            cardNumber = 0;
        }
        return newDecks;
    }

    // Method for deleting all the output text files that were generated in
    // the last running of the program.
    private static void deleteTextFiles() {
        // Retrieve the main source folder and create an array of all files
        // within in. 
        File mainFolder = new File(System.getProperty("user.dir"));
        File files[] = mainFolder.listFiles();
        // If any of these files end in '_output.txt', delete it.
        for (int i = 0; i < files.length; i++) {
            File pes = files[i];
            if (pes.getName().contains("_output.txt")) {
                files[i].delete();
            }
        }
    }

    // Writes the contents of each deck that doesn't have a player assigned to
    // it to its own text file.
    private static void writeEndDeck(int deckID,
                                     ArrayList<CardDeck> decksArray)
    {
        try {
            FileWriter writeFile
                = new FileWriter("deck" + (deckID+1) + "_output.txt", true);
            BufferedWriter buffer = new BufferedWriter(writeFile);
            PrintWriter print = new PrintWriter(buffer);
            // Write the card numbers of the corresponding deck to the first
            // line.
            print.println("deck" + (deckID+1) + " contents: " +
                          decksArray.get(deckID).displayCards());
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        deleteTextFiles();
        // Ask user for number of players playing and the pack text file that
        // is intended to be used.
        numPlayers = inputNumPlayers();
        packFileName = inputPackFileName();

        // Reads the card file again and loads it into a CardPile object.
        entirePack = fileToDeck(packFileName);

        // Distributes first half of the card pile to the players.
        playersArray = generateDecks(numPlayers, entirePack);

        // Distributes the second half of the card pile to the decks.
        decksArray = generateDecks(numPlayers, entirePack);
        
        // Create and start a thread for each player.
        for (int i=0; i<numPlayers; i++) {
            Runnable newPlayer
                = new Player(i+1, playersArray.get(i), decksArray);
            Thread thread = new Thread(newPlayer, "player" + (i+1));
            thread.start();
            playerThreads.add(thread);
            
        }
        // For loop to hault running until all threads have finished.
        for (int i = 0; i < playerThreads.size(); i++) 
        {
            playerThreads.get(i).join(); 
        }

        // Writes the contents of each deck to its own text file.
        for (int i=0; i<decksArray.size(); i++) {
            writeEndDeck(i, decksArray);
        }

        // Display the winning player to the terminal.
        System.out.println("Player " + winner.get(0) + " has won");
    }
}