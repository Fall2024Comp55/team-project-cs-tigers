import acm.graphics.*;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round3 extends Round {
    private GImage tritonImage; // triton character
    private GImage backgroundImage; // Spanos Center background
    private GImage welcomeGif; // welcome GIF
    private GRect pauseButton; 
    private GLabel pauseLabel; 
    private boolean isPaused = false; // tracks whether the game is paused
    private Timer tritonMovementTimer; 
    private double tritonX = 540; 
    private double tritonY = 300; 
    private static final double TRITON_SPEED = 5.0; 
    private GRect overlay; // pause overlay for semi-transparent effect
    private GLabel pauseMessage; // pause message label
   // private King triton = new King(this);
    private Tiger powerCat = new Tiger(this);
    
    @Override
    public void init() {
        setSize(1280, 720); // size of the game window 
        showWelcomeScreen(); // show the welcome GIF before gameplay starts
    }

    private void showWelcomeScreen() {
        // load and display the welcome GIF
        welcomeGif = new GImage("media/SpanosWelcome.gif", 0, 0);
        welcomeGif.setSize(1280, 720); // scales to fit the screen
        add(welcomeGif);

        // adds a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); 
                setupContent(); // set up the main content
                startTritonMovement(); // starts triton movement
            }
        }, 3000); // displays the GIF for 3 seconds
    }

    private void setupContent() {
        // Spanos Center background
        backgroundImage = new GImage("media/SpanosBackground.png", 0, 0);
        backgroundImage.setSize(1280, 720); // scales to fit the screen
        add(backgroundImage);

        // triton character
        tritonImage = new GImage("media/TritonPrototype.gif", tritonX, tritonY);
        tritonImage.setSize(150, 150); // set triton image size
        add(tritonImage);

        // pause button
        pauseButton = new GRect(1150, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", 1175, 45);
        pauseLabel.setFont("Monospaced-15");
        pauseLabel.setColor(java.awt.Color.ORANGE); 
        add(pauseLabel);

        // adds mouse listener for pause
        addMouseListeners();
        
        
   /*     Timer positionCheckTimer = new Timer();
        positionCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Ensure both tiger and hornet are initialized
                if (powerCat != null && triton != null) {
                    GImage hornetLoc = herkie.getHornetLoc();
                    if (hornetLoc != null) {
                        powerCat.checkSide(hornetLoc); // Call Tiger's checkSide method
                    }
                }
            }
        }, 0, 50); // Check every 50ms
*/
    
    }

    
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            togglePause(); // resumes the game
        } else if (pauseButton.contains(e.getX(), e.getY())) {
            togglePause(); // pauses the game
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // toggles the pause state
        if (isPaused) {
            stopTritonMovement();
            showPauseOverlay(); 
        } else {
            removePauseOverlay(); 
            startTritonMovement(); 
        }
    }

    private void showPauseOverlay() {
        // creates a semi-transparent overlay
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); 
        add(overlay);

        // add pause message
        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) {
            remove(overlay); 
            overlay = null; // resets overlay
        }
        if (pauseMessage != null) {
            remove(pauseMessage); 
            pauseMessage = null; // reset message
        }
    }

    private void startTritonMovement() {
        tritonMovementTimer = new Timer();
        tritonMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // only move the triton if the game is not paused
                    tritonX += TRITON_SPEED;
                    if (tritonX > getWidth()) {
                        tritonX = -150; // reset triton to the left of the screen when it moves off the right edge
                    }
                    tritonImage.setLocation(tritonX, tritonY); // updates triton's position
                }
            }
        }, 0, 50); // move every 50ms
    }

    private void stopTritonMovement() {
        if (tritonMovementTimer != null) {
            tritonMovementTimer.cancel(); // stops the timer
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        powerCat.keyPressed(e, this); // Pass the event to the Tiger object
    }
    
    @Override
    public void run() {
        // starts round 3 gameplay
        updateCaption("Welcome to Round 3: Tiger vs. Triton at Alex G. Spanos Center!");
    }

    @Override
    public void startRound() {
        init(); // initialize round 3
        run(); // begin game play logic
    }

    // main method to launch the program
    public static void main(String[] args) {
        new Round3().start(); // starts the ACM graphics program
    }
}

