public abstract class Round {
    private int currentRound = 1; // tracks the current round, starting with round 1
    private boolean roundOver = false; // indicates if the round is over
    private String caption; // stores the current round caption for display

    // starts the round (to be implemented by subclasses)
    public abstract void startRound();

    // updates the caption for the round
    public void updateCaption(String caption) {
        this.caption = caption; // sets the caption
        System.out.println(caption); // displays the caption in the console (for debugging)
    }

    // checks if the player won the current round
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
            resetRounds(); // reset game if player loses
            return false;
        }
    }

    // ends the current round
    public void endRound() {
        roundOver = true;
        System.out.println("Round over.");
    }

    // resets the game to Round 1
    public void resetRounds() {
        currentRound = 1;
        roundOver = false;
        updateCaption("Game reset to Round 1.");
    }

    // advances to the next round
    protected void nextRound() {
        currentRound++;
        updateCaption("Round " + currentRound + " started.");
    }

    // checks if the round is over
    public boolean isRoundOver() {
        return roundOver;
    }

    // displays the current round caption
    public void displayCaption() {
        System.out.println(caption);
    }

    // hides the current caption
    public void hideCaption() {
        caption = ""; // clears the caption
    }

    // abstract methods to be implemented by subclasses
    public abstract void showContent();

    public abstract void hideContent();
}
