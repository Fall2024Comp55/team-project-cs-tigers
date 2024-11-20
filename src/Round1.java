import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage hornetImage; // Hornet character
    private GImage backgroundImage; // Background image
    private GImage welcomeGif; // Welcome GIF
    private GRect pauseButton; // Pause button rectangle
    private GLabel pauseLabel; // Pause button text
    private boolean isPaused = false; // Tracks whether the game is paused
    private Timer hornetMovementTimer; // Timer for Hornet's movement
    private double hornetX = 540; // Initial X position of the Hornet
    private double hornetY = 300; // Initial Y position of the Hornet
    private static final double HORNET_SPEED = 5.0; // Speed of Hornet's horizontal movement
    private GRect overlay; // Pause overlay for semi-transparent effect
    private GLabel pauseMessage; // Pause message label

    @Override
    public void init() {
        setSize(1280, 720); // Set the size of the game window
        showWelcomeScreen(); // Show the welcome GIF before gameplay starts
    }
// new background
    private void showWelcomeScreen() {
        // Load and display the welcome GIF
        welcomeGif = new GImage("media/BurnsWelcome.gif", 0, 0);
        welcomeGif.setSize(1280, 720); // Scale to fit the screen
        add(welcomeGif);

        // Add a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); // Remove the welcome GIF
                setupContent(); // Set up the main content
                startHornetMovement(); // Start the Hornet movement
            }
        }, 3000); // Display the GIF for 3 seconds
    }

    private void setupContent() {
        // Add a black background
        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
        blackBackground.setFilled(true);
        blackBackground.setFillColor(java.awt.Color.BLACK);
        add(blackBackground);

        // Add background image
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(1280, 720); // Scale to the window size
        add(backgroundImage);

        // Add Hornet character
        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
        hornetImage.setSize(150, 150); // Set Hornet image size
        add(hornetImage);

        // Add Pause button
        pauseButton = new GRect(1150, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", 1175, 45);
        pauseLabel.setFont("Monospaced-15");
        pauseLabel.setColor(java.awt.Color.ORANGE); // Set pause label color to white
        add(pauseLabel);

        // Add Mouse Listener for pause
        addMouseListeners();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            // Resume the game if it's paused and the user clicks anywhere
            togglePause();
        } else if (pauseButton.contains(e.getX(), e.getY())) {
            // Pause the game if the user clicks the pause button
            togglePause();
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // Toggle the pause state
        if (isPaused) {
            stopHornetMovement(); // Stop the Hornet's movement
            showPauseOverlay(); // Display the pause overlay
        } else {
            removePauseOverlay(); // Remove the pause overlay
            startHornetMovement(); // Resume Hornet movement
        }
    }

    private void showPauseOverlay() {
        // Create semi-transparent overlay
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        add(overlay);

        // Add pause message
        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) {
            remove(overlay); // Remove the overlay
            overlay = null; // Reset overlay
        }
        if (pauseMessage != null) {
            remove(pauseMessage); // Remove the pause message
            pauseMessage = null; // Reset message
        }
    }

    private void startHornetMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // Only move the Hornet if the game is not paused
                    hornetX += HORNET_SPEED;
                    if (hornetX > getWidth()) {
                        hornetX = -150; // Reset Hornet to the left of the screen when it moves off the right edge
                    }
                    hornetImage.setLocation(hornetX, hornetY); // Update Hornet's position
                }
            }
        }, 0, 50); // Move every 50ms
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel(); // Stop the timer
        }
    }

    @Override
    public void run() {
        // Start Round 1 gameplay
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
    }

    @Override
    public void startRound() {
        // Provide the required implementation for the abstract method
        init(); // Initialize Round 1
        run(); // Begin gameplay logic
    }

    // Main method to launch the program
    public static void main(String[] args) {
        new Round1().start(); // Start the ACM Graphics Program
    }
}

