import javax.swing.JOptionPane;

public abstract class Round {
    protected boolean roundOver = false; // track if the round is over
    protected boolean isPaused = false;  // track if the round is paused
    protected String caption;            // round caption for display

    // starts the round (to be implemented in each specific round class)
    public abstract void startRound();

    // updates and displays the caption
    protected void updateCaption(String captionText) {
        caption = captionText;
        if (!isPaused) {
            JOptionPane.showMessageDialog(null, caption, "Round Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // checks if the player won the current round and advances or resets as needed
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            nextRound();
            return true;
        } else {
            resetRounds();
            return false;
        }
    }

    // ends the current round and marks it as over
    public void endRound() {
        roundOver = true;
        JOptionPane.showMessageDialog(null, "Round over.", "Round Status", JOptionPane.INFORMATION_MESSAGE);
    }

    // resets the game to round 1
    public void resetRounds() {
        roundOver = false;
        updateCaption("Game reset to round 1.");
    }

    // advances to the next round (handled in specific round classes)
    protected abstract void nextRound();

    // shows content (characters/backgrounds)
    public void showContent() {
        if (!isPaused) {
            System.out.println("showing content for the round");
            // code to display background, characters, etc.
        }
    }

    // hides content (characters/backgrounds) when paused
    public void hideContent() {
        System.out.println("hiding content for the round");
        // code to hide characters, backgrounds, etc.
    }

    // pause functionality
    public void pauseGame() {
        isPaused = true;
        hideContent();
        JOptionPane.showMessageDialog(null, "Game paused", "Pause", JOptionPane.INFORMATION_MESSAGE);
    }

    // resume functionality
    public void resumeGame() {
        isPaused = false;
        showContent();
        JOptionPane.showMessageDialog(null, "Game resumed", "Resume", JOptionPane.INFORMATION_MESSAGE);
    }
}
