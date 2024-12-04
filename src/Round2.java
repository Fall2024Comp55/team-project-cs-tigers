import acm.graphics.*;

import java.awt.event.KeyEvent;
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
    private Wave willie = new Wave(this);
    private Tiger powerCat = new Tiger(this);

    @Override
    public void init() {
        setSize(1920, 1080); // size of the game window
        addKeyListeners();
        showWelcomeScreen(); // show the welcome GIF before gameplay starts
    
    }

    private void showWelcomeScreen() {
        // load and display the welcome GIF
        welcomeGif = new GImage("media/PoolWelcome.gif", 0, 0);
        welcomeGif.setSize(1920, 1080); // scales to fit the screen
        add(welcomeGif);

        // adds a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); 
                setupContent(); // set up the main content
            }
        }, 3000); // displays the GIF for 3 seconds
    }

    private void setupContent() {
        // pool background
        backgroundImage = new GImage("media/PoolBackground.png", 0, 0);
        backgroundImage.setSize(1920, 1080); // scales to fit the screen
        add(backgroundImage);

        // wave character
        willie.setWindowHeight(getHeight());
        willie.setWindowWidth(getWidth());
        powerCat.setGroundLevel(willie.getGroundLevel());
        powerCat.setOpponent(willie.getWaveLoc());
        willie.spawnWave();
        Timer startWillie = new Timer();
        startWillie.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		willie.setTigerLoc(powerCat.returnTiger());
        		if (!isPaused && powerCat.getHP() > 0) {
                    powerCat.damage(willie.getDamageGiven());
                    willie.setDamageGivenToZero();
                    //zSystem.out.println("Health: " + powerCat.getHP());
                }
                
                if (!isPaused && willie.getHP() > 0) {
                    willie.damage(powerCat.getDamageGiven());
                    powerCat.setDamageGivenToZero();
                    //System.out.println("Health: " + willie.getHP());
                }
                        	}
        }, 0, 50);
        
        
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
    
        powerCat.spawnTiger();
        
//        Timer positionCheckTimer = new Timer();
//        positionCheckTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                // Ensure both tiger and hornet are initialized
//                if (powerCat != null && willie != null) {
//                    GImage hornetLoc = willie.getWaveLoc();
//                    if (hornetLoc != null) {
//                        powerCat.checkSide(hornetLoc); // Call Tiger's checkSide method
//                    }
//                }
//            }
//        }, 0, 50); // Check every 50ms
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
            showPauseOverlay();
        } else {
            removePauseOverlay(); 
        }
        willie.checkPaused(isPaused);
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

   
    @Override
    public void keyPressed(KeyEvent e) {
        powerCat.keyPressed(e, this); // Pass the event to the Tiger object
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
