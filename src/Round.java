public class Round {
    private int currentRound = 1; // tracks the current round, starting with round 1
    private boolean roundOver = false;
    private String caption; // stores the current round caption for display


    // starts the round based on the current round number
    public void startRound() {
    }

    	// might edit caption to an actual pop up instead
        private void updateCaption() {

    	updateCaption(); // sets the appropriate caption for the round
        System.out.println(caption);
       
        // additional setup for the specific round can go here
    }

    // checks if the player won the current round
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            if (currentRound == 1) {
                System.out.println("won round 1! moving to next round.");
                nextRound();
            } else if (currentRound == 2) {
                System.out.println("won round 2! moving to final round.");
                nextRound();
            } else {
                System.out.println("victory! game won.");
                endRound();
            }
            return true;
        } else {
            System.out.println("lost the round. resetting game to round 1.");
            // if player loses against boss, they reset the entire game 
            resetRounds();
            return false;
            // created round class 
        }
    }

    // ends the current round and updates the round status
    public void endRound() {
        roundOver = true;
        System.out.println("round over."); 
    }

    //resets the game to round 1 if the player loses
    public void resetRounds() {
        currentRound = 1;
        roundOver = false;
        updateCaption();
        System.out.println("game reset to round 1.");
    }

    // advances to the next round if the player wins
    private void nextRound() {
        currentRound++; 
        updateCaption();
    }

    // filler just adds round Over
    public boolean isRoundOver() {
        return roundOver;
    }
    
    // hide and displays 
    // displays the current round caption on screen
    public void displayCaption() {
        System.out.println(caption);
    }
    // hides the caption (for transitioning between rounds or screens)
    public void hideCaption() {
        caption = ""; // clears the caption
        //* 
    }
}

