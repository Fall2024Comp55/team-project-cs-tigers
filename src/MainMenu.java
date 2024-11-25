import acm.graphics.*;
import acm.program.*;

import java.awt.Dimension;
import java.awt.event.*;

public class MainMenu extends GraphicsProgram {
    private GImage startGif; // start screen gif
    private GImage controlImage; // control screen image
    private boolean showingControls = false; // tracks if controls screen is shown

    @Override
    public void init() {
        // maximize the window on startup
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());

        // display start gif
        showStartGif();
        // add key listeners for user interaction
        addKeyListeners();
    }

    private void setSize(Dimension screenSize) {
		// TODO Auto-generated method stub
		
	}

	private void showStartGif() {
        // display the start gif
        startGif = new GImage("media/Start.gif", 0, 0);
        scaleImageToWindow(startGif); // scale gif to window size
        add(startGif); // add gif to screen
    }

    private void showControlScreen() {
        // transition to controls screen
        controlImage = new GImage("media/control.png", 0, 0);
        scaleImageToWindow(controlImage); // scale controls to window size
        add(controlImage); // add control screen
    }

    private void scaleImageToWindow(GImage image) {
        // scale image to fit the current window size
        image.setSize(getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_T) {
            if (!showingControls) {
                // move from start screen to controls
                remove(startGif); // remove start gif
                showControlScreen(); // show control screen
                showingControls = true; // update state
            } else {
                // move from controls to round 1
                remove(controlImage); // remove controls
                transitionToRound1(); // go to round 1
            }
        }
    }

    private void transitionToRound1() {
        // remove everything from the main menu
        removeAll();

        // create and initialize Round1
        Round1 round1 = new Round1();
        round1.setSize(getWidth(), getHeight()); // match the window size
        round1.startRound(); // start the first round

        // add the new Round1 to the program
        add(round1);
    }

    private void add(Round1 round1) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void run() {
        // ensures resizing works dynamically
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // dynamically scale images to window size
                if (startGif != null) scaleImageToWindow(startGif);
                if (controlImage != null) scaleImageToWindow(controlImage);
            }
        });
    }

    private void addComponentListener(ComponentAdapter componentAdapter) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        new MainMenu().start(); // start main menu
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

