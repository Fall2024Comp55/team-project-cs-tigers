import acm.graphics.*;

public class PauseMenu {
    private GRect overlay;
    private GLabel resumeLabel;

    public void showPauseMenu(Round1 round) {
        // Create a semi-transparent overlay
        overlay = new GRect(0, 0, round.getWidth(), round.getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        round.add(overlay);

        // Add Resume Label
        resumeLabel = new GLabel("Game Paused. Click to Resume.", round.getWidth() / 2 - 150, round.getHeight() / 2);
        resumeLabel.setFont("Arial-bold-18");
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
}
