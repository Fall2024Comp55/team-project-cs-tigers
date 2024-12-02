import acm.graphics.*;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage backgroundImage;
    private GImage welcomeGif;
    private PauseMenu pauseMenu = new PauseMenu(); // pause menu helper
    private boolean isPaused = false;
    private Timer hornetMovementTimer;
    private static final double HORNET_SPEED = 5.0;
    private Hornet herkie = new Hornet(this);
    private Tiger powerCat = new Tiger(this);

    // Health bar elements
    private GRect playerHealthBar, playerHealthBarBg; // Tiger's health
    private GRect cpuHealthBar, cpuHealthBarBg; // Hornet's health
    private GLabel playerHealthLabel, cpuHealthLabel;

    @Override
    public void init() {
        setSize(1280, 720); // Set game window size
        addKeyListeners();
        showBurnsMap(); // Show Burns map
    }

    private void showBurnsMap() {
        GImage burnsMap = new GImage("media/BurnsMap.png", 0, 0);
        burnsMap.setSize(1920, 1080);
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
        welcomeGif.setSize(1920, 1080);
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
        backgroundImage.setSize(1920, 1080);
        add(backgroundImage);

        herkie.spawnHornet();
        Timer startHerkie = new Timer();
        startHerkie.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                herkie.setTigerLoc(powerCat.returnTiger());
                powerCat.setOpponent(herkie.getHornetLoc());
                if (!isPaused && powerCat.getHP() > 0) {
                    powerCat.damage(herkie.getDamageGiven());
                    herkie.setDamageGivenToZero();
                }

                if (!isPaused && herkie.getHP() > 0) {
                    herkie.damage(powerCat.getDamageGiven());
                    powerCat.setDamageGivenToZero();
                }
                
                if (powerCat != null && herkie != null) {
                    GImage waveLoc = herkie.getHornetLoc();
                    if (waveLoc != null) {
                        powerCat.checkSide(waveLoc); // Call Tiger's checkSide method
                    }
                }

                checkGameOver(); // Check if the game has ended
            }
        }, 0, 50);
        

        powerCat.spawnTiger();
        powerCat.setGroundLevel(herkie.getGroundLevel());
        setupHealthBars();
        addMouseListeners(); // Enable interactions
    }

    private void setupHealthBars() {
        // Player (Tiger) health bar
        playerHealthBarBg = new GRect(50, 20, 300, 25); // Background
        playerHealthBarBg.setFilled(true);
        playerHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(playerHealthBarBg);

        playerHealthBar = new GRect(50, 20, 300, 25); // Foreground (Health)
        playerHealthBar.setFilled(true);
        playerHealthBar.setFillColor(java.awt.Color.GREEN);
        add(playerHealthBar);

        playerHealthLabel = new GLabel("100", 360, 40); // Health value label
        playerHealthLabel.setFont("Monospaced-bold-20");
        playerHealthLabel.setColor(java.awt.Color.WHITE);
        add(playerHealthLabel);

        // CPU (Hornet) health bar
        cpuHealthBarBg = new GRect(getWidth() - 350, 20, 300, 25); // Background
        cpuHealthBarBg.setFilled(true);
        cpuHealthBarBg.setFillColor(java.awt.Color.DARK_GRAY);
        add(cpuHealthBarBg);

        cpuHealthBar = new GRect(getWidth() - 350, 20, 300, 25); // Foreground (Health)
        cpuHealthBar.setFilled(true);
        cpuHealthBar.setFillColor(java.awt.Color.RED);
        add(cpuHealthBar);

        cpuHealthLabel = new GLabel("100", getWidth() - 400, 40); // Health value label
        cpuHealthLabel.setFont("Monospaced-bold-20");
        cpuHealthLabel.setColor(java.awt.Color.WHITE);
        add(cpuHealthLabel);

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

    private void checkGameOver() {
        if (powerCat.getHP() <= 0) {
            showResultScreen(false); // Player lost
        } else if (herkie.getHP() <= 0) {
            showResultScreen(true); // Player won
        }
    }

    private void showResultScreen(boolean playerWon) {
        removeAll(); // Clear the current game screen

        ResultScreen resultScreen = new ResultScreen();
        resultScreen.setSize(getWidth(), getHeight()); // Match current screen size
        resultScreen.start(); // Start the result screen
        resultScreen.showResult(playerWon);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) { // Pause toggled via 'P'
            isPaused = !isPaused;
            if (isPaused) {
                pauseMenu.showPauseMenu(this);
            } else {
                pauseMenu.removePauseMenu(this);
            }
        }
        powerCat.keyPressed(e, this); // Forward key event to Tiger
    }

    @Override
    public void run() {
        updateCaption("welcome to round 1: tiger vs. hornet at burn's tower!");
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round1().start(); // Launch round 1
    }

	public void setSize(double width, double height) {
		// TODO Auto-generated method stub
		
	}
}


