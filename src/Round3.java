import acm.graphics.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round3 extends Round {
    private GImage backgroundImage, spanMap, welcomeGif; 
    private boolean isPaused = false; 
    private Timer kingMovementTimer, healthBarUpdater;
    private Tiger powerCat = new Tiger(this);
    private King triton = new King(this);

    // Health bar elements
    private GRect playerHealthBar, playerHealthBarBg; 
    private GRect tritonHealthBar, tritonHealthBarBg; 
    private GLabel playerHealthLabel, tritonHealthLabel;

    // Pause feature elements
    private GRect overlay;
    private GLabel pauseMessage;

    private boolean gameOver = false;

    @Override
    public void init() {
        removeAll();
        setSize((int) getWidth(), (int) getHeight());
        addKeyListeners();
        showSpanMap();
    }

    private void showSpanMap() {
        spanMap = new GImage("media/SpanMap.png", 0, 0);
        spanMap.setSize(getWidth(), getHeight());
        add(spanMap);

        Timer mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(spanMap);
                showWelcomeScreen();
            }
        }, 3000); 
    }

    private void showWelcomeScreen() {
        welcomeGif = new GImage("media/SpanosWelcome.gif", 0, 0);
        welcomeGif.setSize(getWidth(), getHeight());
        add(welcomeGif);

        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent();
            }
        }, 3000); 
    }

    private void setupContent() {
        backgroundImage = new GImage("media/SpanosBackground.png", 0, 0);
        backgroundImage.setSize(getWidth(), getHeight());
        add(backgroundImage);

        triton.setWindowWidth(getWidth());
        powerCat.setGroundLevel(triton.getGroundLevel());
        powerCat.setOpponent(triton.getKingLoc());
        triton.spawnKing();
        powerCat.spawnTiger();

        startKingMovement();

        setupHealthBars(); // Set up health bars
        Sound.playBackgroundMusic("media/youtube_e9fhrU7YYcQ_audio (remux).wav");

    }

    private void startKingMovement() {
        kingMovementTimer = new Timer();
        kingMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused && !gameOver) {
                    triton.setTigerLoc(powerCat.returnTiger());

                    if (powerCat.getHP() > 0) {
                        powerCat.damage(triton.getDamageGiven());
                        triton.setDamageGivenToZero();
                    }

                    if (triton.getHP() > 0) {
                        triton.damage(powerCat.getDamageGiven());
                        powerCat.setDamageGivenToZero();
                    }

                    checkGameOver();
                }
            }
        }, 0, 50); 
    }

    private void setupHealthBars() {
        int barYPosition = 50; 
        int barHeight = 25; 

        // Player's Health Bar
        GLabel playerLabel = new GLabel("PLAYER", 50, barYPosition - 10); 
        playerLabel.setFont("Monospaced-bold-18");
        playerLabel.setColor(java.awt.Color.BLACK); 
        add(playerLabel);

        playerHealthBarBg = new GRect(50, barYPosition, 300, barHeight); 
        playerHealthBarBg.setFilled(true);
        playerHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(playerHealthBarBg);

        playerHealthBar = new GRect(50, barYPosition, 300, barHeight); 
        playerHealthBar.setFilled(true);
        playerHealthBar.setFillColor(java.awt.Color.GREEN);
        add(playerHealthBar);

        playerHealthLabel = new GLabel("150", 175, barYPosition + 18); 
        playerHealthLabel.setFont("Monospaced-bold-18");
        playerHealthLabel.setColor(java.awt.Color.BLACK);
        add(playerHealthLabel);

        // Triton's Health Bar (CPU)
        GLabel tritonLabel = new GLabel("TRITON", getWidth() - 350, barYPosition - 10); 
        tritonLabel.setFont("Monospaced-bold-18");
        tritonLabel.setColor(java.awt.Color.BLACK); 
        add(tritonLabel);

        tritonHealthBarBg = new GRect(getWidth() - 350 - 25, barYPosition, 300, barHeight); 
        tritonHealthBarBg.setFilled(true);
        tritonHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(tritonHealthBarBg);

        tritonHealthBar = new GRect(getWidth() - 350 - 25, barYPosition, 300, barHeight); 
        tritonHealthBar.setFilled(true);
        tritonHealthBar.setFillColor(java.awt.Color.RED);
        add(tritonHealthBar);

        tritonHealthLabel = new GLabel("150", getWidth() - 170 - 25, barYPosition + 18); 
        tritonHealthLabel.setFont("Monospaced-bold-18");
        tritonHealthLabel.setColor(java.awt.Color.BLACK);
        add(tritonHealthLabel);

        healthBarUpdater = new Timer();
        healthBarUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateHealthBars();
            }
        }, 0, 50);
    }

    private void updateHealthBars() {
        // Update Player's Health Bar
        double playerHealthPercentage = Math.max(0, powerCat.getHP() / 150.0);
        playerHealthBar.setSize(300 * playerHealthPercentage, 25);
        playerHealthLabel.setLabel(String.valueOf((int) powerCat.getHP()));

        // Update Triton's Health Bar (from right to left)
        double tritonHealthPercentage = Math.max(0, triton.getHP() / 150.0);
        double tritonBarWidth = 300 * tritonHealthPercentage;
        tritonHealthBar.setSize(tritonBarWidth, 25);
        tritonHealthBar.setLocation(getWidth() - 350 - 25 + (300 - tritonBarWidth), tritonHealthBar.getY());
        tritonHealthLabel.setLabel(String.valueOf((int) triton.getHP()));
    }

    private void checkGameOver() {
        if (powerCat.getHP() <= 0 || triton.getHP() <= 0) {
            gameOver = true;
            showResultScreen(triton.getHP() <= 0);
        }
    }

    private void showResultScreen(boolean playerWon) {
        stopAllTimers(); 
        removeAll(); 

        GImage resultScreen = new GImage(playerWon ? "media/vicScreen.gif" : "media/dftScreen.gif", 0, 0);
        resultScreen.setSize(getWidth(), getHeight());
        add(resultScreen);

        Timer restartTimer = new Timer();
        restartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(resultScreen); 
                GameClass.startMainMenu(); 
            }
        }, 3000); 
    }

    private void stopAllTimers() {
        if (kingMovementTimer != null) kingMovementTimer.cancel();
        if (healthBarUpdater != null) healthBarUpdater.cancel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            togglePause();
        }
        if (!isPaused && !gameOver) {
            powerCat.keyPressed(e, this); 
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            showPauseOverlay();
            pauseGame();
        } else {
            removePauseOverlay();
            resumeGame();
        }
        triton.checkPaused(isPaused); 
    }

    private void showPauseOverlay() {
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150));
        add(overlay);

        pauseMessage = new GLabel("Game Paused. Press 'P' to Resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) remove(overlay);
        if (pauseMessage != null) remove(pauseMessage);
    }

    private void pauseGame() {
        stopAllTimers();
        powerCat.stopMovement();
        triton.stopMovement();
    }

    private void resumeGame() {
        startKingMovement();
        powerCat.startMovement();
        triton.startMovement();
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 3: Tiger at Alex G. Spanos Center!");
        requestFocus();
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round3().start(); 
    }
}
//tested it
