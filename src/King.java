import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class King extends GraphicsProgram{
	
	private double hp = 100;
	private String stageName = "Alex G. Spanos Center";
	private int tridentAttackValue = 10;
	private int meleeValue = 5;
	private int grenadeAttackValue = 15;
	private int lightingBoltValue = 15;
	private double tigerLocX = 500;
	private double tigerLocY = 800;
	private static final double SPEED = 15.0;
	private static final double THROWSPEED = 20.0;
	private static final double LIGHTINGSPEED = 25.0;
	private static final double GRENADESPEED = 25.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private static final double GROUNDLEVEL = 650;
	private static final double KINGWIDTH = 300;
	private static final double KINGHEIGHT = 324;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GImage king = new GImage("tritonRight.png",0,GROUNDLEVEL - KINGHEIGHT);
	
	public void setTigerLocation(double x, double y) {
		tigerLocX = x;
		tigerLocY = y;
	}
	public void setHP(double i) {
		hp = i;
	}
	public String getStage() {
		return stageName;
	}
	public double getHP() {
		return hp;
	}
	public void damage(double i) {
		setHP(getHP() - i);
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
		return king.getX() + king.getWidth()/2;
	}
	public double getCenterY() {
		return king.getY() + king.getHeight()/2;
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
		if(king.getX() > tigerLocX) {
			king.setImage("tritonLeft.png");
			//king.setSize(KINGWIDTH, KINGHEIGHT);
		}
		else {
			king.setImage("tritonRight.png");
			//king.setSize(KINGWIDTH, KINGHEIGHT);
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
	
	public void glideToEnemy(GImage s, double x, double y, double speed) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            s.move(speed * dx / distance, speed * dy / distance);
        } else {
            s.setLocation(x, y);
            remove(s);
        }
    }
	
//	public void throwToEnemy(GImage s, double x, double y) {
//        double dx = x - s.getX();
//        double dy = y - s.getY();
//        double distance = Math.sqrt(dx * dx + dy * dy);
//
//        if (distance > THROWSPEED) {
//            s.move(THROWSPEED * dx / distance, THROWSPEED * dy / distance);
//        } else {
//            s.setLocation(x, y);
//            remove(s);
//        }
//    }
	
	public void grenadeAttack() {	
        GImage temp = new GImage("LightingBall.gif", king.getX(), king.getY());
        temp.setSize(120, 120);
        double tempX = rgen.nextDouble(0,1080 - 120);
        add(temp);
        
        TimerTask lightingBombTask = new TimerTask() {
            @Override
            public void run() {
                double dx = tempX - temp.getX();
                double dy = GROUNDLEVEL - temp.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance > GRENADESPEED) {
                    temp.move(GRENADESPEED * dx / distance, GRENADESPEED * dy / distance);
                } else {
                    temp.setLocation(tempX, GROUNDLEVEL);
                    cancel();

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            temp.setImage("explosion.png");
                            temp.setSize(100, 100);

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    remove(temp);
                                }
                            }, 1000);
                        }
                    }, 750);
                }
            }
        };
        
        new Timer().scheduleAtFixedRate(lightingBombTask, 0, 50);
    }
	
	public void lightingBolt() {
		double tempX = rgen.nextDouble(0,1000);
        GImage s = new GImage("LightingBolt.gif", tempX, 0);
        s.setSize(120,120);
        add(s);
        
        TimerTask lightingTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, GROUNDLEVEL,LIGHTINGSPEED);
                if (s.getX() == tempX && s.getY() == GROUNDLEVEL) {
                    cancel();
                }
            }
        };
        
        new Timer().scheduleAtFixedRate(lightingTask, 0, 50);
	}
	
	public void tridentThrowAttack() {
		isAttackActive = true;
	 	double tempX = tigerLocX;
	 	double tempY = tigerLocY;
        GImage s = new GImage("LightingTrident.gif", getCenterX(), getCenterY() - 100);
        s.setSize(200,200);
        add(s);
        
        TimerTask tridentTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, tempY, THROWSPEED);
                if (s.getX() == tempX && s.getY() == tempY) {
                    cancel();
                    isAttackActive = false;
                }
            }
        };
        
        new Timer().scheduleAtFixedRate(tridentTask, 0, 50);
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
		//king.setSize(KINGWIDTH, KINGHEIGHT);
		add(king);
		
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isWalkActive) {
					if(!isAttackActive) {
					
						double tempX = tigerLocX;
						double tempY = GROUNDLEVEL - KINGHEIGHT;
						
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
			}
		}, 0, 50);
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		spawnKing();
		
		grenadeAttack();
		grenadeAttack();
		grenadeAttack();
		
		lightingBolt();
		lightingBolt();
		lightingBolt();
		
		tridentThrowAttack();
		
		//

	}
	
	public void init() {
		setSize(1920,1080);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new King().start();
	}
}