/*import acm.graphics.*;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage hornetImage; 
    private GImage backgroundImage; 
    private GImage welcomeGif; 
    private PauseMenu pauseMenu = new PauseMenu(); // pause menu helper
    private boolean isPaused = false; 
    private Timer hornetMovementTimer; 
    private double hornetX = 540; 
    private double hornetY = 300; 
    private static final double HORNET_SPEED = 5.0;
    private Hornet herkie = new Hornet(this);
    private Tiger powerCat = new Tiger(this);
    
    @Override
    public void init() {
        setSize(1280, 720); // set game window size
        addKeyListeners();
        showBurnsMap(); // show burns map
    }

    private void showBurnsMap() {
        // display burns map
        GImage burnsMap = new GImage("media/BurnsMap.png", 0, 0);
        burnsMap.setSize(1920, 1080);
        add(burnsMap);

        // transition to welcome screen
        Timer mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(burnsMap);
                showWelcomeScreen();
            }
        }, 3000); // show map for 3 seconds
    }

    private void showWelcomeScreen() {
        // display burns welcome gif
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(1920, 1080);
        add(welcomeGif);

        // setup game content after gif
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent();
            }
        }, 3000); // show gif for 3 seconds
    }

    private void setupContent() {
        // black background
        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
        blackBackground.setFilled(true);
        blackBackground.setFillColor(java.awt.Color.BLACK);
        add(blackBackground);

        // burns tower background
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(1920, 1080);
        add(backgroundImage);

        // hornet character
        herkie.spawnHornet();
        Timer startHerkie = new Timer();
        startHerkie.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		herkie.setTigerLoc(powerCat.returnTiger());
        		if (!isPaused && powerCat.getHP() > 0) {
                    powerCat.damage(herkie.getDamageGiven());
                    herkie.setDamageGivenToZero();
                    System.out.println("Health: " + powerCat.getHP());
                }
                
                if (!isPaused && herkie.getHP() > 0) {
                    herkie.damage(powerCat.getDamageGiven());
                    powerCat.setDamageGivenToZero();
                    System.out.println("Health: " + herkie.getHP());
                }
                
                if (powerCat != null && herkie != null) {
                    GImage hornetLoc = herkie.getHornetLoc();
                    if (hornetLoc != null) {
                        powerCat.checkSide(hornetLoc); // Call Tiger's checkSide method
                    }
                }
        	}
        }, 0, 50);
        
//        Timer damageCheck = new Timer();
//        damageCheck.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (!isPaused && powerCat.getHP() > 0) {
//                    powerCat.damage(herkie.getDamageGiven());
//                    herkie.setDamageGivenToZero();
//                    System.out.println("Health: " + powerCat.getHP());
//                }
//                
//                if (!isPaused && herkie.getHP() > 0) {
//                    herkie.damage(powerCat.getDamageGiven());
//                    powerCat.setDamageGivenToZero();
//                    System.out.println("Health: " + herkie.getHP());
//                }
//            }
//        }, 0, 500); 

        powerCat.spawnTiger();
        // add pause button
        pauseMenu.addPauseButton(this);

        addMouseListeners(); // enable interactions
        
//        Timer positionCheckTimer = new Timer();
//        positionCheckTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                // Ensure both tiger and hornet are initialized
//                if (powerCat != null && herkie != null) {
//                    GImage hornetLoc = herkie.getHornetLoc();
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
            pauseMenu.removePauseMenu(this);
            isPaused = false; 
        } else if (pauseMenu.isPauseButtonClicked(e.getX(), e.getY())) {
            pauseMenu.showPauseMenu(this);
            isPaused = true; 
        }
        herkie.checkPaused(isPaused);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        powerCat.keyPressed(e, this); // Pass the event to the Tiger object
    }

   
    
    @Override
    public void run() {
        // start round 1
        updateCaption("welcome to round 1: tiger vs. hornet at burn's tower!");
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    public static void main(String[] args) {
        new Round1().start(); // launch round 1
    }

	public void setSize(double width, double height) {
		// TODO Auto-generated method stub
		
	}

}
*/

