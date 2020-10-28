import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList; 

public class CardGame {
    static int numPlayers = 3;
    static String packFileName = "three.txt";
    static CardDeck entirePack;
    static ArrayList<CardDeck> playersArray = new ArrayList<CardDeck>();
    static ArrayList<CardDeck> decksArray = new ArrayList<CardDeck>(); 

    // Method for retrieving the number of players in this game.
    static int inputNumPlayers () 
    {
        Scanner scan = new Scanner(System.in);
        // Keep asking for a number until the input is an integer greater than 1.
        do {
            System.out.print("Please enter the number of players: ");
            while (!scan.hasNextInt()) {
                System.out.print("Please enter the number of players: (a positive integer greater than 1) ");
                scan.next();
            }
            numPlayers = scan.nextInt();
        }
        while (numPlayers < 2);
        // A successful input will close the scanner and return the value to the main method.
        return numPlayers;
    } 

    // Method for retrieving the name of the pack text file to be used in this game.
    static String inputPackFileName ()
    {
        boolean isValidPack;
        Scanner scan = new Scanner(System.in);
        // Keep asking for the file name until the pack has been identifed as valid.
        do {
            isValidPack = true;
            System.out.print("Please enter the pack file name: ");
            packFileName = scan.nextLine();
            try {
                File packFile = new File(packFileName);
                Scanner reader = new Scanner(packFile);
                int[] cards = {};  
                while (reader.hasNextLine()) {
                    // Read the next line and increase the size of the numbers array.
                    String cardNumber = reader.nextLine();
                    cards = Arrays.copyOf(cards, cards.length + 1);
                    // Try converting the line to an integer an add it to the end of the array.
                    try {
                        cards[cards.length - 1] = Integer.parseInt(cardNumber);
                        // If the number is less than 1 it is an invalid pack.
                        if (Integer.parseInt(cardNumber) < 1) {
                            isValidPack = false;
                            throw new NumberFormatException();
                        }
                    // If it's not a number the pack is invalid.
                    } catch (NumberFormatException e) {
                        isValidPack = false;
                        System.out.println("Invalid pack contents, please make sure the numbers are positive integers.");
                        break;
                    }
                }
                // If the pack doesn't have 8n numbers the pack is invalid.
                if (cards.length != numPlayers * 8) {
                    isValidPack = false;
                    System.out.println("Invalid pack length, please make sure there are 8n positive integers on each line.");
                }
                reader.close();
            // The user is asked for the file name again if it is not found, this occurs if the pack is found to be invalid as well.
            } catch (FileNotFoundException e) {
                isValidPack = false;
                System.out.println("Cannot find file, please type the file name as fileName.txt");
            }
        }
        while (!isValidPack);
        // A valid pack will close the scanner and return the value to the main method.
        scan.close();
        return packFileName;
    }

    // Method for creating an array of Card objects from all the cards in the text file. 
    static CardDeck fileToPile (String packFileName)
    {
        CardDeck entirePack = new CardDeck();
        File packFile = new File(packFileName);
        Scanner reader;
        try {
            reader = new Scanner(packFile);
            while (reader.hasNextLine()) {
                int cardNumber= Integer.parseInt(reader.nextLine());
                Card newCard = new Card(cardNumber);
                entirePack.addCard(newCard);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return entirePack;
    }

    /*
    // Method for distributing the cards to each player in a round-robin fashion.
    static ArrayList<CardDeck> generatePlayers (int numPlayers, CardDeck entirePack) 
    {
        int cardDiff = numPlayers - 1;
        for (int i = 0; i < numPlayers; i++) {
            CardDeck playerHand = new CardDeck();
            int cardNumber = 0;
            int counter = 0;
            while (cardNumber < 4) {
                Card card = entirePack.getCard(counter);
                playerHand.addCard(card);
                cardNumber ++;
                counter += cardDiff;
            }
            Player newPlayer = new Player(i+1, playerHand);
            playersArray.add(newPlayer);
            cardDiff -= 1;
            cardNumber = 0;
        }
        return playersArray;
    }
    */

    // Method for distrbuting the second half of the cards to the decks that players draw from.
    static ArrayList<CardDeck> generateDecks (int numPlayers, CardDeck entirePack)
    {
        int cardDiff = numPlayers - 1;
        for (int i = 0; i < numPlayers; i++) {
            CardDeck deck = new CardDeck();
            int cardNumber = 0;
            int counter = 0;
            while (cardNumber < 4) {
                Card card = entirePack.getCard(counter);
                deck.addCard(card);
                cardNumber ++;
                counter += cardDiff;
            }
            decksArray.add(deck);
            cardDiff -= 1;
            cardNumber = 0;
        }
        return decksArray;
    }

    public static void main(String[] args) {
        // Ask user for number of players playing and the pack text file that is intended to be used.
        //numPlayers = inputNumPlayers();
        //packFileName = inputPackFileName();
        
        // Reads the card file again and loads it into a CardPile object.
        entirePack = fileToPile(packFileName);
        System.out.println("Pack Loaded Successfully");

        // Distributes first half of the card pile to the players.
        playersArray = generateDecks(numPlayers, entirePack);

        // Distributes the second half of the card pile to the decks.
        decksArray = generateDecks(numPlayers, entirePack);

        for (int i=0; i<numPlayers; i++) {
            Runnable newPlayer = new Player(i+1, playersArray.get(i));
            Thread thread = new Thread(newPlayer, "player" + (i+1));
            thread.start();
        }
    }
}