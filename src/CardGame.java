import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList; 

public class CardGame extends Thread {
    static int numPlayers;
    static String packFileName;
    static CardPile entirePack;
    static ArrayList<Player> playersArray = new ArrayList<Player>(); 
    static Deck[] decks;

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
    static CardPile fileToPile (String packFileName)
    {
        CardPile entirePack = new CardPile();
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

    // Method for distributing the cards to each players in a round-robin fashion.
    static ArrayList<Player> generatePlayers (int numPlayers, CardPile entirePack) 
    {
        int cardDiff = numPlayers - 1;
        for (int i = 0; i < numPlayers; i++) {
            CardPile playerHand = new CardPile();
            int temp = 0;
            int j = 0;
            while (temp < 4) {
                Card card = entirePack.getCard(j);
                playerHand.addCard(card);
                temp ++;
                j += cardDiff;
            }
            Player newPlayer = new Player(i+1, playerHand);
            playersArray.add(newPlayer);
            cardDiff -= 1;
            temp = 0;

        }
        return playersArray;
    }

    public CardGame (String s) { 
        super(s); 
    }

    public static void main(String[] args) {
        // Ask user for number of players playing and the pack text file that is intended to be used.
        numPlayers = inputNumPlayers();
        packFileName = inputPackFileName();
        // Reads the card file again and loads it into a CardPile object.
        entirePack = fileToPile(packFileName);
        System.out.println("Pack Loaded Successfully");
        //entirePack.displayCards();
        // Distributes first half of the card pile to the players.
        playersArray = generatePlayers(numPlayers, entirePack);
        //entirePack.displayCards();

        for (int i=0; i<numPlayers; i++) {
            CardGame temp = new CardGame("Player" + i);
            Player newPlayer = ((Player) playersArray.get(i));
            temp.start();
            System.out.println("Started Thread:" + (i+1));
            System.out.println("Player " + (i+1) + " initial hand: ");
            newPlayer.getPlayerHand().displayCards();
            
        }
    }
}