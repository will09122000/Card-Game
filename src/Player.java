public class Player implements Runnable
{
    private int playerID;
    private CardDeck playerHand;
    private boolean stop = false;

    // Setters
    public void setplayerID(int playerID){
        this.playerID = playerID;
    }

    // Getters
    public int getplayerID() {
        return playerID;
    }

    public CardDeck getPlayerHand() {
        return playerHand;
    }

    // Constructor
    public Player(int playerID, CardDeck playerHand) {
        this.playerID = playerID;
        this.playerHand = playerHand;
    }

    public synchronized void stop() {
        this.stop = true;
    }

    private synchronized boolean running() {
        return this.stop == false;
    }

    private synchronized boolean isWinningHand() {
        int cardNumber = this.getPlayerHand().viewCard(0).getNumber();
        for (int i = 0; i < 4; i++) {
            if (cardNumber != this.getPlayerHand().viewCard(i).getNumber()) {
                return false;
            }
        }
        return true;
    }

    public void run()
    {
        while(running()) {
            System.out.println(Thread.currentThread().getName() + ": " + this.getPlayerHand().displayCards());
            if (isWinningHand()) {
                stop();
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
