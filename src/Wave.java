import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Wave extends GraphicsProgram {
	private GImage wave = new GImage("Robot.png",500,500);
	private int hp = 100;
	private String stageName = "Chris Kjeldsen Pool";
	private int waveAttackValue = 10;
	private int meleeValue = 5;
	private int seaweedAttackValue = 15;
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
	
	public int getWaveAttackValue() {
		return waveAttackValue;
	}
	
	public int getMeleeValue() {
		return meleeValue;
	}
	
	public int getSeaweedValue() {
		return seaweedAttackValue;
	}
	public double getCenterX() {
		return wave.getWidth()/2;
	}
	public double getCenterY() {
		return wave.getHeight()/2;
	}
	
	private void walkToEnemy(GImage s,double x, double y) {	
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
        } else {
        		s.setLocation(x, y);
        		isWalkActive = false;
        }
    }
	
	private void moveWaveAttack(GImage s, double x, double y) {
		double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
        } else {
        		s.setLocation(x, y);
        		remove(s);
        		isAttackActive = false;
        }
	}
	
	private void waveAttack() {
		GImage waveAttack = new GImage("Wave.gif",1800,780);
		add(waveAttack);
		isAttackActive = true;
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				moveWaveAttack(waveAttack,0,GROUNDLEVEL);
			}
		}, 0, 50);
	}
	
	private void seaweedAttack() {
		isAttackActive = true;
		//wave.setImage();
		Timer temp = new Timer();
		temp.schedule(new TimerTask() {
			@Override
			public void run() {
				//wave.setImage();
				GImage seaweed = new GImage("Seaweed.gif",500,GROUNDLEVEL);
				add(seaweed);
				isAttackActive = true;
				
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						remove(seaweed);
						isAttackActive = false;
					}
				}, 1000);
			}
		}, 500);
	}
	
	public void spawnWave() {
		add(wave);
		
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isWalkActive) {
					
					double tempX = tigerLocX;
					double tempY = tigerLocY;
					
					Timer t = new Timer();
					TimerTask t2 = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
								walkToEnemy(wave,tempX,tempY);
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
		
		add(wave);
		
//		double tempX = tigerLocX;
//		double tempY = tigerLocY;
//		
//		Timer t = new Timer();
//		TimerTask t2 = new TimerTask() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				if(!isWalkActive) {
//					walkToEnemy(wave,tempX,tempY);
//					isWalkActive = true;
//				}
//			}
//		};	
//		
//		t.scheduleAtFixedRate(t2, 0, 50);
		
		spawnWave();
		
		waveAttack();
		
		seaweedAttack();
	}
	
	public void init() {
		setSize(1920,1080);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Wave().start();
		
	}

}
