import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// do not let it exit the entire game let it return to the homePage
public class PauseMenu {
    private boolean isPaused; //tracks if the game is paused
    private JDialog pauseDialog; //dialog for the pause menu

    //constructor to initialize the pause menu
    public PauseMenu() {
        isPaused = false; // game is not paused initially
        createPauseDialog(); // create the dialog when the menu is instantiated
    }

    // creates the pause menu dialog
    private void createPauseDialog() {
        pauseDialog = new JDialog(); // create new dialog
        pauseDialog.setTitle("Game Paused"); // set title
        pauseDialog.setSize(300, 150); // set size
        pauseDialog.setLocationRelativeTo(null); // center on screen
        pauseDialog.setModal(true); // modal dialog prevents interaction with other windows

        JPanel panel = new JPanel(); // create a panel for buttons
        JButton resumeButton = new JButton("Resume Game"); // button to resume
        JButton exitButton = new JButton("Exit Game"); // button to exit

        // action listener to resume the game
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame(); // call resumeGame method when button is pressed
            }
        });

        // action listener to exit the game
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame(); // call exitGame method when button is pressed
            }
        });

        panel.add(resumeButton); // add resume button to panel
        panel.add(exitButton); // add exit button to panel
        pauseDialog.add(panel); // add panel to dialog
    }

    // method to pause the game
    public void pauseGame() {
        if (!isPaused) {
            isPaused = true; // set paused state to true
            pauseDialog.setVisible(true); // show the pause menu dialog
        }
    }

    // method to resume the game
    private void resumeGame() {
        isPaused = false; // set paused state to false
        pauseDialog.setVisible(false); // hide the pause menu dialog
        // add logic here to resume the game
    }

    // method to exit the game
    private void exitGame() {
        // add logic here to exit the game or return to the main menu
        System.exit(0); // exits the program
    }

    // method to check if the game is currently paused
    public boolean isPaused() {
        return isPaused; // return paused state
    }
}
