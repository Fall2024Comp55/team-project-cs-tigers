import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Tiger  { // extends GraphicsProgram implements ActionListener
	//variables for the tiger
	public double hp = 100; //temp hp value change later //default is 100
	public static final int attack_val = 10; //temp attack value change later
	public static final int specialAttack_val = 30; // temp special attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private double damageGiven = 0;
	private boolean spAttackUsed = false;
	GImage tiger = new GImage("KingRight.png", 250, 500 );
	private GImage attack;
	private GImage specialAttack;
	private boolean isFacingRight = true;
	private GraphicsProgram parentProgram;
	//variables for the movement
	private int velocityY = 0; // Vertical velocity (used for jump and gravity)
	private static final int GRAVITY = 1; // Gravity constant (affects velocity)
	private static final int JUMP_STRENGTH = -25; // Strength of the jump // must be a negative number// value to be determined
	private boolean isJumping = false;
	private Timer jumpTimer = new Timer(20, e -> updateJump());
	//variables for testing
	public static final int PROGRAM_WIDTH = 1920; //temp value for testing
	public static final int PROGRAM_HEIGHT = 1080; // temp values for testing
	private GRect test = new GRect(100, 500 , 200, 200);
	private static final int FLOOR = 500;
	private GRect opponent = new GRect(800, FLOOR, 100, 100); // test opponent rectangle
	private double opponentHP = 100;
	
	
	public void spawnTiger() {
		parentProgram.add(tiger);
		
	}
	
	public Tiger(GraphicsProgram parentProgram) {
        this.parentProgram = parentProgram;
    }
	
	
	public double getHP() {
		return hp;
	}
	
	public void setHP(double i) {
		hp = i;
	}
	public void damage(double i) {
		setHP(getHP() - i);
	}
	
	public void setDamageGiven(double i) {
		damageGiven = damageGiven + i;
	}
	
	public void setDamageGivenToZero() {
		damageGiven = 0;
	}
	
	public double getDamageGiven() {
		return damageGiven;
	}
	
	public int getAtkVal() {
		return attack_val;
	}
	
	public int specialAttackVal() { 
		return attack_val * 2;
	}
	
	public GImage returnTiger() {
		return tiger;
	}
	
	public void addToProgram(GraphicsProgram program) {
        program.add(tiger);
    }
	
	public void checkSide(GImage opponent) {
		if(opponent.getX() < tiger.getX()) {
			tiger.setImage("KingLeft.png");
			isFacingRight = false;
		}
		else {
			tiger.setImage("KingRight.png");
			isFacingRight = true;
		}
	}
	
	public boolean isDead() {
		if(hp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	//@Override
	public void keyPressed(KeyEvent e, GraphicsProgram program) {
		 switch (e.getKeyCode()) {
		 case KeyEvent.VK_UP:
		 	if (!isJumping) {
		 		jump();
		 		System.out.println("up arrow"); //for debug purposes 
		 		}
		 		break;
         case KeyEvent.VK_LEFT:
	         tiger.move(-15, 0); // Move left
	         break;
	     case KeyEvent.VK_RIGHT:
	         tiger.move(15, 0); // Move right
	         break;
	     case KeyEvent.VK_Z: // Attack
	            normalAttack(program);
	            System.out.println("Attacking"); // Debugging purpose
	            break;
	     case KeyEvent.VK_X: // Special Attack
             if (getHP() <= 30 && !spAttackUsed) {
                 specialAttack(program);
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
	   // opponentHP -= getDamageGiven();
       // System.out.println(opponentHP);
        //setDamageGiven(0.0);
        //System.out.println(opponentHP);
	}
	
	public void actionPerformed(ActionEvent e) {
		updateJump();
    }
	
	
	public void updateJump() {
		if (isJumping) {
            velocityY += GRAVITY; // Apply gravity
            tiger.move(0, velocityY); // Move character

            // Stop the jump if character reaches the ground
            if (tiger.getY() >= FLOOR) {
                tiger.setLocation(tiger.getX(), FLOOR);
                isJumping = false;
                jumpTimer.stop();
            }
        }
	}
	
	
	public void normalAttack(GraphicsProgram program) {
	    // Create an attack hitbox to either the right or left of the character
		double attackXCord = tiger.getX();
		
		if (tiger.getX() > opponent.getX()) {
			attackXCord = tiger.getX() - 250;
		}
		
		GRect attackHitbox = new GRect(attackXCord + tiger.getWidth(), tiger.getY(), 50, tiger.getHeight() / 2);
	    attackHitbox.setFilled(true);
	    attackHitbox.setFillColor(java.awt.Color.RED);
	    program.add(attackHitbox);
	    Sound.playSound("media/punch.wav");
	    if(attackHitbox.getBounds().intersects(opponent.getBounds())) {
        	setDamageGiven(attack_val);
        }
	    
	    // Timer to remove attack hitbox after a short delay
	    Timer attackTimer = new Timer(200, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	program.remove(attackHitbox);
	            ((Timer) e.getSource()).stop();
	        }
	    });
	    attackTimer.start();
	}
	
	public void specialAttack(GraphicsProgram program) {
	    // Create an arc jump movement for the special attack
	    isJumping = true;
	    velocityY = JUMP_STRENGTH;
	    Timer specialAttackTimer = new Timer(20, new ActionListener() {
	    	private int horizontalVelocity = 10; 
	        private boolean hitOpponent = false;
	    
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            velocityY += GRAVITY; 
	            if (opponent.getX() > tiger.getX()) {
	            	tiger.move(horizontalVelocity, velocityY); // Move character in an arc to the left
	            }
	            else {
	            	tiger.move(-horizontalVelocity, velocityY); // Move character in an arc to the right
	            }
	            
	            // Check if character hits the opponent
	            if (!hitOpponent && tiger.getBounds().intersects(opponent.getBounds())) {
	                // Display boom image at opponent position
	                GImage boom = new GImage("explosion.png", opponent.getX(), opponent.getY() - 50);
	                program.add(boom);
	                hitOpponent = true;
	                Timer boomTimer = new Timer(500, new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        program.remove(boom);
	                        ((Timer) e.getSource()).stop();
	                    }
	                });
	                boomTimer.start();
	            }
	        
	            // Stop the jump if character reaches the ground
	            if (tiger.getY() >= FLOOR) {
	                tiger.setLocation(tiger.getX(), FLOOR);
	                isJumping = false;
	                ((Timer) e.getSource()).stop();
	            }
	        }
	    });
	    specialAttackTimer.start();
	}
	

	
/*	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
        addKeyListeners();  
        add(tiger);
        tiger.setSize(tiger.getWidth(), tiger.getHeight());
        add(opponent);
	}	
	
	
	//@Override
	public void run() {
		while (true) {
			//update();
			pause(16);  // Run at ~60 FPS 16ms is what you need 
		}
	}

	public static void main(String[] args) {
		new Tiger().start();
	}

*/
}
