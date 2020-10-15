import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {
    static int numPlayers;

    static int playerNum () 
    {
        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Please enter the number of players: ");
            while (!scan.hasNextInt()) {
                System.out.print("Please enter the number of players: (a positive integer greater than 1) ");
                scan.next();
            }
            numPlayers = scan.nextInt();
        }
        while (numPlayers < 2);
        scan.close();
        return numPlayers;
    } 

    static void packFile () {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter location of pack to load:");
        String fileName = scan.next();
        scan.close();
        System.out.println("The file name entered by user: " + fileName);
    } 

    public static void main(String[] args) {
        numPlayers = playerNum();
        System.out.println("The number entered by user: " + numPlayers);
    }
}