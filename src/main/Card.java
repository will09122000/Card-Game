package main;

// This class represents a singular card with one value assigned to it.
public class Card {
    private int number;

    // Setter
    public void setNumber(int number) {
        this.number = number;
    }

    // Getter
    public int getNumber() {
        return number;
    }

    // Constructor
    public Card(int number) {
        this.number = number;
    }
}
