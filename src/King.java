import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class King extends GraphicsProgram{
	
	private GImage king = new GImage("tritonRight.png",0,0);
	private int hp = 100;
	private String stageName = "Alex G. Spanos Center";
	private int tridentAttackValue = 10;
	private int meleeValue = 5;
	private int grenadeAttackValue = 15;
	private int lightingBoltValue = 15;
	private double tigerLocX = 500;
	private double tigerLocY = 800;
	private static final double SPEED = 15.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private static double GROUNDLEVEL = 700;
	private static double KINGWIDTH = 300;
	private static double KINGHEIGHT = 300;
	
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
	
	public void glideToEnemy(GImage s, double x, double y) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
        } else {
            s.setLocation(x, y);
            remove(s);
        }
    }
	
	public void grenadeAttack() {	
	 	double tempX = tigerLocX;
        GImage temp = new GImage("LightingBall.gif", king.getX(), king.getY());
        add(temp);
        
        TimerTask honeyBombTask = new TimerTask() {
            @Override
            public void run() {
                double dx = tempX - temp.getX();
                double dy = GROUNDLEVEL - temp.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance > SPEED) {
                    temp.move(SPEED * dx / distance, SPEED * dy / distance);
                } else {
                    temp.setLocation(tempX, 600);
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
                    }, 3000);
                }
            }
        };
        
        new Timer().scheduleAtFixedRate(honeyBombTask, 0, 50);
    }
	
	public void tridentThrowAttack() {
	 	double tempX = tigerLocX;
	 	double tempY = tigerLocY;
        GImage s = new GImage("robot.png", king.getX(), king.getY());
        add(s);
        
        TimerTask stingerTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, tempY);
                if (s.getX() == tempX && s.getY() == tempY) {
                    cancel();
                }
            }
        };
        
        new Timer().scheduleAtFixedRate(stingerTask, 0, 50);
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
		
		grenadeAttack();
	}
	
	public void init() {
		setSize(1920,1080);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new King().start();
	}
}
