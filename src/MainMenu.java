import acm.graphics.*;
import acm.program.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenu extends GraphicsProgram {
    private GImage startScreenGif;

    @Override
    public void run() {
        showStartScreen(); // display the start screen
    }

    private void showStartScreen() {
        // load and display the start screen gif
        startScreenGif = new GImage("media/StartScreen.gif", 0, 0);
        startScreenGif.setSize(getWidth(), getHeight()); // adjust gif size dynamically
        add(startScreenGif);

        // wait for 3 seconds and transition to round1
        Timer startScreenTimer = new Timer();
        startScreenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(startScreenGif);
                transitionToRound1(); // switch to round1
            }
        }, 5000); // delay for 3 seconds
    }

    private void transitionToRound1() {
        Round1 round1 = new Round1(); // create an instance of round1
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