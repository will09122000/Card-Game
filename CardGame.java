import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    static int numPlayers;
    static String packFileName;

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
        //scan.close();
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

    public static void main(String[] args) {
        // Ask user for number of players playing and the pack text file that is intended to be used.
        numPlayers = inputNumPlayers();
        packFileName = inputPackFileName();
    }
}