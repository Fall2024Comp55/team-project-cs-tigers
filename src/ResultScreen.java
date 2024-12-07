import acm.graphics.*;
import acm.program.*;

public class ResultScreen extends GraphicsProgram {
    private GImage resultBackground; // Holds the victory or defeat screen image
    private GLabel resultMessage; // Message for the player
    private GLabel creatorsLabel; // Credits for creators

    @Override
    public void run() {
        // Default behavior when starting the program
        // This can remain empty for now if you don't need specific behavior
    }

    // Method to show result screen
    public void showResult(boolean playerWon) {
        removeAll(); // Clear the screen to prepare for result display

        if (playerWon) {
            // Display victory screen
            resultBackground = new GImage("media/vicScreen.gif", 0, 0);
        } else {
            // Display defeat screen
            resultBackground = new GImage("media/dftScreen.gif", 0, 0);
        }

        // Scale the background to fit the window
        resultBackground.setSize(getWidth(), getHeight());
        add(resultBackground);

        // Set up result message
        resultMessage.setFont("Monospaced-bold-30");
        add(resultMessage);

        // Add creators' credits
        showCredits();
    }

    private void showCredits() {
        creatorsLabel = new GLabel("Game Created By: Ibrahim, Paul, Faizah, Rogelio", 50, getHeight() - 50);
        creatorsLabel.setFont("Monospaced-20");
        creatorsLabel.setColor(java.awt.Color.WHITE);
        add(creatorsLabel);
    }

    // Main method for standalone testing
    public static void main(String[] args) {
        ResultScreen resultScreen = new ResultScreen();
        resultScreen.start(); // Start the ACM program
        resultScreen.showResult(true); // Replace with false to test defeat screen
    }

	public void setSize(double width, double height) {
		// TODO Auto-generated method stub
		
	}
}

// test
