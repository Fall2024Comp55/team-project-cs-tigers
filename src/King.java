import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class King extends GraphicsProgram{
	
	private GImage king = new GImage("Robot.png",500,500);
	private int hp = 100;
	private String stageName = "Alex G. Spanos Center";
	private int tridentAttackValue = 10;
	private int meleeValue = 5;
	private int grenadeAttackValue = 15;
	private int lightingBoltValue = 15;
	private double tigerLocX = 1500;
	private double tigerLocY = 800;
	private static final double SPEED = 15.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private double GROUNDLEVEL = 700;
	
	public void setTigerLocation(double x, double y) {
		tigerLocX = x;
		tigerLocY = y;
	}
	public String getStage() {
		return stageName;
	}
	public int getHP() {
		return hp;
	}
	public int getTridentAttackValue() {
		return tridentAttackValue;
	}
	public int getMeleeValue() {
		return meleeValue;
	}
	public int getGrenadeValue() {
		return grenadeAttackValue;
	}
	public int getLightingValue() {
		return lightingBoltValue;
	}
	public double getCenterX() {
		return king.getWidth()/2;
	}
	public double getCenterY() {
		return king.getHeight()/2;
	}
	
	public void checkSide() {
		if(king.getX() > tigerLocX) {
			king.setImage("HornetPrototype.gif");
		}
		else {
			king.setImage("HornetPrototypeFlipped.gif");
		}
	}
	
	public void walkToEnemy(GImage s,double x, double y) {	
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
            checkSide();
        } else {
        		s.setLocation(x, y);
        		isWalkActive = false;
        }
    }
	
	public void tridentStab() {
		king.setImage("robot.png");
		
		Timer temp = new Timer();
		
		temp.schedule(new TimerTask() {
			@Override
			public void run() {
				if(isWalkActive) {
					king.setImage("robot.png");
				}
				else {
					king.setImage("robot.png");
				}
			}
		}, 300);
	}
	
	public void spawnKing() {
		add(king);
		
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isWalkActive) {
					
					double tempX = tigerLocX;
					double tempY = GROUNDLEVEL;
					
					Timer t = new Timer();
					TimerTask t2 = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
								walkToEnemy(king,tempX,tempY);
								isWalkActive = true;
						}
					};	
					
					t.scheduleAtFixedRate(t2, 0, 50);
				}
			}
		}, 0, 50);
		
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		spawnKing();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
