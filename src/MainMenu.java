import acm.graphics.*;
import acm.program.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenu extends GraphicsProgram {
    private GImage startScreenGif; // start screen gif

    @Override
    public void init() {
        setSize(1280, 720); // set initial size of the window
        showStartScreen(); // show the start screen gif
    }

    @Override
    public void run() {
        // This method must be implemented but is not used here
        // All logic is handled in init()
    }

    private void showStartScreen() {
        // load and display the start screen gif
        startScreenGif = new GImage("media/StartScreen.gif", 0, 0);
        startScreenGif.setSize(getWidth(), getHeight()); // adjust gif size to screen size
        add(startScreenGif);

        // timer to remove the start screen and transition to round1
        Timer startScreenTimer = new Timer();
        startScreenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(startScreenGif); // remove the gif
                transitionToRound1(); // move to the next screen
            }
        }, 7000); // gif displays for 7 seconds
    }

    private void transitionToRound1() {
        Round1 round1 = new Round1(); // create an instance of round1
        round1.setSize((int) getWidth(), (int) getHeight()); // cast width and height to int
        round1.start(); // start the round1 game
    }

    public static void main(String[] args) {
        new MainMenu().start(); // start the main menu
    }
}


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