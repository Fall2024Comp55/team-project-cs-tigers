/*import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage backgroundImage; 
    private GImage welcomeGif; 
    private GRect pauseButton; 
    private GLabel pauseLabel; t
    private boolean isPaused = false; //tracks whether the game is paused
    private GRect overlay; //pause overlay for semi-transparent effect
    private GLabel pauseMessage; //pause message label
    private Hornet hornet; 

    @Override
    public void init() {
        setSize(1280, 720); // Set the size of the game window
        showWelcomeScreen(); // Show the welcome GIF before gameplay starts
    }

    private void showWelcomeScreen() {
        // Load and display the welcome GIF
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(1280, 720); // Scale to fit the screen
        add(welcomeGif);

        // Add a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); // Remove the welcome GIF
                setupContent(); // Set up the main content
            }
        }, 3000); // Display the GIF for 3 seconds
    }

    private void setupContent() {
        // Add the background image
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(1280, 720); // Scale to the window size
        add(backgroundImage);

        //initialize and add the Hornet
        hornet = new Hornet();
        add(hornet.hornet); // Add the Hornet's GImage to the canvas
        hornet.setTigerLoc(640, 360); // Set the Tiger's position for attacks
        hornet.startHornetBehavior(); // Start the Hornet's behavior (movement, attacks)

        //adds pause button
        pauseButton = new GRect(1150, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", 1175, 45);
        pauseLabel.setFont("ArcadeClassic-16");
        pauseLabel.setColor(java.awt.Color.WHITE);
        add(pauseLabel);

        //add mouse listener for pause/resume
        addMouseListeners();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            //resume the game if paused and the user clicks anywhere
            togglePause();
        } else if (pauseButton.contains(e.getX(), e.getY())) {
            // Pause the game if the pause button is clicked
            togglePause();
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // Toggle the pause state
        if (isPaused) {
            showPauseOverlay(); // Display the pause overlay
            hornet.pauseHornet(); // Pause the Hornet's behavior
        } else {
            removePauseOverlay(); // Remove the pause overlay
            hornet.resumeHornet(); // Resume the Hornet's behavior
        }
    }

    private void showPauseOverlay() {
        // Create semi-transparent overlay
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        add(overlay);

        // Add pause message
        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("ArcadeClassic-18");
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

    @Override
    public void run() {
        // Start round 1 gameplay
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
    }

    @Override
    public void startRound() {
        // Provide the required implementation for the abstract method
        init(); // initialize Round 1
        run(); // begin gameplay logic
    }

    // Main method to launch the program
    public static void main(String[] args) {
        new Round1().start(); // Start the ACM Graphics Program
    }
}


*/







//--------

// simplified commenting
// my original code without incorperating rogelios hornet class (tester!)



/*import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage hornetImage; 
    private GImage backgroundImage; 
    private GImage welcomeGif; 
    private GRect pauseButton; 
    private GLabel pauseLabel; 
    private boolean isPaused = false; //tracks whether the game is paused
    private Timer hornetMovementTimer; 
    private double hornetX = 540; 
    private double hornetY = 300; 
    private static final double HORNET_SPEED = 5.0; t
    private GRect overlay; //pause overlay for semi-transparent effect
    private GLabel pauseMessage; //pause message label

    @Override
    public void init() {
        setSize(1280, 720); // size of the game window
        showWelcomeScreen(); //show the welcome GIF before game play starts
    }
    private void showWelcomeScreen() {
        //load and display the welcome GIF
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(1280, 720); //scales to fit the screen
        add(welcomeGif);

        //adds a timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif); 
                setupContent(); //set up the main content
                startHornetMovement();
            }
        }, 3000); //displays the GIF for 3 seconds
    }

    private void setupContent() {
        //black background
        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
        blackBackground.setFilled(true);
        blackBackground.setFillColor(java.awt.Color.BLACK);
        add(blackBackground);

        //adds background image
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(1280, 720); // Scale to the window size
        add(backgroundImage);

        //hornet character TESTER
        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
        hornetImage.setSize(150, 150); // Set Hornet image size
        add(hornetImage);

        //pause 
        pauseButton = new GRect(1150, 20, 100, 40);
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", 1175, 45);
        pauseLabel.setFont("Monospaced-15");
        pauseLabel.setColor(java.awt.Color.ORANGE); //sets pause label color to white
        add(pauseLabel);

        //adds mouse listener for pause
        addMouseListeners();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            //resumes the game if it's paused and the user clicks anywhere
            togglePause();
        } else if (pauseButton.contains(e.getX(), e.getY())) {
            //pauses the game if the user clicks the pause button
            togglePause();
        }
    }

    private void togglePause() {
        isPaused = !isPaused; //toggles the pause state
        if (isPaused) {
            stopHornetMovement(); //
            showPauseOverlay(); // displays
        } else {
            removePauseOverlay(); // remove the pause overlay
            startHornetMovement(); //resume Hornet movement
        }
    }

    private void showPauseOverlay() {
        // creates a semi-transparent overlay
        overlay = new GRect(0, 0, getWidth(), getHeight());
        overlay.setFilled(true);
        overlay.setColor(new java.awt.Color(0, 0, 0, 150)); // Semi-transparent black
        add(overlay);

        // Add pause message
        pauseMessage = new GLabel("Game Paused. Click anywhere to resume.", getWidth() / 2 - 200, getHeight() / 2);
        pauseMessage.setFont("Monospaced-bold-25");
        pauseMessage.setColor(java.awt.Color.WHITE);
        add(pauseMessage);
    }

    private void removePauseOverlay() {
        if (overlay != null) {
            remove(overlay); // Remove the overlay
            overlay = null; // Reset overlay
        }
        if (pauseMessage != null) {
            remove(pauseMessage); // Remove the pause message
            pauseMessage = null; // Reset message
        }
    }

    private void startHornetMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) { // Only move the Hornet if the game is not paused
                    hornetX += HORNET_SPEED;
                    if (hornetX > getWidth()) {
                        hornetX = -150; // Reset Hornet to the left of the screen when it moves off the right edge
                    }
                    hornetImage.setLocation(hornetX, hornetY); // Update Hornet's position
                }
            }
        }, 0, 50); // Move every 50ms
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel(); // Stop the timer
        }
    }

    @Override
    public void run() {
        // Start Round 1 game play
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
    }

    @Override
    public void startRound() {
        // Provide the required implementation for the abstract method
        init(); // initialize Round 1
        run(); // begin game play logic
    }

    // Main method to launch the program
    public static void main(String[] args) {
        new Round1().start(); //starts the ACM g m
    }
}





*/

