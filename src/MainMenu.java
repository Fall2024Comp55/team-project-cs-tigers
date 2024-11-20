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
//        showStartScreen(); // show the start screen gif or background
//        addKeyListeners(); // listen for key presses
//    }
//
//    private void showStartScreen() {
//        // load and display the start screen gif
//        startScreenGif = new GImage("media/StartScreen.gif", 0, 0);
//        startScreenGif.setSize(getWidth(), getHeight()); // dynamically adjust size
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
//            transitionToRound1(); // move to the next class
//        }
//    }
//
//    private void transitionToRound1() {
//        Round1 round1 = new Round1();
//        round1.setSize(getWidth(), getHeight()); // dynamically adjust size
//        round1.start(); // start Round1 gameplay
//    }
//
//    public static void main(String[] args) {
//        new MainMenu().start(); // start the main menu
//    }
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//}


/* not letting me create a new class so i am taking over BallLauncher.java
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BallLauncher extends GraphicsProgram{
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int SIZE = 25;
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		addMouseListeners();
	}
	
	public void mousePressed(MouseEvent e) {
		GOval ball = makeBall(SIZE/2, e.getY());
		add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	public static void main(String[] args) {
		new BallLauncher().start();
	}

}
*/