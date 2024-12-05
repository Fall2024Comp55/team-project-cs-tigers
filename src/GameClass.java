import acm.graphics.*;
import acm.program.*;
import javax.swing.*;

public class GameClass {

    private static int currentRound = 0;  // Tracks the current round (0 = MainMenu, 1 = Round1, etc.)
    private static boolean isTransitioning = false;  // Prevents overlapping transitions
    private static boolean gameOver = false; // Tracks if the game has ended

    public static void main(String[] args) {
        // Start the game with the Main Menu
        SwingUtilities.invokeLater(GameClass::startGame);
    }

    public static void startGame() {
        // Start the Main Menu
        if (!isTransitioning) {
            isTransitioning = true;
            gameOver = false; // Reset game state
            System.out.println("Starting Main Menu...");
            SwingUtilities.invokeLater(() -> {
                MainMenu mainMenu = new MainMenu();
                mainMenu.start();
                currentRound = 0;  // Set to Main Menu
                isTransitioning = false;
            });
        }
    }

    public static void transitionToRound1() {
        // Transition to Round1
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Transitioning to Round1...");
            SwingUtilities.invokeLater(() -> {
                Round1 round1 = new Round1();
                round1.start();
                currentRound = 1;  // Set to Round1
                isTransitioning = false;
            });
        }
    }

    public static void transitionToRound2() {
        // Transition to Round2
        if (!isTransitioning && currentRound == 1) {  // Only allow this if coming from Round1
            isTransitioning = true;
            System.out.println("Transitioning to Round2...");
            SwingUtilities.invokeLater(() -> {
                Round2 round2 = new Round2();
                round2.start();
                currentRound = 2;  // Set to Round2
                isTransitioning = false;
            });
        }
    }

    public static void transitionToRound3() {
        if (!isTransitioning && currentRound == 2) { // Only transition from Round2
            isTransitioning = true;
            System.out.println("Transitioning to Round3...");
            SwingUtilities.invokeLater(() -> {
                Round3 round3 = new Round3(); // Create a new instance of Round3
                round3.start(); // Start Round3
                currentRound = 3; // Update the current round
                isTransitioning = false;
            });
        }
    }

    public static void displayEndScreen() {
        // Display the end screen after the final round
        if (!isTransitioning && currentRound == 3 && !gameOver) {  // Only allow this if coming from Round3
            isTransitioning = true;
            gameOver = true; // Mark the game as finished
            System.out.println("Displaying End Screen...");
            SwingUtilities.invokeLater(() -> {
                GraphicsProgram endProgram = new GraphicsProgram() {
                    @Override
                    public void run() {
                        GImage endScreen = new GImage("media/endScreen.png", 0, 0);
                        endScreen.setSize(1920, 1080);
                        add(endScreen);
                    }
                };
                endProgram.start();
                currentRound = 4;  // Set to End Screen
                isTransitioning = false;
            });
        }
    }

    public static void nextLevel() {
        // Handles the transition to the next level based on the current round
        if (gameOver) {
            System.out.println("Game is already over. Returning to Main Menu...");
            startGame();
            return;
        }

        System.out.println("Next level triggered. Current round: " + currentRound);
        switch (currentRound) {
            case 0:  // From Main Menu
                transitionToRound1();
                break;
            case 1:  // From Round1
                transitionToRound2();
                break;
            case 2:  // From Round2
                transitionToRound3();
                break;
            case 3:  // From Round3
                displayEndScreen();
                break;
            default:  // Unexpected case
                System.out.println("Unexpected round. Resetting to Main Menu.");
                startGame();
                break;
        }
    }
}

