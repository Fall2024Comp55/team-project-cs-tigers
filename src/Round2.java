import acm.graphics.*;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round2 extends Round {
    private GImage backgroundImage; // pool background
    private GImage welcomeGif; // welcome GIF
    private boolean isPaused = false; // tracks whether the game is paused
    private Timer waveMovementTimer;
    private Wave willie = new Wave(this);
    private Tiger powerCat = new Tiger(this);

    // Health bar elements
    private GRect playerHealthBar, playerHealthBarBg; // Tiger's health
    private GRect cpuHealthBar, cpuHealthBarBg; // Wave's health
    private GLabel playerHealthLabel, cpuHealthLabel;

    // Pause feature elements
    private GRect overlay;
    private GLabel pauseMessage;;

    private boolean roundCompleted = false; // Prevents duplicate transitions

    @Override
    public void init() {
        setSize((int) getWidth(), (int) getHeight()); // size of the game window
        addKeyListeners();
        showMapScreen(); // Show the map before gameplay starts
    }

    private void showMapScreen() {
        GImage kjPoolMap = new GImage("media/kjpoolMap.png", 0, 0);
        kjPoolMap.setSize(getWidth(), getHeight()); // Fit the window
        add(kjPoolMap);

        // Timer to remove the map and proceed to the welcome screen
        Timer mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(kjPoolMap);
                showWelcomeScreen();
            }
        }, 3000); // Display the map for 3 seconds
    }

    private void showWelcomeScreen() {
        welcomeGif = new GImage("media/PoolWelcome.gif", 0, 0);
        welcomeGif.setSize
        (getWidth(), getHeight()); // scales to fit the screen
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
        backgroundImage.setSize(getWidth(), getHeight()); // scales to fit the screen
        add(backgroundImage);

        // Add characters
        willie.setWindowHeight(getHeight());
        willie.setWindowWidth(getWidth());
        powerCat.setGroundLevel(willie.getGroundLevel());
        powerCat.setOpponent(willie.getWaveLoc());
        willie.spawnWave();
        powerCat.spawnTiger();

        startWaveMovement(); // Start Wave's movement when content is set up
        setupHealthBars(); // Set up health bars
    }

    private void startWaveMovement() {
        waveMovementTimer = new Timer();
        waveMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // Movement is only updated when not paused
                    willie.setTigerLoc(powerCat.returnTiger());
                    if (powerCat.getHP() > 0) {
                        powerCat.damage(willie.getDamageGiven());
                        willie.setDamageGivenToZero();
                    }

                    if (willie.getHP() > 0) {
                        willie.damage(powerCat.getDamageGiven());
                        powerCat.setDamageGivenToZero();
                    }

                    checkGameOver(); // Check if the game has ended
                }
            }
        }, 0, 50); // Updates every 50ms
    }

    private void setupHealthBars() {
        int barYPosition = 80; // Lowered position for better visibility

        // PLAYER Health Bar and Label
        GLabel playerLabel = new GLabel("PLAYER", 50, barYPosition - 15); // Positioned above the bar
        playerLabel.setFont("Monospaced-bold-20");
        playerLabel.setColor(java.awt.Color.BLACK); // Black for visibility
        add(playerLabel);

        playerHealthBarBg = new GRect(50, barYPosition, 300, 25); // Background
        playerHealthBarBg.setFilled(true);
        playerHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(playerHealthBarBg);

        playerHealthBar = new GRect(50, barYPosition, 300, 25); // Foreground (Health)
        playerHealthBar.setFilled(true);
        playerHealthBar.setFillColor(java.awt.Color.GREEN);
        add(playerHealthBar);

        playerHealthLabel = new GLabel("51", 180, barYPosition + 18); // Centered on the bar
        playerHealthLabel.setFont("Monospaced-bold-20");
        playerHealthLabel.setColor(java.awt.Color.BLACK);
        add(playerHealthLabel);

        // CPU Health Bar and Label
        GLabel cpuLabel = new GLabel("CPU", getWidth() - 400, barYPosition - 15); // Positioned above the bar
        cpuLabel.setFont("Monospaced-bold-20");
        cpuLabel.setColor(java.awt.Color.BLACK); // Black for visibility
        add(cpuLabel);

        cpuHealthBarBg = new GRect(getWidth() - 400, barYPosition, 325, 25); // Wider background for better fit
        cpuHealthBarBg.setFilled(true);
        cpuHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY); // Ensure constant visibility
        add(cpuHealthBarBg);

        cpuHealthBar = new GRect(getWidth() - 400, barYPosition, 300, 25); // Foreground (Health)
        cpuHealthBar.setFilled(true);
        cpuHealthBar.setFillColor(java.awt.Color.RED);
        add(cpuHealthBar);

        cpuHealthLabel = new GLabel("125", getWidth() - 245, barYPosition + 18); // Adjusted for better centering
        cpuHealthLabel.setFont("Monospaced-bold-20");
        cpuHealthLabel.setColor(java.awt.Color.BLACK);
        add(cpuHealthLabel);

        // Timer for updating health values dynamically
        Timer healthBarUpdater = new Timer();
        healthBarUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateHealthBars();
            }
        }, 0, 50); // Refresh every 50ms
    }


    private void updateHealthBars() {
        double playerHealthPercentage = Math.max(0, powerCat.getHP() / 100.0);
        playerHealthBar.setSize(300 * playerHealthPercentage, 25);
        playerHealthLabel.setLabel(String.valueOf((int) powerCat.getHP()));

        double cpuHealthPercentage = Math.max(0, willie.getHP() / 100.0);
        cpuHealthBar.setSize(300 * cpuHealthPercentage, 25);
        cpuHealthLabel.setLabel(String.valueOf((int) willie.getHP()));
        
    }

    private void checkGameOver() {
        if (!roundCompleted) { // Prevent duplicate transitions
            if (powerCat.getHP() <= 0) {
                roundCompleted = true;
                willie.setTigerDeath();
                showResultScreen(false); // Player lost
            } else if (willie.getHP() <= 0) {
                roundCompleted = true;
                showResultScreen(true); // Player won
            }
        }
    }

    private void showResultScreen(boolean playerWon) {
        stopAllElements(); // Clean up before transitioning

        removeAll(); // Clear the current game screen
        if (playerWon) {
            GameClass.transitionToRound3(); // Transition to Round3
        } else {
            // If Tiger loses, show defeat screen
            GImage dftScreen = new GImage("media/dftScreen.gif", 0, 0);
            dftScreen.setSize(getWidth(), getHeight());
            add(dftScreen);
        }
    }

    private void stopAllElements() {
        if (waveMovementTimer != null) {
            waveMovementTimer.cancel();
        }
        willie.stopMovement();
        powerCat.stopMovement();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            togglePause(); // Toggle pause/unpause with 'P' key
        }
        if (!isPaused) { // Only handle keypress if not paused
            powerCat.keyPressed(e, this); // Forward key event to Tiger
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            showPauseOverlay(); // Display pause overlay
            pauseGame(); // Pause the game
        } else {
            removePauseOverlay(); // Remove pause overlay
            resumeGame(); // Resume the game
        }
        willie.checkPaused(isPaused); // Update Wave's pause state
    }

    private void showPauseOverlay() {
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        add(overlay);

        pauseMessage = new GLabel("Game Paused. Press 'P' to Resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) {
            remove(overlay);
            overlay = null;
        }
        if (pauseMessage != null) {
            remove(pauseMessage);
            pauseMessage = null;
        }
    }

    private void pauseGame() {
        waveMovementTimer.cancel(); // Stop Wave's movement
        powerCat.stopMovement();
        willie.stopMovement();
    }

    private void resumeGame() {
        startWaveMovement(); // Restart Wave's movement
        powerCat.startMovement();
        willie.startMovement();
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 2: Tiger vs. Wave at Chris Kjeldsen Pool!");
        requestFocus();
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round2().start(); // Launch Round 2
    }
}
