import javax.swing.JOptionPane; // import JOptionPane for pop-up dialogs

public class Round {
    private int currentRound = 1; // tracks the current round, starting with round 1
    private boolean roundOver = false; // indicator of whether or not round is over

    // starts the round based on the current round number
    public void startRound() {
        // add more setup for the specific round
    }

    // updates the caption for the current round and displays it in a pop-up dialog
    private void updateCaption() {
        String caption = "Round " + currentRound + " Begins!"; // sets the appropriate caption for the round
        JOptionPane.showMessageDialog(null, caption, "Round Notification", JOptionPane.INFORMATION_MESSAGE); // displays the pop-up dialog
    }

    // checks if the player won the current round and handles round progression
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            if (currentRound == 1) {
                JOptionPane.showMessageDialog(null, "Won round 1! Moving to next round.", "Victory", JOptionPane.INFORMATION_MESSAGE);
                nextRound(); // moves to the next round
            } else if (currentRound == 2) {
                JOptionPane.showMessageDialog(null, "Won round 2! Moving to final round.", "Victory", JOptionPane.INFORMATION_MESSAGE);
                nextRound(); //moves to the final round
            } else {
                JOptionPane.showMessageDialog(null, "Victory! Game won.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                endRound(); //ends the game if the final round is won
            }
            return true; //returns true if the player won
        } else {
            JOptionPane.showMessageDialog(null, "Lost the round. Resetting game to round 1.", "Defeat", JOptionPane.WARNING_MESSAGE);
            resetRounds(); //resets the game to round 1 if the player loses
            return false; // returns false if the player lost
        }
    }

    // ends the current round and updates the round status
    public void endRound() {
        roundOver = true; // sets round status to over
        JOptionPane.showMessageDialog(null, "Round over.", "Round Status", JOptionPane.INFORMATION_MESSAGE); // informs that the round is over
    }

    // resets the game to round 1 if the player loses
    public void resetRounds() {
        currentRound = 1; // resets to the first round
        roundOver = false; // marks round as not over
        updateCaption(); // updates the caption for round 1
        JOptionPane.showMessageDialog(null, "Game reset to round 1.", "Game Reset", JOptionPane.INFORMATION_MESSAGE); // informs about the reset
    }

    // advances to the next round if the player wins
    private void nextRound() {
        currentRound++; // increments the round number
        updateCaption(); // updates the caption for the new round
    }

    // returns whether the current round is over
    public boolean isRoundOver() {
        return roundOver; // returns round status
    }

    // hides the caption, useful for transitions between rounds or screens
    public void hideCaption() {
    }
}