import acm.program.*;
// tested 
public class GameClass extends GraphicsProgram {

    private static GraphicsProgram currentScreen; // Active screen
    private static boolean isTransitioning = false; // Prevents overlapping transitions

    public static void main(String[] args) {
        new GameClass().start(); // Start the application
    }

    @Override
    public void run() {
        startMainMenu(); // Start the game with the Main Menu
    }

 // Add this in GameClass.java
    public static void startMainMenu() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Starting Main Menu...");
            switchScreen(new MainMenu());  // Switch to MainMenu
            isTransitioning = false;
        }
  
    }

    // Transition to Round1
    public static void transitionToRound1() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Transitioning to Round1...");
            Sound.stopBackgroundMusic();
            switchScreen(new Round1());
            isTransitioning = false;
        }
    }

    // Transition to Round2
    public static void transitionToRound2(boolean playerWon) {
        if (!isTransitioning) {
            isTransitioning = true;
            Sound.stopBackgroundMusic();
            if (playerWon) {
                System.out.println("Transitioning to Round2...");
                switchScreen(new Round2());
            } else {
                System.out.println("Player lost in Round1. Returning to MainMenu.");
                switchScreen(new MainMenu());
            }
            isTransitioning = false;
        }
    }

    // Transition to Round3
    public static void transitionToRound3(boolean playerWon) {
        if (!isTransitioning) {
            isTransitioning = true;
            Sound.stopBackgroundMusic();
                
            if (playerWon) {
                System.out.println("Transitioning to Round3...");
                switchScreen(new Round3());
            } else {
                System.out.println("Player lost in Round2. Returning to MainMenu.");
                switchScreen(new MainMenu());
            }
            isTransitioning = false;
        
        
        }
    }

    // Cleanly switch screens
    private static void switchScreen(GraphicsProgram newScreen) {
        if (currentScreen != null) {
            currentScreen.removeAll(); // Clear all content from the current screen
        }
        currentScreen = newScreen;     // Assign the new screen
        currentScreen.start();         // Start the new screen
    }
}


