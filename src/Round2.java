import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round2 extends Round {
    private GImage waveImage; // wave character
    private GImage backgroundImage; // pool background
    private GImage welcomeGif; // welcome GIF
    private GRect pauseButton; 
    private GLabel pauseLabel; 
    private boolean isPaused = false; // tracks whether the game is paused
    private Timer waveMovementTimer; 
    private double waveX = 540; 
    private double waveY = 300; 
    private static final double WAVE_SPEED = 5.0; 
    private GRect overlay; // pause overlay for semi-transparent effect
    private GLabel pauseMessage; // pause message label

    @Override
    public void init() {
        setSize(1280, 720); // size of the game window
        showWelcomeScreen(); // show the welcome GIF before gameplay starts
    }

    private void showWelcomeScreen() {
        // load and display the welcome GIF
        welcomeGif = new GImage("media/PoolWelcome.gif", 0, 0);
        welcomeGif.setSize(1280, 720); // scales to fit the screen
        add(welcomeGif);

        // adds a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); 
                setupContent(); // set up the main content
                startWaveMovement(); // starts wave movement
            }
        }, 3000); // displays the GIF for 3 seconds
    }

    private void setupContent() {
        // pool background
        backgroundImage = new GImage("media/PoolBackground.png", 0, 0);
        backgroundImage.setSize(1280, 720); // scales to fit the screen
        add(backgroundImage);

        // wave character
        waveImage = new GImage("media/WavePrototype.gif", waveX, waveY);
        waveImage.setSize(150, 150); // set wave image size
        add(waveImage);

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
            stopWaveMovement();
            showPauseOverlay(); 
        } else {
            removePauseOverlay(); 
            startWaveMovement(); 
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

    private void startWaveMovement() {
        waveMovementTimer = new Timer();
        waveMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // only move the wave if the game is not paused
                    waveX += WAVE_SPEED;
                    if (waveX > getWidth()) {
                        waveX = -150; // reset wave to the left of the screen when it moves off the right edge
                    }
                    waveImage.setLocation(waveX, waveY); // updates wave's position
                }
            }
        }, 0, 50); // move every 50ms
    }

    private void stopWaveMovement() {
        if (waveMovementTimer != null) {
            waveMovementTimer.cancel(); // stops the timer
        }
    }

    @Override
    public void run() {
        // starts round 2 gameplay
        updateCaption("Welcome to Round 2: Tiger vs. Wave at Chris Kjeldsen Pool!");
    }

    @Override
    public void startRound() {
        init(); // initialize round 2
        run(); // begin gameplay logic
    }

    // main method to launch the program
    public static void main(String[] args) {
        new Round2().start(); // starts the ACM graphics program
    }
}
