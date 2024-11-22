import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Tiger extends GraphicsProgram implements ActionListener {
	//variables for the tiger
	public static final int hp = 30; //temp hp value change later //default is 100
	public static final int attack_val = 10; //temp attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private boolean spAttackUsed = false;
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
	private GRect opponent = new GRect(800, FLOOR, 100, 100); // test opponent rectangle
	
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
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case KeyEvent.VK_UP:
		 	if (!isJumping) {
		 		jump();
		 		System.out.println("up arrow"); //for debug purposes 
		 		}
		 		break;
         case KeyEvent.VK_LEFT:
	         test.move(-15, 0); // Move left
	         break;
	     case KeyEvent.VK_RIGHT:
	         test.move(15, 0); // Move right
	         break;
	     case KeyEvent.VK_Z: // Attack
	            normalAttack();
	            System.out.println("Attacking"); // Debugging purpose
	            break;
	     case KeyEvent.VK_X: // Special Attack
             if (getHP() <= 30 && !spAttackUsed) {
                 specialAttack();
                 spAttackUsed = true;
                 System.out.println("Special Attacking"); // Debugging purpose
             }
             break;
	     
	     
	     default:
	    	 break;
		}
	}
	
	public void jump() {
		isJumping = true;
		velocityY = JUMP_STRENGTH;
	    jumpTimer.start();
	    System.out.println("jump()");
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
	
	
	public void normalAttack() {
	    // Create an attack hitbox to either the right or left of the character
		double attackXCord = test.getX();
		
		if (test.getX() > opponent.getX()) {
			attackXCord = test.getX() - 250;
		}
		
		GRect attackHitbox = new GRect(attackXCord + test.getWidth(), test.getY(), 50, test.getHeight() / 2);
	    attackHitbox.setFilled(true);
	    attackHitbox.setFillColor(java.awt.Color.RED);
	    add(attackHitbox);
	    
	    // Timer to remove attack hitbox after a short delay
	    Timer attackTimer = new Timer(200, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            remove(attackHitbox);
	            ((Timer) e.getSource()).stop();
	        }
	    });
	    attackTimer.start();
	}
	
	public void specialAttack() {
	    // Create an arc jump movement for the special attack
	    isJumping = true;
	    velocityY = JUMP_STRENGTH;
	    Timer specialAttackTimer = new Timer(20, new ActionListener() {
	    	private int horizontalVelocity = 10; 
	        private boolean hitOpponent = false;
	    
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            velocityY += GRAVITY; 
	            if (opponent.getX() > test.getX()) {
	            	test.move(horizontalVelocity, velocityY); // Move character in an arc to the left
	            }
	            else {
	            	test.move(-horizontalVelocity, velocityY); // Move character in an arc to the right
	            }
	            
	            // Check if character hits the opponent
	            if (!hitOpponent && test.getBounds().intersects(opponent.getBounds())) {
	                // Display boom image at opponent position
	                GImage boom = new GImage("explosion.png", opponent.getX(), opponent.getY() - 50);
	                add(boom);
	                hitOpponent = true;
	                Timer boomTimer = new Timer(500, new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        remove(boom);
	                        ((Timer) e.getSource()).stop();
	                    }
	                });
	                boomTimer.start();
	            }
	        
	            // Stop the jump if character reaches the ground
	            if (test.getY() >= FLOOR) {
	                test.setLocation(test.getX(), FLOOR);
	                isJumping = false;
	                ((Timer) e.getSource()).stop();
	            }
	        }
	    });
	    specialAttackTimer.start();
	}
	

	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
        addKeyListeners();  
        add(test);
        test.setSize(test.getWidth(), test.getHeight());
        add(opponent);
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
