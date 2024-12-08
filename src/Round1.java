import acm.graphics.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage backgroundImage;
    private GImage welcomeGif;
    private boolean isPaused = false;
    private Timer hornetMovementTimer;
    private Hornet herkie = new Hornet(this);
    private Tiger powerCat = new Tiger(this);

    // Health bar elements
    private GRect playerHealthBar, playerHealthBarBg; // Tiger's health
    private GRect cpuHealthBar, cpuHealthBarBg; // Hornet's health
    private GLabel playerHealthLabel, cpuHealthLabel;

    // Pause feature elements
    private GRect overlay;
    private GLabel pauseMessage;


    @Override
    public void init() {
        removeAll(); // Clear any lingering components
        setSize(getWidth(), getHeight()); // Set game window size
        addKeyListeners(); // Enable key listeners
        showBurnsMap(); // Display Burns map
    }


    private void showBurnsMap() {
        GImage burnsMap = new GImage("media/BurnsMap.png", 0, 0);
        burnsMap.setSize(getWidth(), getHeight());
        add(burnsMap);

        Timer mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(burnsMap);
                showWelcomeScreen();
            }
        }, 3000); // Show map for 3 seconds
    }

    private void showWelcomeScreen() {
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(getWidth(), getHeight());
        add(welcomeGif);

        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent();
            }
        }, 3000); // Show GIF for 3 seconds
    }

    private void setupContent() {
        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
        blackBackground.setFilled(true);
        blackBackground.setFillColor(java.awt.Color.BLACK);
        add(blackBackground);

        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(getWidth(), getHeight());
        add(backgroundImage);

        herkie.spawnHornet();
        startHerkieMovement(); // Start Hornet's movement when content is set up
        herkie.setWindowWidth(getWidth());
        
        powerCat.setGroundLevel(herkie.getGroundLevel());
        powerCat.spawnTiger();
        setupHealthBars();
        addMouseListeners(); // Enable interactions
        Sound.playBackgroundMusic("media/youtube_LsIWRHBKeh0_audio (remux).wav");
    }

    private void startHerkieMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // Movement is only updated when not paused
                    herkie.setTigerLoc(powerCat.returnTiger());
                    powerCat.setOpponent(herkie.getHornetLoc());
                    if (powerCat.getHP() > 0) {
                        powerCat.damage(herkie.getDamageGiven());
                        herkie.setDamageGivenToZero();
                    }

                    if (herkie.getHP() > 0) {
                        herkie.damage(powerCat.getDamageGiven());
                        powerCat.setDamageGivenToZero();
                    }

                    if (powerCat != null && herkie != null) {
                        GImage waveLoc = herkie.getHornetLoc();
                        if (waveLoc != null) {
                            powerCat.checkSide(waveLoc); // Call Tiger's checkSide method
                        }
                    }

                    //checkGameOver(); // Check if the game has ended
                    
                    if(checkGameOver()) {
                    	cancel();
                    }
                }
            }
        }, 0, 50); // Updates every 50ms
    }
  
    private void setupHealthBars() {
        int barYPosition = 60; // Slightly higher position for better visibility

        // PLAYER Health Bar and Label
        GLabel playerLabel = new GLabel("PLAYER", 50, barYPosition - 20); // Positioned above the bar
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

        playerHealthLabel = new GLabel("100", 50 + 150 - 15, barYPosition + 18); // Centered inside the bar
        playerHealthLabel.setFont("Monospaced-bold-20");
        playerHealthLabel.setColor(java.awt.Color.BLACK);
        add(playerHealthLabel);

        // CPU (Hornet) Health Bar and Label
        GLabel cpuLabel = new GLabel("CPU", getWidth() - 400, barYPosition - 20); // Positioned above the bar
        cpuLabel.setFont("Monospaced-bold-20");
        cpuLabel.setColor(java.awt.Color.BLACK); // Black for visibility
        add(cpuLabel);

        cpuHealthBarBg = new GRect(getWidth() - 400, barYPosition, 300, 25); // Wider background for better fit
        cpuHealthBarBg.setFilled(true);
        cpuHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY); // Ensure constant visibility
        add(cpuHealthBarBg);

        cpuHealthBar = new GRect(getWidth() - 400, barYPosition, 300, 25); // Foreground (Health)
        cpuHealthBar.setFilled(true);
        cpuHealthBar.setFillColor(java.awt.Color.RED);
        add(cpuHealthBar);

        cpuHealthLabel = new GLabel("100", getWidth() - 400 + 150 - 15, barYPosition + 18); // Centered inside the bar
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

        double cpuHealthPercentage = Math.max(0, herkie.getHP() / 100.0);
        cpuHealthBar.setSize(300 * cpuHealthPercentage, 25);
        cpuHealthLabel.setLabel(String.valueOf((int) herkie.getHP()));
    }

    private boolean checkGameOver() {
        if (powerCat.getHP() <= 0) {
            showResultScreen(false); // Player lost
            return true;
        } else if (herkie.getHP() <= 0) {
            showResultScreen(true); // Player won
            return true;
        }
        return false;
    }

    private void showResultScreen(boolean playerWon) {
        stopAllTimers();
        stopAllAnimations();
        removeAll(); // Clear current round

        if (playerWon) {
            GameClass.transitionToRound2(true);  // Proceed to next round
        } else {

            // If Tiger loses, show defeat screen
        	Sound.stopBackgroundMusic();
        	herkie.setTigerDeath();
        	GImage dftScreen = new GImage("media/dftScreen.gif", 0, 0);
            dftScreen.setSize(getWidth(), getHeight());
            add(dftScreen);

            Timer restartTimer = new Timer();
            restartTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    remove(dftScreen); // Remove defeat screen
                    GameClass.startMainMenu();  // Return to Main Menu after 3 seconds
                }
            }, 3000); 
        }
    }

 // Add these methods near the bottom, after existing helper methods:
    private void stopAllTimers() {
        if (hornetMovementTimer != null) hornetMovementTimer.cancel();
    }

    private void stopAllAnimations() {
        if (powerCat != null) powerCat.stopMovement();
        if (herkie != null) herkie.stopMovement();
    }



            // Timer to go back to Main Menu after 3 seconds of showing defeat screen
//            Timer restartTimer = new Timer();
//            restartTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    remove(dftScreen); // Remove defeat screen
//                    startRound(); // Go back to the main menu
//                }
//            }, 3000); // Wait for 3 seconds before transitioning

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
        herkie.checkPaused(isPaused); // Update Hornet's pause state
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
        // Pauses movement and action by stopping the timers and interactions
        hornetMovementTimer.cancel();
        powerCat.stopMovement();
        herkie.stopMovement();
    }

    private void resumeGame() {
        // Resumes movement and action by starting the timers and interactions
        startHerkieMovement();
        powerCat.startMovement();
        herkie.startMovement();
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
        requestFocus();
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round1().start(); // Launch Round 1
    }

    public void setSize(double width, double height) {
    }
}

