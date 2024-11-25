import acm.graphics.*;
import java.awt.Font;

public class PauseMenu {
    private GRect overlay;
    private GRect pauseButton;
    private GLabel pauseLabel;
    private GLabel resumeLabel;

    public void showPauseMenu(Round1 round) {
        // semi-transparent overlay
        overlay = new GRect(0, 0, round.getWidth(), round.getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // black transparency
        round.add(overlay);

        // paused game label
        resumeLabel = new GLabel("game paused. click to resume.", round.getWidth() / 2 - 150, round.getHeight() / 2);
        resumeLabel.setFont(new Font("ArcadeClassic", Font.BOLD, 18)); // game font
        resumeLabel.setColor(java.awt.Color.WHITE);
        round.add(resumeLabel);

        round.addMouseListeners(); // enables resume
    }

    public void removePauseMenu(Round1 round) {
        if (overlay != null) {
            round.remove(overlay);
            round.remove(resumeLabel);
        }
    }

    public void addPauseButton(Round1 round) {
        // black pause button
        pauseButton = new GRect(round.getWidth() - 120, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        round.add(pauseButton);

        // pause label
        pauseLabel = new GLabel("pause", round.getWidth() - 110, 50);
        pauseLabel.setFont(new Font("ArcadeClassic", Font.BOLD, 16)); // game font
        pauseLabel.setColor(java.awt.Color.WHITE);
        round.add(pauseLabel);
    }

    public boolean isPauseButtonClicked(int x, int y) {
        // checks pause button click
        return pauseButton.contains(x, y);
    }
}



