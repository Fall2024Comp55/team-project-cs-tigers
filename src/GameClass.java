import acm.graphics.*;
import acm.program.*;
import javax.swing.*;

public class GameClass {

    private static boolean round2Started = false;  // flag to track if Round2 has started

    public static void main(String[] args) {
        // Start the game with the Main Menu
        SwingUtilities.invokeLater(GameClass::startGame);
    }

    static void startGame() {
        // Display the start screen
        new MainMenu().start(); // Starts the MainMenu screen
    }

    public static void transitionToRound1() {
        // Transition from the MainMenu to Round1 smoothly
        System.out.println("Transitioning to Round1...");
        SwingUtilities.invokeLater(() -> {
            Round1 round1 = new Round1();  // Create Round1 instance
            round1.start();  // Start Round1
        });
    }

    public static void transitionToRound2() {
        // transitions from Round1 to Round2, ensuring smooth rendering
        if (!round2Started) {
            System.out.println("Transitioning to Round2...");
            round2Started = true;  // sets flag to true so Round2 doesn't start multiple times

            SwingUtilities.invokeLater(() -> {
                Round2 round2 = new Round2();  // Create Round2 instance
                round2.start();  // starts Round2 properly after Round1 finishes
            });
        }
    }

    public static void displayEndScreen() {
        // called when the game ends, either because the player lost or finished
        System.out.println("Game Over. Thank you for playing.");
    }

    // method will be called after Round1 detects a win
    public static void nextLevel() {
        // if moving from one round to another (for example, Round1 to Round2)
        System.out.println("Get ready for the next level!");
        transitionToRound2(); // transitions to Round2 smoothly
    }
}

