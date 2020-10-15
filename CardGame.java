import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the number of players:");
        int num = scan.nextInt();
        scan.close();
        System.out.println("The number entered by user: "+num);
        //String s = scan.next();
        //System.out.println(s);
    }
}
