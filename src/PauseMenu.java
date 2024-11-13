import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenu {    //will create a button on each round so that they can click it and it will pop up the following
    private boolean isPaused; // tracks if the game is paused
    private JDialog pauseDialog; // pop up for pause menu

    // constructor initializes the pause menu
    public PauseMenu() {
        isPaused = false; // starts not paused
        createPauseDialog(); // sets up the pop up menu
    }

    // creates the popup menu
    private void createPauseDialog() {
        pauseDialog = new JDialog(); // create a new dialog
        pauseDialog.setTitle("Game Paused"); // set dialog title
        pauseDialog.setSize(300, 150); // set size of dialog// placeholder for now
        pauseDialog.setLocationRelativeTo(null); // center on screen

        // create buttons for resume and exit
        JButton resumeButton = new JButton("Resume Game");
        JButton exitButton = new JButton("Return to Home Page");

        // action for resume button
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame(); // resumes the game
            }
        });

        // action for exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomePage(); // returns to the home page
            }
        });

        // add buttons to dialog
        JPanel panel = new JPanel(); // create panel for buttons
        panel.add(resumeButton); // add resume button
        panel.add(exitButton); // add exit button

        pauseDialog.add(panel); // add panel to dialog
    }

    // pauses the game
    public void pauseGame() {
        if (!isPaused) { // only pause if not already paused
            isPaused = true; // set paused state
            pauseDialog.setVisible(true); // show pause menu
        }
    }

    // resumes the game
    private void resumeGame() {
        isPaused = false; // set not paused state
        pauseDialog.setVisible(false); // hide pause menu
    }

    // returns to the home page
    private void returnToHomePage() {
        pauseDialog.setVisible(false); // hide pause menu
        // logic to show the home page goes here
        System.out.println("Returning to home page..."); // placeholder for home page logic
    }

    // checks if the game is paused
    public boolean isPaused() { 
        return isPaused; // returns paused state
    }
}

