import java.awt.event.KeyEvent;
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Tiger extends GraphicsProgram {
	public static final int hp = 100; //temp hp value change later
	public static final int attack_val = 10; //temp attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private GImage idle;
	private GImage attack;
	private GImage specialAttack;
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	private GImage tigerTesting = new GImage("Robot.png", 300, 100); //image for movement testing
	
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
	
	
	
	//public GImage spawnTiger(getXPosition, getYPosition) { //placeholder for spawnTiger function change/replace as needed
		//return //GImage specialTiger = new(insert image file when we have, getXPosition, getYPosition)
		
	
	//	}
	
	@Override
	 public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 	case KeyEvent.VK_UP:
		 		tigerTesting.move(0, -5); // Move up
	            break;
	        case KeyEvent.VK_DOWN:
	            tigerTesting.move(0, 5); // Move down
               break;
           case KeyEvent.VK_LEFT:
	            tigerTesting.move(-5, 0); // Move left
	            break;
	        case KeyEvent.VK_RIGHT:
	            tigerTesting.move(5, 0); // Move right
	            break;
	        }
	    }
	
	
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}	
	
	
	public void run() {
        add(tigerTesting);
        addKeyListeners();
		}
	
	
	public static void main(String[] args) {
		new Tiger().start();
	}
}

