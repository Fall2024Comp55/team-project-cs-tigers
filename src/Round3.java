import acm.graphics.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round3 extends Round {
    private GImage backgroundImage; // Span map background
    private GImage welcomeGif; // Welcome GIF
    private boolean isPaused = false; // Tracks whether the game is paused
    private Timer roundTimer;
    private Timer kingMovementTimer;
    private Tiger powerCat = new Tiger(this);
    private King triton = new King(this);

    // Health bar elements
    private GRect playerHealthBar, playerHealthBarBg; // Tiger's health
    private GRect tritonHealthBar, tritonHealthBarBg; // Triton's health
    private GLabel playerHealthLabel, tritonHealthLabel;

    // Pause feature elements
    private GRect overlay;
    private GLabel pauseMessage;

    private boolean gameOver = false; // Track if the round is over

    @Override
    public void init() {
        removeAll(); // Clear any lingering components
        setSize((int) getWidth(), (int) getHeight()); // Set game window size
        addKeyListeners();
        showSpanMap(); // Show the map before starting the round
    }

    private void showSpanMap() {
        GImage spanMap = new GImage("media/SpanMap.png", 0, 0);
        spanMap.setSize(getWidth(), getHeight());
        add(spanMap);

        Timer mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(spanMap);
                showWelcomeScreen();
            }
        }, 3000); // Show map for 3 seconds
    }

    private void showWelcomeScreen() {
        // Load and display the welcome GIF
        welcomeGif = new GImage("media/SpanosWelcome.gif", 0, 0);
        welcomeGif.setSize(getWidth(), getHeight()); // Scales to fit the screen
        add(welcomeGif);

        // Add a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent(); // Set up the main content
            }
        }, 3000); // Displays the GIF for 3 seconds
    }

    private void setupContent() {
        // Span map background
        backgroundImage = new GImage("media/SpanosBackground.png", 0, 0);
        backgroundImage.setSize(getWidth(), getHeight()); // Scales to fit the screen
        add(backgroundImage);
        
        //add Tiger and King
        triton.setWindowWidth(getWidth());
        powerCat.setGroundLevel(triton.getGroundLevel());
        powerCat.setOpponent(triton.getKingLoc());
        triton.spawnKing();
        powerCat.spawnTiger();

        startKingMovement();
        setupHealthBars(); // Set up health bars
    }
