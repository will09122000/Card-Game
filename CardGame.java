import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the number of players:");
        int num = scan.nextInt();
        System.out.print("Please enter location of pack to load:");
        String fileName = scan.next();
        scan.close();
        System.out.println("The number entered by user: " + num);
        System.out.println("The file name entered by user: " + fileName);
    }
}