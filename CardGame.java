import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    static int numPlayers = 3;
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
        scan.close();
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
            packFileName = scan.next();
            try {
                File packFile = new File(packFileName);
                Scanner reader = new Scanner(packFile);
                int[] cards = {};  
                while (reader.hasNextLine()) {
                    String cardNumber = reader.nextLine();
                    cards = Arrays.copyOf(cards, cards.length + 1);
                    try {
                        cards[cards.length - 1] = Integer.parseInt(cardNumber);
                        if (Integer.parseInt(cardNumber) < 1) {
                            isValidPack = false;
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        isValidPack = false;
                        System.out.println("Invalid pack contents, please make sure the numbers are positive integers.");
                        break;
                    }
                }
                if (cards.length != numPlayers * 8) {
                    isValidPack = false;
                    System.out.println("Invalid pack length, please make sure there are 8n positive integers on each line.");
                }
                reader.close();
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
        //numPlayers = inputNumPlayers();
        packFileName = inputPackFileName();
    }
}