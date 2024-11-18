/*import javax.swing.JOptionPane;

public class Round2 extends Round {

    private boolean waveDefeated = false; // track if Wave is defeated

    @Override
    public void startRound() {
        updateCaption("Round 2: Wave vs. Tiger");
        showContent();
        // add setup for the background and characters specific to Round 2
        setupBackground();
        setupCharacters();
    }

    // update the caption for this round
    private void setupBackground() {
        // code to display the background for Round 2, such as pool setting
        System.out.println("Setting background to 'Chris Kjeldsen Pool'");
    }

    private void setupCharacters() {
        // code to display Wave and Tiger
        System.out.println("Showing Wave and Tiger characters");
    }

    @Override
    public boolean checkWinner(boolean playerWon) {
        if (playerWon) {
            waveDefeated = true;
            JOptionPane.showMessageDialog(null, "Round 2 won! Prepare for the final round.", "Victory", JOptionPane.INFORMATION_MESSAGE);
            nextRound();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lost in Round 2. Resetting game to round 1.", "Defeat", JOptionPane.WARNING_MESSAGE);
            resetRounds();
            return false;
        }
    }

    @Override
    protected void nextRound() {
        hideContent();
        System.out.println("Moving to Round 3");
        // transition logic to Round3
    }

    @Override
    public void showContent() {
        if (!isPaused) {
            System.out.println("Showing Round 2 content");
            // logic to display characters and background
        }
    }

    @Override
    public void hideContent() {
        System.out.println("Hiding Round 2 content");
        // logic to hide characters and background
    }
}
*/