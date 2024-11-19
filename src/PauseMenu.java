import acm.graphics.*;
import java.awt.Font;

public class PauseMenu {
    private GRect overlay;
    private GRect pauseButton;
    private GLabel pauseLabel;
    private GLabel resumeLabel;

    public void showPauseMenu(Round1 round) {
        // Create a semi-transparent overlay
        overlay = new GRect(0, 0, round.getWidth(), round.getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        round.add(overlay);

        // Add Resume Label
        resumeLabel = new GLabel("Game Paused. Click to Resume.", round.getWidth() / 2 - 150, round.getHeight() / 2);
        resumeLabel.setFont(new Font("ArcadeClassic", Font.BOLD, 18)); // Use a video game font
        resumeLabel.setColor(java.awt.Color.WHITE);
        round.add(resumeLabel);

        round.addMouseListeners(); // Enable interaction for resuming
    }

    public void removePauseMenu(Round1 round) {
        if (overlay != null) {
            round.remove(overlay);
            round.remove(resumeLabel);
        }
    }

    public void addPauseButton(Round1 round) {
        // Create a black pause button
        pauseButton = new GRect(round.getWidth() - 120, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK); // Black background
        round.add(pauseButton);

        // Add a label with white text in a video game font
        pauseLabel = new GLabel("PAUSE", round.getWidth() - 110, 50);
        pauseLabel.setFont(new Font("ArcadeClassic", Font.BOLD, 16)); // Use a video game font
        pauseLabel.setColor(java.awt.Color.WHITE); // White text
        round.add(pauseLabel);
    }

    public boolean isPauseButtonClicked(int x, int y) {
        // Check if the pause button is clicked
        return pauseButton.contains(x, y);
    }
}


