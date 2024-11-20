import acm.graphics.*;
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private GImage hornetImage;
    private GImage backgroundImage;
    private GImage welcomeGif;
    private GRect pauseButton;
    private GLabel pauseLabel;
    private boolean isPaused = false;
    private Timer hornetMovementTimer;
    private double hornetX = 540;
    private double hornetY = 300;
    private static final double HORNET_SPEED = 5.0;
    private GRect overlay;
    private GLabel pauseMessage;

    @Override
    public void init() {
        setSize(1280, 720); // set initial size of the game window
        showWelcomeScreen(); // show the welcome gif before gameplay starts
    }

    private void showWelcomeScreen() {
        // load and display the welcome gif
        welcomeGif = new GImage("media/BurnsTWelcome.gif", 0, 0);
        welcomeGif.setSize(getWidth(), getHeight()); // adjust to current screen size
        add(welcomeGif);

        // timer to remove the welcome screen and start the round
        Timer welcomeTimer = new Timer();
        welcomeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(welcomeGif);
                setupContent(); // set up the main content
                startHornetMovement();
            }
        }, 3000); // display the gif for 3 seconds
    }

    private void setupContent() {
        // add black background
        GRect blackBackground = new GRect(0, 0, getWidth(), getHeight());
        blackBackground.setFilled(true);
        blackBackground.setFillColor(java.awt.Color.BLACK);
        add(blackBackground);

        // add background image and make it dynamic
        backgroundImage = new GImage("media/BurnsTBackground.png", 0, 0);
        backgroundImage.setSize(getWidth(), getHeight()); // adjust to current screen size
        add(backgroundImage);

        // add hornet character
        hornetImage = new GImage("media/HornetPrototype.gif", hornetX, hornetY);
        hornetImage.setSize(150, 150);
        add(hornetImage);

        // add pause button
        pauseButton = new GRect(getWidth() - 130, 20, 100, 40); // adjust pause button position dynamically
        pauseButton.setFilled(true);
        pauseButton.setFillColor(java.awt.Color.BLACK);
        add(pauseButton);

        pauseLabel = new GLabel("Pause", getWidth() - 110, 45); // adjust label position dynamically
        pauseLabel.setFont("Monospaced-15");
        pauseLabel.setColor(java.awt.Color.ORANGE);
        add(pauseLabel);

        addMouseListeners(); // add mouse listener for pause functionality
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isPaused) {
            togglePause(); // resume the game if paused
        } else if (pauseButton.contains(e.getX(), e.getY())) {
            togglePause(); // pause the game if the pause button is clicked
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // toggle pause state
        if (isPaused) {
            stopHornetMovement();
            showPauseOverlay();
        } else {
            removePauseOverlay();
            startHornetMovement();
        }
    }

    private void showPauseOverlay() {
        // create a semi-transparent overlay
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
            overlay = null;
        }
        if (pauseMessage != null) {
            remove(pauseMessage);
            pauseMessage = null;
        }
    }

    private void startHornetMovement() {
        hornetMovementTimer = new Timer();
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    hornetX += HORNET_SPEED;
                    if (hornetX > getWidth()) {
                        hornetX = -150;
                    }
                    hornetImage.setLocation(hornetX, hornetY);
                }
            }
        }, 0, 50);
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel();
        }
    }

    @Override
    public void run() {
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
    }

    @Override
    public void startRound() {
        init();
        run();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (backgroundImage != null) {
            backgroundImage.setSize(getWidth(), getHeight()); // resize background dynamically
        }
        if (welcomeGif != null) {
            welcomeGif.setSize(getWidth(), getHeight()); // resize welcome gif dynamically
        }
    }

    public static void main(String[] args) {
        new Round1().start();
    }
}

