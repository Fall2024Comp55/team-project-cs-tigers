

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