//import java.util.Timer;
//import java.util.TimerTask;
//// WILL EDIT THIS SO MAINMENU DIRECTS HERE
//// ADD BURNS T MAP
//public class Round1 extends Round {
//    private GImage hornetImage; 
//    private GImage backgroundImage; 
//    private GImage welcomeGif; 
//    private GRect pauseButton; 
//    private GLabel pauseLabel; 
//    private boolean isPaused = false; //tracks whether the game is paused
//    private Timer hornetMovementTimer; 
//    private double hornetX = 540; 
//    private double hornetY = 300; 
//    private static final double HORNET_SPEED = 5.0; 
//    private GRect overlay; //pause overlay for semi-transparent effect
//    private GLabel pauseMessage; //pause message label
//
//    @Override
//    public void init() {
//        setSize(1280, 720); // size of the game window
//        showWelcomeScreen(); //show the welcome GIF before game play starts
//    }
//    private void showWelcomeScreen() {
//        //load and display the welcome GIF
//        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
//        welcomeGif.setSize(1280, 720); //scales to fit the screen
//        add(welcomeGif);
//
//        //adds a timer to remove the welcome screen and start the round
//        Timer welcomeTimer = new Timer();
//        welcomeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                remove(welcomeGif); 
//                setupContent(); //set up the main content
//                startHornetMovement();
//            }
//        }, 3000); //displays the GIF for 3 seconds
//    }
//
//    private void setupContent() {
//        //black background
//        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
//        blackBackground.setFilled(true);
//        blackBackground.setFillColor(java.awt.Color.BLACK);
//        add(blackBackground);
//
//        //adds background image
//        backgroundImage = new GImage("media/burnsTBackground.png", 0, 0);
//        backgroundImage.setSize(1280, 720); // Scale to the window size
//        add(backgroundImage);
//
//        //hornet character TESTER
//        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
//        hornetImage.setSize(150, 150); // Set Hornet image size
//        add(hornetImage);
//
//        //pause 
//        pauseButton = new GRect(1150, 20, 100, 40);
//        pauseButton.setFilled(true);
//        pauseButton.setFillColor(java.awt.Color.BLACK);
//        add(pauseButton);
//
//        pauseLabel = new GLabel("Pause", 1175, 45);
//        pauseLabel.setFont("Monospaced-15");
//        pauseLabel.setColor(java.awt.Color.ORANGE); //sets pause label color to white
//        add(pauseLabel);
//
//        //adds mouse listener for pause
//        addMouseListeners();
//    }
//
//    @Override
//    public void mousePressed(java.awt.event.MouseEvent e) {
//        if (isPaused) {
//            //resumes the game if it's paused and the user clicks anywhere
//            togglePause();
//        } else if (pauseButton.contains(e.getX(), e.getY())) {
//            //pauses the game if the user clicks the pause button
//            togglePause();
//        }
//    }
//
//    private void togglePause() {
//        isPaused = !isPaused; //toggles the pause state
//        if (isPaused) {
//            stopHornetMovement(); //
//            showPauseOverlay(); // displays
//        } else {
//            removePauseOverlay(); // remove the pause overlay
//            startHornetMovement(); //resume Hornet movement
//        }
//    }
//
//    private void showPauseOverlay() {
//        // creates a semi-transparent overlay
//        overlay = new GRect(0, 0, getWidth(), getHeight());
//        overlay.setFilled(true);
//        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
//        add(overlay);
//
//        // Add pause message
//        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
//        pauseMessage.setFont("Monospaced-bold-25");
//        pauseMessage.setColor(java.awt.Color.WHITE);
//        add(pauseMessage);
//    }
//
//    private void removePauseOverlay() {
//        if (overlay != null) {
//            remove(overlay); // remove the overlay
//            overlay = null; // resets overlay
//        }
//        if (pauseMessage != null) {
//            remove(pauseMessage); // removes the pause message
//            pauseMessage = null; //reset message
//        }
//    }
///// pause movement notes here 
//    private void startHornetMovement() {
//        hornetMovementTimer = new Timer();
//        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (!isPaused) { // only move the hornet if the game is not paused
//                    hornetX += HORNET_SPEED;
//                    if (hornetX > getWidth()) {
//                        hornetX = -150; // reset hornet to the left of the screen when it moves off the right edge
//                    }
//                    hornetImage.setLocation(hornetX, hornetY); // updates hornet's position
//                }
//            }
//        }, 0, 50); //move every 50ms
//    }
//
//    private void stopHornetMovement() {
//        if (hornetMovementTimer != null) {
//            hornetMovementTimer.cancel(); // stops the timer
//        }
//    }
//
//    @Override
//    public void run() {
//        // this starts round 1 game play
//        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
//    }
//
//    @Override
//    public void startRound() {
//        ///provide required implementation for the abstract method
//        init(); // initialize round 1
//        run(); // begin game play logic
//    }
//
//    // main method to launch the program
//    public static void main(String[] args) {
//        new Round1().start(); //starts the ACM g m
//    }
//}

//how the pausing  works
//the bee moves because of TimerTask that updates its position every 50ms.
//When the game is paused (isPaused is true), the bee should stop moving. 
//used the stopHornetMovement() function to cancel the TimerTask and effectively "freeze" the bee
//the bee resumes moving when startHornetMovement() is called, reinitializing the TimerTask
//
