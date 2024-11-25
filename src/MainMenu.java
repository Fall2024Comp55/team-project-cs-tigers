import acm.graphics.*;
import acm.program.*;
import java.awt.event.KeyEvent;

public class MainMenu extends GraphicsProgram {
    private GImage startGif;

    @Override
    public void init() {
        setSize(1280, 720); // Set the window size
        showStartScreen(); // Show the Start.gif
        addKeyListeners(); // Add key listeners for keyboard input
    }

    private void showStartScreen() {
        // Display the Start.gif
        startGif = new GImage("media/Start.gif", 0, 0);
        startGif.setSize(getWidth(), getHeight()); // Scale to fit the screen
        add(startGif);
        System.out.println("Start screen displayed. Waiting for 'T'...");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar()); // Debugging key press
        if (e.getKeyCode() == KeyEvent.VK_T) {
            System.out.println("'T' detected. Proceeding to next screen...");
            remove(startGif); // Remove the Start.gif
            transitionToNextScreen(); // Placeholder for the next screen logic
        }
    }

    private void transitionToNextScreen() {
        // Example logic for transitioning to the next screen
        GLabel nextScreenMessage = new GLabel("Welcome to the next screen!", getWidth() / 2 - 100, getHeight() / 2);
        nextScreenMessage.setFont("Monospaced-bold-20");
        nextScreenMessage.setColor(java.awt.Color.RED);
        add(nextScreenMessage);
    }

    @Override
    public void run() {
        // Required by GraphicsProgram; can remain empty for now
    }

    public static void main(String[] args) {
        new MainMenu().start();
    }
}


// ibrahims previous code 
//import acm.graphics.*;
//import acm.program.*;
//import java.awt.event.KeyEvent;
//
//public class MainMenu extends GraphicsProgram {
//    private GImage startScreenGif; // gif for the main menu
//    private GLabel pressTMessage; // message to prompt user to start
//
//    @Override
//    public void init() {
//        setSize(1280, 720); // set initial size of the main menu window
//        showStartScreen(); // show the start screen gif
//        addKeyListeners(); // listen for key presses
//    }
//
//    private void showStartScreen() {
//        // load and display the start screen gif
//        startScreenGif = new GImage("media/StartScreen.gif", 0, 0);
//        startScreenGif.setSize(getWidth(), getHeight());
//        add(startScreenGif);
//
//        // add a message prompting the user to press 'T'
//        pressTMessage = new GLabel("Press 'T' to Start", getWidth() / 2 - 100, getHeight() - 50);
//        pressTMessage.setFont("Monospaced-bold-20");
//        pressTMessage.setColor(java.awt.Color.WHITE);
//        add(pressTMessage);
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_T) {
//            // user presses 'T', transition to Round1
//            removeAll(); // clear the main menu screen
//            transitionToRound1(); // move to the next screen
//        }
//    }
//
//    private void transitionToRound1() {
//        Round1 round1 = new Round1();
//        round1.setSize(getWidth(), getHeight()); // dynamically adjust size
//        round1.init(); // initialize Round1
//        round1.start(); // start Round1 gameplay
//    }
//
//    public static void main(String[] args) {
//        new MainMenu().start(); // start the main menu
//    }
//}

