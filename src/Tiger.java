import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Tiger  { // extends GraphicsProgram implements ActionListener
	//variables for the tiger
	public double hp = 100; //temp hp value change later //default is 100
	public static final int attack_val = 5; //temp attack value change later
	public static final int specialAttack_val = 30; // temp special attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private double damageGiven = 0;
	private boolean spAttackUsed = false;
	//GImage tiger = new GImage("KingRight.png", 250, FLOOR );
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
	private double FLOOR = 1400; 
	//private GRect opponent = new GRect(800, FLOOR, 100, 100); // test opponent rectangle
	private GImage opponent = new GImage("",0,0);
	private double opponentHP = 100;
	GImage tiger = new GImage("tigerPoseLeftCrop.png", 250, FLOOR);
	private int FLOORBOUNDARY = 541;
	private boolean isAttacking = false;
	
	public void spawnTiger() {
		tiger = new GImage("tigerPoseLeftCrop.png",250,FLOOR);
		//tiger.setLocation(250,FLOOR - tiger.getHeight());
		tiger.scale(0.4);
		tiger.setLocation(250,FLOOR - tiger.getHeight());
		parentProgram.add(tiger);
		
	}
	
	public Tiger(GraphicsProgram parentProgram) {
        this.parentProgram = parentProgram;
    }
	
	public void setOpponent(GImage t) {
		opponent = t;
	}
	public void setGroundLevel(double g) {
		FLOOR = g;
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
		if (isAttacking == false) {
			if(opponent.getX() < tiger.getX()) {
				tiger.setImage("tigerPoseLeftCrop.png");
				isFacingRight = false;
			}
			else {
				tiger.setImage("tigerPoseRightCrop.png");
				isFacingRight = true;
			}
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
		 case KeyEvent.VK_UP, KeyEvent.VK_W :
		 	if (!isJumping) {
		 		jump();
		 		System.out.println("up arrow"); //for debug purposes 
		 		}
		 		break;
		 case KeyEvent.VK_LEFT, KeyEvent.VK_A:
			    if (tiger.getX() > 0) { // Ensure Tiger doesn't go off the left edge
			        tiger.move(-30, 0); // Move left
			    }
			    if(opponent.getX() < tiger.getX()) {
					tiger.setImage("tigerPoseLeftCrop.png");
					isFacingRight = false;
				}
				else {
					tiger.setImage("tigerPoseRightCrop.png");
					isFacingRight = true;
			    }
			    break;
		 case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
			    if (tiger.getX() + tiger.getWidth() < parentProgram.getWidth()) { // Ensure Tiger doesn't go off the right edge
			        tiger.move(30, 0); // Move right
			    }
			    if(opponent.getX() < tiger.getX()) {
					tiger.setImage("tigerPoseLeftCrop.png");
					isFacingRight = false;
				}
				else {
					tiger.setImage("tigerPoseRightCrop.png");
					isFacingRight = true;
				}
				break;
	     case KeyEvent.VK_Z, KeyEvent.VK_J: // Attack
	            normalAttack(program);
	            System.out.println("Attacking"); // Debugging purpose
	            System.out.println(tiger.getY()); // Debugging purpose
	            break;
	     case KeyEvent.VK_X, KeyEvent.VK_K: // Special Attack
           //if (getHP() <= 30 && !spAttackUsed) {
                 specialAttack(program);
                 spAttackUsed = true;
                 System.out.println("Special Attacking"); // Debugging purpose
             //}
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
            if (tiger.getY() >= FLOOR - tiger.getHeight()) {
                tiger.setLocation(tiger.getX(), FLOOR - tiger.getHeight());
                isJumping = false;
                jumpTimer.stop();
            }
        }
	}


	
	public void normalAttack(GraphicsProgram program) {
	    // Create an attack hitbox to either the right or left of the character
		
		isAttacking = true;
		
		double attackXCord = tiger.getX();
		tiger.setImage("punchRightCrop.png");
		
		if (tiger.getX() > opponent.getX()) {
			attackXCord = tiger.getX() - 250;
			tiger.setImage("punchLeftCrop.png");
		}
		
		GRect attackHitbox = new GRect(attackXCord + tiger.getWidth(), tiger.getY(), 50, tiger.getHeight() / 2);
	    //attackHitbox.setFilled(true);
	   // attackHitbox.setFillColor(java.awt.Color.RED);
	    attackHitbox.setVisible(false);
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
	    		
	    		if (tiger.getX() > opponent.getX()) {
	    			tiger.setImage("tigerPoseLeftCrop.png");
	    		}
	        	
	    		else {
	    			tiger.setImage("tigerPoseRightCrop.png");
	    		}
	    		
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
	            
	    	tiger.setImage("punchRightCrop.png");	
	    	
	    	if (tiger.getX() > opponent.getX()) {
	    		tiger.setImage("punchLeftCrop.png");
	    	}
	        	
	        	
	        	
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
	                boom.scale(0.4);
	                program.add(boom);
	                hitOpponent = true;
	                if(boom.getBounds().intersects(opponent.getBounds())) {
	                	setDamageGiven(specialAttack_val);
	                }
	                if (tiger.getX() > opponent.getX()) {
    	    			tiger.setImage("tigerPoseLeftCrop.png");
    	    		}
    	        	
    	    		else {
    	    			tiger.setImage("tigerPoseRightCrop.png");
    	    		}
	                
	                isAttacking = false;
	                
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
	            if (tiger.getY() >= FLOOR - tiger.getHeight()) {
	                tiger.setLocation(tiger.getX(), FLOOR - tiger.getHeight());
	                isJumping = false;
	                ((Timer) e.getSource()).stop();
	            }
	        }
	    });
	    specialAttackTimer.start();
	}
// round1
	public void stopMovement() {
		// TODO Auto-generated method stub
		
	}

	public void startMovement() {
		// TODO Auto-generated method stub
		
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
