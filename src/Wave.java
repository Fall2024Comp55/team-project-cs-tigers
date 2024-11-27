import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Wave {
	private GImage wave = new GImage("Robot.png",500,500);
	private double hp = 100;
	private String stageName = "Chris Kjeldsen Pool";
	private static final int WAVEATTACKVALUE = 10;
	private static final int MELEEATTACKVALUE = 5;
	private static final int SEAWEEDATTACKVALUE = 15;
	private double tigerLocX = 1500;
	private double tigerLocY = 800;
	private static final double SPEED = 15.0;
	private static final double WAVESPEED = 20.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private boolean isFacingRight = false;
	private boolean isPaused = false;
	private static final double GROUNDLEVEL = 700;
	private int damageGiven = 0;
	private GImage tiger = new GImage("",0,0);
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GraphicsProgram parentProgram;
	
//	public int tempHealth = 200;
//    GImage tigerTemp = new GImage("TigerPlaceHolder.png", 500 - 9, 500);

	public void Wave(GraphicsProgram parentProgram) {
		this.parentProgram = parentProgram;
        this.wave = wave;
	}
	public void addToProgram(GraphicsProgram program) {
        program.add(wave);
    }
	public void setTigerLoc(GImage s) {
		tiger = s;
	}
	public void checkPaused(boolean t) {
		isPaused = t;
	}
	
	public GImage getWavetLoc() {
		return wave;
	}
	
	public String getStage() {
		return stageName;
	}
	
	public void setHP(double i) {
		hp = i;
	}
	
	public double getHP() {
		return hp;
	}
	
	public void damage(double i) {
		setHP(getHP() - i);
	}
	
	public int getWaveAttackValue() {
		return WAVEATTACKVALUE;
	}
	
	public int getMeleeValue() {
		return MELEEATTACKVALUE;
	}
	
	public int getSeaweedValue() {
		return SEAWEEDATTACKVALUE;
	}
	public double getCenterX() {
		return wave.getWidth()/2;
	}
	public double getCenterY() {
		return wave.getHeight()/2;
	}
	
	public void setDamageGiven(int i) {
		damageGiven = damageGiven + i;
	}
	public int getDamageGiven() {
		return damageGiven;
	}
	
	public boolean isDead() {
		if(hp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void checkSide() {
		if(wave.getX() > tiger.getX()) {
			wave.setImage("HornetPrototype.gif");
			isFacingRight = false;
		}
		else {
			wave.setImage("HornetPrototypeFlipped.gif");
			isFacingRight = true;
		}
	}
	
	private boolean imagesIntersect(GImage image1, GImage image2, boolean t) {
	    double x1 = image1.getX();
	    double y1 = image1.getY();
	    double w1 = image1.getWidth();
	    double h1 = image1.getHeight();

	    double x2 = image2.getX();
	    double y2 = image2.getY();
	    double w2 = image2.getWidth();
	    double h2 = image2.getHeight();

	    if(!t) {
	    	return (x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2);
	    }
	    else {
	    	if(isFacingRight) {
	            return ((x1 < x2 + w2 + 10 && x1 + w1 + 10 > x2) && y1 < y2 + h2 && y1 + h1 > y2);
	    	}
	    	else {
	            return ((x1 - 10 < x2 + w2 && x1 + w1 > x2 - 10) && y1 < y2 + h2 && y1 + h1 > y2);
	    	}
	    }
	}
	
	private void walkToEnemy(GImage s,double x, double y) {	
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
	
	private void moveWaveAttack(GImage s, double x, double y) {
		double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > WAVESPEED) {
            s.move(WAVESPEED * dx / distance, WAVESPEED * dy / distance);
        } else {
        		s.setLocation(x, y);
        		//remove(s);
        		parentProgram.remove(s);
        		isAttackActive = false;
        }
	}
	
	private void waveAttack() {
		GImage waveAttack = new GImage("Wave.gif",1800,GROUNDLEVEL);
		//add(waveAttack);
		parentProgram.add(waveAttack);
		isAttackActive = true;
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isPaused) {
					moveWaveAttack(waveAttack,0,GROUNDLEVEL);
					if(imagesIntersect(waveAttack,tiger,false)) {
						if(!isPaused) {
							setDamageGiven(WAVEATTACKVALUE);
						}
					}
				}
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
				//add(seaweed);
				parentProgram.add(seaweed);
				isAttackActive = true;
				
				if(imagesIntersect(seaweed,tiger,false)) {
					if(!isPaused) {
						setDamageGiven(SEAWEEDATTACKVALUE);
					}
				}
				
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						//remove(seaweed);
						parentProgram.remove(seaweed);
						isAttackActive = false;
					}
				}, 1000);
			}
		}, 500);
	}
	
	private void waterWhip() {
		wave.setImage("robot.png");
		
		if(imagesIntersect(wave,tiger,true)) {
			if(!isPaused) {
				setDamageGiven(MELEEATTACKVALUE);
			}
		}
		
		Timer temp = new Timer();
		
		temp.schedule(new TimerTask() {
			@Override
			public void run() {
				if(isWalkActive) {
					wave.setImage("robot.png");
				}
				else {
					wave.setImage("robot.png");
				}
			}
		}, 300);
	}
	
	public void spawnWave() {
		//add(wave);
		parentProgram.add(wave);
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isWalkActive) {
					
					double tempX = tiger.getX();
					double tempY = GROUNDLEVEL;
					
					Timer t = new Timer();
					TimerTask t2 = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(!isPaused) {
								walkToEnemy(wave,tempX,tempY);
								isWalkActive = true;
							}
						}
					};	
					
					t.scheduleAtFixedRate(t2, 0, 50);
				}
			}
		}, 0, 50);
		
		Timer attackTimer = new Timer();
		attackTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isPaused) {
                    int choice = rgen.nextInt(1, 3);
                    if (choice == 1) {
                        seaweedAttack();
                    } else if (choice == 2) {
                        waveAttack();
                    } else if (choice == 3) {
                        waterWhip();
                    }
				}
			}
		},500, 500);
		
//		 Timer t22 = new Timer();
//	        t22.scheduleAtFixedRate(new TimerTask() {
//	        	@Override
//	        	public void run() {
//	        		setTigerLoc(tigerTemp);
//	        		System.out.println("Health: " + (tempHealth - getDamageGiven()));
//	        		setDamageGiven(0);
//	        	}
//	        },0,500);
		
		
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		spawnWave();
//	}
//	
//	public void init() {
//		setSize(1920,1080);
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		new Wave().start();
//		
//	}

}
