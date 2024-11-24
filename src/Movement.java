import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import javax.swing.*;

/*
								
								this class is no longer used anymore	
								functions of this class have been moved to the tiger class

*/

public class Movement extends GraphicsProgram implements ActionListener {
	public static final int PROGRAM_HEIGHT = 600; //temp value for testing
	public static final int PROGRAM_WIDTH = 800; // temp values for testing
	private GImage tigerTesting = new GImage("TigerPlaceHolder.png", 300, 100); //image for movement testing
	private GRect test = new GRect(100, 100 , 200, 200);
	private static GImage tigerTestingConst = new GImage("TigerPlaceHolder.png", 300, 100); //image for movement testing
	private int velocityY = 0; // Vertical velocity (used for jump and gravity)
	private static final int GRAVITY = 1; // Gravity constant (affects velocity)
	private static final int JUMP_STRENGTH = -25; // Strength of the jump // must be a negative number// value to be determined
	private boolean isJumping = false;
	private static final int CHARACTER_WIDTH = (int) (tigerTestingConst.getWidth() / 3);
	private static final int CHARACTER_HEIGHT = (int) (tigerTestingConst.getHeight() / 3);
	private static final int FLOOR = 500;
	private Timer jumpTimer = new Timer(20, this);
	
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case KeyEvent.VK_UP:
		 		if (!isJumping) {
		            //velocityY = JUMP_STRENGTH;  // Apply upward velocity
		           // isJumping = true;  // Mark the character as jumping
		 			jump();
		 			System.out.println("line30");
		 		}
		 		break;
		// case KeyEvent.VK_DOWN:
			// tigerTesting.move(0, 5); // Move down
            // break;
         case KeyEvent.VK_LEFT:
	         test.move(-15, 0); // Move left
	         break;
	     case KeyEvent.VK_RIGHT:
	         test.move(15, 0); // Move right
	         break;
	       
	     default:
	    	 break;
		}
	}
	
	
	
	public void jump() {
		isJumping = true;
		velocityY = JUMP_STRENGTH;
	    jumpTimer.start();
	    System.out.println("	line67");
	}
	
	public void actionPerformed(ActionEvent e) {
		updateJump();
    }


	public void updateJump() {
		if (isJumping) {
            velocityY += GRAVITY; // Apply gravity
            test.move(0, velocityY); // Move character

            // Stop the jump if character reaches the ground
            if (test.getY() >= FLOOR) {
                test.setLocation(test.getX(), FLOOR);
                isJumping = false;
                jumpTimer.stop();
            }
        }
	}
	
	

	
	
	
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
        addKeyListeners();  
        add(test);
        test.setSize(test.getWidth(), test.getHeight());
	}	
	
	
	
	
	public void run() {
		while (true) {
			//update();
            pause(16);  // Run at ~60 FPS 16ms is what you need 
        }
    }
	
	
	
	
	
	
	public static void main(String[] args) {
		new Movement().start();
		
	}

}
