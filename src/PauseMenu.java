import javax.swing.*;

public class PauseMenu {

    // displays a pause menu with options to resume or exit the game
    public void pauseGame(JDialog parentDialog, Runnable onResume) {
        // show a modal dialog for the pause menu
        int choice = JOptionPane.showOptionDialog(
            parentDialog,
            "Game Paused. Click Resume to continue or Exit to quit.",
            "Pause Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Resume", "Exit"},
            "Resume" // default button
        );

        if (choice == 0) {
            onResume.run(); // call the resume method
        } else {
            System.exit(0); // exit the game
        }
    }
}

