import acm.graphics.*;
import java.awt.Font;

public class PauseMenu {
    private GRect overlay; // Semi-transparent overlay
    private GLabel pauseOptions; // Pause menu options

    // Show the pause menu with overlay
    public void showPauseMenu(Round1 round) {
        // Create a semi-transparent overlay to cover the screen
        overlay = new GRect(0, 0, round.getWidth(), round.getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Black transparency
        round.add(overlay);

        // Display pause options
        pauseOptions = new GLabel("PAUSED: [P] Unpause | [Y] Controls | [R] Home | [E] Exit", 
                                  round.getWidth() / 2 - 250, round.getHeight() / 2);
        pauseOptions.setFont(new Font("ArcadeClassic", Font.BOLD, 18));
        pauseOptions.setColor(java.awt.Color.WHITE);
        round.add(pauseOptions);
    }

    // Remove the pause menu and overlay
    public void removePauseMenu(Round1 round) {
        if (overlay != null) {
            round.remove(overlay);
            round.remove(pauseOptions);
        }
    }

}


