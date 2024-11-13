import javax.swing.JOptionPane;

public class Round3 extends Round {

    private boolean kingDefeated = false; // track if King (Triton) is defeated

    @Override
    public void startRound() {
        updateCaption("Final Round: King vs. Tiger at AG Spanos Center");
        showContent();
        setupBackground();
        setupCharacters();
    }

    // sets up the AG Spanos Center as the background for the final round
    private void setupBackground() {
        System.out.println("Setting background to 'AG Spanos Center'");
    }

    // sets up the final characters for the showdown: King vs. Tiger
    private void setupCharacters() {
        System.out.println("Displaying King (Triton) and Tiger characters");
    }

    @Override
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            kingDefeated = true;
            JOptionPane.showMessageDialog(null, "Congratulations! You defeated King. Game won!", "Victory", JOptionPane.INFORMATION_MESSAGE);
            endRound(); // end the game upon winning the final round
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lost the final round. Resetting game to round 1.", "Defeat", JOptionPane.WARNING_MESSAGE);
            resetRounds(); // restart if the player loses
            return false;
        }
    }

    @Override
    protected void nextRound() {
        // This is the final round; no further rounds to advance to
        hideContent();
        System.out.println("Game completed. No further rounds.");
    }

    @Override
    public void showContent() {
        if (!isPaused) {
            System.out.println("Displaying content for Final Round at AG Spanos Center");
            // Code to show King and Tiger characters and background
        }
    }

    @Override
    public void hideContent() {
        System.out.println("Hiding content for Final Round");
        // Code to hide all characters and backgrounds
    }
}
