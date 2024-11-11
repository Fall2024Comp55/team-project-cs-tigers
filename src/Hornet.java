import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Hornet extends GraphicsProgram implements ActionListener {
	
	private int hp = 100;
	private String stageName = "Burn's Tower";
	private int rangedAttackValue = 10;
	private int meleeValue = 5;
	private int honeyBombValue = 15;
	Timer timer = new Timer();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	GImage hornet = new GImage("HornetPrototype.gif",100,100);
	private boolean active;
    private double LocationX;
    private double LocationY;
    private double tigerLocX = 400;
    private double tigerLocY = 400;
    private static final double GROUNDLEVEL = 600.0;
    private static final double SPEED = 15.0;
    private TimerTask moveTask;

	
	public Hornet() {
        setRandomTarget();
    }
	public void setTigerLoc(double x, double y) {
		tigerLocX = x;
		tigerLocY = y;
	}
	public void setRandomTarget() {
	        LocationX = rgen.nextDouble(0, 1400);
	        LocationY = rgen.nextDouble(0, 800);
    }
	public void glideToTarget() {
        if (!active) {
            double dx = LocationX - hornet.getX();
            double dy = LocationY - hornet.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > SPEED) {
                hornet.move(SPEED * dx / distance, SPEED * dy / distance);
            } else {
                hornet.setLocation(LocationX, LocationY);
            }
        }
    }
	
	 public void glideToEnemy(GImage s, double x, double y) {
	        double dx = x - s.getX();
	        double dy = y - s.getY();
	        double distance = Math.sqrt(dx * dx + dy * dy);

	        if (distance > SPEED) {
	            s.move(SPEED * dx / distance, SPEED * dy / distance);
	        } else {
	            s.setLocation(LocationX, LocationY);
	            remove(s);
	        }
	    }
	public void checkSide() {
		if(hornet.getX() > tigerLocX) {
			hornet.setImage("HornetPrototype.gif");
		}
		else {
			hornet.setImage("HornetPrototypeFlipped.gif");
		}
	}
	public int getHP() {
		return hp;
	}
	public String getStageName() {
		return stageName;
	}
	public int getRangedAttackValue() {
		return rangedAttackValue;
	}
	public int getMeleeValue() {
		return meleeValue;
	}
	public int getHoneyBombValue() {
		return honeyBombValue;
	}
	public double getCenterX() {
		return hornet.getWidth()/2;
	}
	public double getCenterY() {
		return hornet.getHeight()/2;
	}
	public void move(double x,double y) {
        double distance = Math.sqrt(x * x + y * y);
        double SPEED = 20.0;
        
        hornet.move(SPEED * x / distance, SPEED * y / distance);
        		
	}
	 public void stingerAttack() {
	 	double tempX = tigerLocX;
	 	double tempY = tigerLocY;
        GImage s = new GImage("robot.png", hornet.getX(), hornet.getY());
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
        
        timer.scheduleAtFixedRate(stingerTask, 0, 50);
	 }
	 public void honeyBombAttack() {	
		 	double tempX = tigerLocX;
	        GImage temp = new GImage("honeyBomb.png", hornet.getX(), hornet.getY());
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
	        
	        timer.scheduleAtFixedRate(honeyBombTask, 0, 50);
	    }
	 public void chargeAttack() {
	        if (active) return;
	        active = true;
	        
	        double tempX = tigerLocX;
	        double tempY = tigerLocY;

	        TimerTask chargeTask = new TimerTask() {
	            @Override
	            public void run() {
	                double dx = tempX - hornet.getX();
	                double dy = tempY - hornet.getY();
	                double distance = Math.sqrt(dx * dx + dy * dy);

	                hornet.setImage("robot.png");

	                if (distance > SPEED) {
	                    hornet.move(SPEED * dx / distance, SPEED * dy / distance);
	                } else {
	                    hornet.setLocation(tempX, tempY);
	                    hornet.setImage("HornetPrototype.gif");
	                    active = false;
	                    cancel();
	                }
	            }
	        };

	        timer.scheduleAtFixedRate(chargeTask, 0, 50);
	    }
	@Override
	public void run() {
        add(hornet);
        GImage temp = new GImage("TigerPlaceHolder.png", 400, 400);
        temp.setSize(200, 200);
        add(temp);
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		setRandomTarget();
        	}
        }, 0, 2000);

        moveTask = new TimerTask() {
            @Override
            public void run() {
                if (!active) {
//                    setRandomTarget();
                    glideToTarget();
                    checkSide();
                }
            }
        };
        timer.scheduleAtFixedRate(moveTask, 0, 50);

        TimerTask actionTask = new TimerTask() {
            @Override
            public void run() {
                if (!active) {
                    int choice = rgen.nextInt(1, 3);
                    if (choice == 1) {
                        chargeAttack();
                    } else if (choice == 2) {
                        stingerAttack();
                    } else if (choice == 3) {
                        honeyBombAttack();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(actionTask, 500, 2000);
    }
	public void init() {
		setSize(1920,1080);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Hornet().start();
	}

}