//
    private void startKingMovement() {
        kingMovementTimer = new Timer();
        kingMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // Movement is only updated when not paused
                    triton.setTigerLoc(powerCat.returnTiger());
                    if (powerCat.getHP() > 0) {
                        powerCat.damage(triton.getDamageGiven());
                        triton.setDamageGivenToZero();
                    }

                    if (triton.getHP() > 0) {
                        triton.damage(powerCat.getDamageGiven());
                        powerCat.setDamageGivenToZero();
                    }

                    checkGameOver(); // Check if the game has ended
                }
            }
        }, 0, 50); // Updates every 50ms
    }

    private void setupHealthBars() {
        int barYPosition = 50; // Y position for health bars
        int barHeight = 25; // Height of the bars

        // Player Health Bar and Label
        GLabel playerLabel = new GLabel("PLAYER", 50, barYPosition - 10); // Label positioned above the bar
        playerLabel.setFont("Monospaced-bold-18");
        playerLabel.setColor(java.awt.Color.BLACK); // Black for better visibility
        add(playerLabel);

        playerHealthBarBg = new GRect(50, barYPosition, 300, barHeight); // Background
        playerHealthBarBg.setFilled(true);
        playerHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(playerHealthBarBg);

        playerHealthBar = new GRect(50, barYPosition, 300, barHeight); // Foreground (Health)
        playerHealthBar.setFilled(true);
        playerHealthBar.setFillColor(java.awt.Color.GREEN);
        add(playerHealthBar);

        playerHealthLabel = new GLabel("100", 175, barYPosition + 18); // Centered on the bar
        playerHealthLabel.setFont("Monospaced-bold-18");
        playerHealthLabel.setColor(java.awt.Color.BLACK);
        add(playerHealthLabel);

        // Triton Health Bar and Label
        GLabel tritonLabel = new GLabel("TRITON", getWidth() - 350, barYPosition - 10); // Label positioned above the bar
        tritonLabel.setFont("Monospaced-bold-18");
        tritonLabel.setColor(java.awt.Color.BLACK); // Black for better visibility
        add(tritonLabel);

        tritonHealthBarBg = new GRect(getWidth() - 350 - 25, barYPosition, 325, barHeight); // Adjusted position and width
        tritonHealthBarBg.setFilled(true);
        tritonHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(tritonHealthBarBg);

        tritonHealthBar = new GRect(getWidth() - 350 - 25, barYPosition, 300, barHeight); // Foreground (Health)
        tritonHealthBar.setFilled(true);
        tritonHealthBar.setFillColor(java.awt.Color.RED);
        add(tritonHealthBar);

        tritonHealthLabel = new GLabel("150", getWidth() - 170 - 25, barYPosition + 18); // Centered on the bar
        tritonHealthLabel.setFont("Monospaced-bold-18");
        tritonHealthLabel.setColor(java.awt.Color.BLACK);
        add(tritonHealthLabel);

        // Timer for updating health dynamically
        Timer healthBarUpdater = new Timer();
        healthBarUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateHealthBars();
            }
        }, 0, 50); // Refresh every 50ms
    }


    private void updateHealthBars() {
        // Update Tiger health bar
        double playerHealthPercentage = Math.max(0, powerCat.getHP() / 100.0);
        playerHealthBar.setSize(300 * playerHealthPercentage, 25);
        playerHealthLabel.setLabel(String.valueOf((int) powerCat.getHP()));

        // Update Triton health bar
        double tritonHealthPercentage = Math.max(0, triton.getHP() / 100.0);
        tritonHealthBar.setSize(300 * tritonHealthPercentage, 25);
        tritonHealthLabel.setLabel(String.valueOf((int) triton.getHP()));

        // Check for victory condition
        if (triton.getHP() <= 0) { // Check if Triton's health is 0 or less
            triton.setHP(0); // Clamp Triton's health to 0
            showVictoryScreen(); // Trigger the victory screen
        }
    }

    private void checkGameOver() {
        if (powerCat.getHP() <= 0) {
            showDefeatScreen(); // Player lost
        }
    }

    private void showDefeatScreen() {
        gameOver = true; // Mark game as over
        stopAllTimers(); // Stop all timers
        removeAll(); // Clear the current game screen
        GImage dftScreen = new GImage("media/dftScreen.gif", 0, 0);
        dftScreen.setSize(getWidth(), getHeight());
        add(dftScreen);
    }

    private void showVictoryScreen() {
        gameOver = true; // Mark game as over
        stopAllTimers(); // Stop all active timers
        removeAll(); // Clear the current game screen

        // Display victory screen
        GImage vicScreen = new GImage("media/vicScreen.gif", 0, 0);
        vicScreen.setSize(getWidth(), getHeight()); // Scale to fit the screen
        add(vicScreen);
    }

    private void stopAllTimers() {
        if (kingMovementTimer != null) kingMovementTimer.cancel();
        if (roundTimer != null) roundTimer.cancel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            togglePause(); // Toggle pause/unpause with 'P' key
        }
        if (!isPaused && !gameOver) { // Only handle keypress if not paused or game over
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
        if (roundTimer != null) {
            roundTimer.cancel(); // Stop the timer
        }
        powerCat.stopMovement(); // Stop Tiger movement
    }

    private void resumeGame() {
        startRoundTimer(); // Restart the timer
        powerCat.startMovement(); // Resume Tiger movement
    }

    private void startRoundTimer() {
        roundTimer = new Timer();
        roundTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused && !gameOver) {
                    checkGameOver(); // Check if the game has ended
                }
            }
        }, 0, 50); // Update every 50ms
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 3: Tiger at Alex G. Spanos Center!");
        startRoundTimer();
        requestFocus();
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round3().start(); // Launch Round 3
    }
}
