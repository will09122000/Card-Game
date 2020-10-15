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
        scan.close();
        return numPlayers;
    } 

    // Method for retrieving the name of the pack text file to be used in this game.
    static String inputPackFileName () 
    {
        boolean isValidPack = false;
        Scanner scan = new Scanner(System.in);
        // Keep asking for the file name until the pack has been identifed as valid.
        do {
            System.out.print("Please enter the pack file name: ");
            while (!scan.hasNext()) {
                System.out.print("Please enter the pack file name: (fileName.txt) ");
                scan.next();
            }
            packFileName = scan.next();
            try {
                File packFile = new File(packFileName);
                Scanner reader = new Scanner(packFile);
                isValidPack = true;
                while (reader.hasNextLine()) {
                    String cardNumber = reader.nextLine();
                    if (Integer.parseInt(cardNumber) < 1) {
                        isValidPack = false;
                    }
                }
                if (!isValidPack)
                    System.out.print("Invalid pack contents, please make sure there are 8n positive integers on each line.\n");
                reader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        while (!isValidPack);
        // A valid pack will close the scanner and return the value to the main method.
        scan.close();
        return packFileName;
    }  

    public static void main(String[] args) {
        //numPlayers = inputNumPlayers();
        //System.out.println("The number entered by user: " + numPlayers);
        packFileName = inputPackFileName();
    }
}