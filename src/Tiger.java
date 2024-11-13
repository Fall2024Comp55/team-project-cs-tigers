import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Tiger extends GraphicsProgram implements ActionListener {
	//variables for the tiger
	public static final int hp = 100; //temp hp value change later
	public static final int attack_val = 10; //temp attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private GImage idle;
	private GImage attack;
	private GImage specialAttack;
	//variables for the movement
	private int velocityY = 0; // Vertical velocity (used for jump and gravity)
	private static final int GRAVITY = 1; // Gravity constant (affects velocity)
	private static final int JUMP_STRENGTH = -25; // Strength of the jump // must be a negative number// value to be determined
	private boolean isJumping = false;
	private Timer jumpTimer = new Timer(20, this); 
	//variables for testing
	public static final int PROGRAM_WIDTH = 1920; //temp value for testing
	public static final int PROGRAM_HEIGHT = 1080; // temp values for testing
	private GRect test = new GRect(100, 500 , 200, 200);
	private static final int FLOOR = 500;
	
	public int getHP() {
		return hp;
	}
	
	public int getAtkVal() {
		return attack_val;
	}
	
	public int specialAttackVal() { 
		return attack_val * 2;
	}
	
	public GImage returnIdle() {
		return idle;
	}
	
	public GImage getAttackAnimation() {
		return attack;
	}
	
	public GImage specialAttack() {
		return specialAttack;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case KeyEvent.VK_UP:
		 	if (!isJumping) {
		 		jump();
		 		System.out.println("line30"); //for debug purposes 
		 		}
		 		break;
		
		 		
		 		//down arrow movement is temporarily disabled until the function of it is confirmed
		 		
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
	
	
	@Override
	public void run() {
		while (true) {
			//update();
            pause(16);  // Run at ~60 FPS 16ms is what you need 
        }
	}

	public static void main(String[] args) {
		new Tiger().start();
	}

}

