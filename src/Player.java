public class Player {
    private int playerID;
    private CardPile playerHand;

    // Setters
    public void setplayerID(int playerID){
        this.playerID = playerID;
    }

    // Getters
    public int getplayerID() {
        return playerID;
    }

    // Constructor
    public Player(int playerID, CardPile playerHand) {
        this.playerID = playerID;
        this.playerHand = playerHand;
    }
}
