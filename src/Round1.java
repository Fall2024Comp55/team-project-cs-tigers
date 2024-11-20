import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage hornetImage; 
    private GImage backgroundImage; 
    private GImage welcomeGif; 
    private GRect pauseButton; 
    private GLabel pauseLabel; 
    private boolean isPaused = false; // tracks whether the game is paused
    private Timer hornetMovementTimer; 
    private double hornetX = 540; 
    private double hornetY = 300; 
    private static final double HORNET_SPEED = 5.0; 
    private GRect overlay; // pause overlay for semi-transparent effect
    private GLabel pauseMessage; // pause message label

    @Override
    public void init() {
        setSize(1280, 720); // size of the game window
        showWelcomeScreen(); // show the welcome GIF before game play starts
        addMouseListeners(); // add mouse listener for all interactions
    }

    private void showWelcomeScreen() {
        // load and display the welcome GIF
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(getWidth(), getHeight()); // scales to fit the screen
        add(welcomeGif);

        // adds a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); 
                setupContent(); // set up the main content
                startHornetMovement();
            }
        }, 3000); // displays the GIF for 3 seconds
    }

    private void setupContent() {
        // adds background image
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(getWidth(), getHeight()); // dynamically adjusts size
        add(backgroundImage);

        // adds hornet character
        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
        hornetImage.setSize(150, 150); 
        add(hornetImage);

        // adds pause button
        addPauseButton();
    }

    private void addPauseButton() {
        pauseButton = new GRect(getWidth() - 130, 20, 100, 40); // dynamically set position
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", getWidth() - 110, 45); // add label dynamically
        pauseLabel.setFont("Monospaced-15");
        pauseLabel.setColor(java.awt.Color.ORANGE);
        add(pauseLabel);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            // resumes the game if it's paused
            togglePause();
        } else if (pauseButton != null && pauseButton.contains(e.getX(), e.getY())) {
            // pauses the game if the user clicks the pause button
            togglePause();
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // toggles the pause state
        if (isPaused) {
            stopHornetMovement(); // stops hornet movement
            showPauseOverlay(); // displays the pause overlay
        } else {
            removePauseOverlay(); // removes the pause overlay
            startHornetMovement(); // resumes hornet movement
        }
    }

    private void showPauseOverlay() {
        // creates a semi-transparent overlay
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // semi-transparent black
        add(overlay);

        // adds pause message
        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) {
            remove(overlay); // removes the overlay
            overlay = null; 
        }
        if (pauseMessage != null) {
            remove(pauseMessage); // removes the pause message
            pauseMessage = null;
        }
    }

    private void startHornetMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    hornetX += HORNET_SPEED;
                    if (hornetX > getWidth()) {
                        hornetX = -150; // reset hornet to the left of the screen when it moves off the right edge
                    }
                    hornetImage.setLocation(hornetX, hornetY); // updates hornet's position
                }
            }
        }, 0, 50); // move every 50ms
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel(); // stops the timer
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (backgroundImage != null) {
            backgroundImage.setSize(getWidth(), getHeight()); // adjusts background size
        }
        if (pauseButton != null) {
            pauseButton.setBounds(getWidth() - 130, 20, 100, 40); // repositions pause button
            pauseLabel.setLocation(getWidth() - 110, 45); // repositions pause label
        }
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round1().start(); // starts the ACM graphics program
    }
}
