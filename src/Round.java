import acm.program.*;
// TEST
public abstract class Round extends GraphicsProgram {
    private int currentRound = 1; // Tracks the current round, starting with round 1
   
    // Abstract method to start the round (to be implemented by subclasses)
    public abstract void startRound();

    // Updates the caption for the round
    public void updateCaption(String caption) {
        System.out.println(caption); // Displays the caption in the console
    }

    // Checks if the player won the current round
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            if (currentRound == 1) {
                System.out.println("Won Round 1! Moving to next round.");
                nextRound();
            } else if (currentRound == 2) {
                System.out.println("Won Round 2! Moving to final round.");
                nextRound();
            } else {
                System.out.println("Victory! Game won.");
                endRound();
            }
            return true;
        } else {
            System.out.println("Lost the round. Resetting game to Round 1.");
            resetGame();
            return false;
        }
    }

    // Advances to the next round
    public void nextRound() {
        currentRound++;
    }

    // Ends the current round
    public void endRound() {
    }

    // Resets the game to the first round
    public void resetGame() {
        currentRound = 1;
    }
}