import acm.graphics.*;
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

    @Override
    public void init() {
        setSize(1280, 720); // set game window size
        showBurnsMap(); // show burns map
    }

    private void showBurnsMap() {
        // display burns map
        GImage burnsMap = new GImage("media/BurnsMap.png", 0, 0);
        burnsMap.setSize(1280, 720);
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
        welcomeGif.setSize(1280, 720);
        add(welcomeGif);

        // setup game content after gif
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent();
                startHornetMovement();
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
        backgroundImage.setSize(1280, 720);
        add(backgroundImage);

        // hornet character
        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
        hornetImage.setSize(150, 150);
        add(hornetImage);

        // add pause button
        pauseMenu.addPauseButton(this);

        addMouseListeners(); // enable interactions
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            pauseMenu.removePauseMenu(this);
            isPaused = false; 
            startHornetMovement();
        } else if (pauseMenu.isPauseButtonClicked(e.getX(), e.getY())) {
            pauseMenu.showPauseMenu(this);
            isPaused = true; 
            stopHornetMovement();
        }
    }

    private void startHornetMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    hornetX += HORNET_SPEED;
                    if (hornetX > getWidth()) hornetX = -150;
                    hornetImage.setLocation(hornetX, hornetY);
                }
            }
        }, 0, 50);
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) hornetMovementTimer.cancel();
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
