public class Round {
    private int currentRound = 1; //tracks the current round, starting with round 1
    private boolean roundOver = false; // indicator of whether or not round over
    private String caption; //stores the current round caption for display

    /// starts the round based on the current round number
    public void startRound() {
        //add more setup for the specific round .
    }

    //updates the caption for the current round and prints it
    //planning to be updated to a pop up display in future
    private void updateCaption() {
        updateCaption(); //sets the appropriate caption for the round
        System.out.println(caption); //prints the caption
    }

    //checks if the player won the current round and handles round progression
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            if (currentRound == 1) {
                System.out.println("won round 1! moving to next round.");
                nextRound(); //moves to next round
            } else if (currentRound == 2) {
                System.out.println("won round 2! moving to final round.");
                nextRound(); // moves  to final round
            } else {
                System.out.println("victory! game won.");
                endRound(); // this ends  the game if the final round is won
            }
            return true; // returns true if the player won
        } else {
            System.out.println("lost the round. resetting game to round 1.");
            resetRounds(); // resets the game to round 1 if the player loses
            return false; // returns false if the player lost
        }
    }

    // ends the current round and updates the round status
    public void endRound() {
        roundOver = true; // sets round status to over
        System.out.println("round over.");
    }

    // resets the game to round 1 if the player loses
    public void resetRounds() {
        currentRound = 1; // resets to the first round
        roundOver = false; // marks round as not over
        updateCaption(); //updates the caption for round 1
        System.out.println("game reset to round 1.");
    }

    //advances to the next round if the player wins
    private void nextRound() {
        currentRound++; //increment the round number
        updateCaption(); //updates the caption for the new round
    }

    //returns whether the current round is over
    public boolean isRoundOver() {
        return roundOver; //returns round status
    }
    
    //displays the current round caption on screen
    public void displayCaption() {
        System.out.println(caption); //prints the caption
    }

    //hides the caption, useful for transitions between rounds or screens
    public void hideCaption() {
        caption = ""; //clears the caption
    }
}

