import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Hornet {
	
	GImage hornet = new GImage("HornetPrototype.gif",100,100);
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String stageName = "Burn's Tower";
	private double hp = 100;
	private boolean active;
    private double LocationX;
    private double LocationY;
    private double tigerLocX = 400;
    private double tigerLocY = 400;
    private double tigerCornerX = 0.0;
    private double tigerCornerY = 0.0;
    private int damageGiven = 0;
    private boolean isPaused = false;
    private static final double GROUNDLEVEL = 600.0;
    private static final double SPEED = 15.0;
    private static final double STINGERSPEED = 20.0;
    private static final double HONEYBOMBSPEED = 20.0;
    private static final double CHARGESPEED = 25.0;
    private static final double TIGERWIDTH = 200.0;
    private static final double TIGERHEIGHT = 200.0;
    private static final int RANGEATTACKVALUE = 5;
	private static final int MELEEVALUE = 15;
	private static final int HONEYBOMBVALUE = 20;
    private TimerTask moveTask;
	Timer timer = new Timer();
	private GImage tiger = new GImage("",0,0);
	private GraphicsProgram parentProgram;
	
//	public int tempHealth = 200;
//    GImage tigerTemp = new GImage("TigerPlaceHolder.png", 400, 400);

	
	public Hornet(GraphicsProgram parentProgram) {
        setRandomTarget();
//        hornet = new GImage("HornetPrototype.gif", 100, 100);
        this.parentProgram = parentProgram;
        this.hornet = hornet;
    }
	
	public void addToProgram(GraphicsProgram program) {
        program.add(hornet);
    }
	public void checkPaused(boolean t) {
		isPaused = t;
	}
	
	public void setTigerLoc(GImage t) {
		tiger = t;
	}
	public GImage getHornetLoc() {
		return hornet;
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
	
	public void glideToEnemy(GImage s, double x, double y, double speed) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            s.move(speed * dx / distance, speed * dy / distance);
        } else {
            s.setLocation(LocationX, LocationY);
            //remove(s);
        }
    }
	public void checkSide() {
		if(hornet.getX() > tiger.getX()) {
			hornet.setImage("HornetPrototype.gif");
		}
		else {
			hornet.setImage("HornetPrototypeFlipped.gif");
		}
	}
	public void setHP(double i) {
		hp = i;
	}
	public void damage(double i) {
		setHP(getHP() - i);
	}
	public double getHP() {
		return hp;
	}
	public String getStageName() {
		return stageName;
	}
	public boolean getActive() {
		return active;
	}
	public int getRangedAttackValue() {
		return RANGEATTACKVALUE;
	}
	public int getMeleeValue() {
		return MELEEVALUE;
	}
	public int getHoneyBombValue() {
		return HONEYBOMBVALUE;
	}
	public void setDamageGiven(int i) {
		damageGiven = damageGiven + i;
	}
	public int getDamageGiven() {
		return damageGiven;
	}
	public double getCenterX() {
		return (hornet.getX() + hornet.getWidth()) /2;
	}
	public double getCenterY() {
		return (hornet.getY() + hornet.getHeight())/2;
	}
	public boolean isDead() {
		if(hp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public void move(double x,double y) {
        double distance = Math.sqrt(x * x + y * y);
        double SPEED = 20.0;
        
        hornet.move(SPEED * x / distance, SPEED * y / distance);
        		
	}
	private boolean imagesIntersect(GImage image1, GImage image2) {
	    double x1 = image1.getX();
	    double y1 = image1.getY();
	    double w1 = image1.getWidth();
	    double h1 = image1.getHeight();

	    double x2 = image2.getX();
	    double y2 = image2.getY();
	    double w2 = image2.getWidth();
	    double h2 = image2.getHeight();

	    return (x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2);
	}
	
	 public void stingerAttack() {
	 	double tempX = tiger.getX();
	 	double tempY = tiger.getY();	 	
        GImage s = new GImage("robot.png", hornet.getX(), hornet.getY());
        
//        if(getCenterX() > tigerLocX) {
//        	if(getCenterY() > tigerLocY) {
//        		s.setImage("LeftCornerStinger.png");
//        	}
//        	else {
//        		//s.setImage("");
//        	}
//        }
//        else {
//        	if(getCenterY() > tigerLocY) {
//        		s.setImage("TopLeftCornerStinger.png");
//        	}
//        	else {
//        		//s.setImage("BottomRightCornerStinger.png");
//        	}
//        }
        
        
        
        
        
        //add(s);
        parentProgram.add(s);
        
        TimerTask stingerTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, tempY, STINGERSPEED);
                
                if (imagesIntersect(s, tiger)) {
                    setDamageGiven(RANGEATTACKVALUE);
                    parentProgram.remove(s);
                   //remove(s);
                    cancel();
                }
                
                if (s.getX() == tempX && s.getY() == tempY) {
                    parentProgram.remove(s);
                	//remove(s);
                    cancel();
                }
            }   
        };
        
        timer.scheduleAtFixedRate(stingerTask, 0, 50);        
	 }
	 public void honeyBombAttack() {	
		 	double tempX = tiger.getX();
	        GImage temp = new GImage("honeyBomb.png", hornet.getX(), hornet.getY());
	        temp.scale(0.5);
	        //add(temp);
	        parentProgram.add(temp);
	        
	        TimerTask honeyBombTask = new TimerTask() {
	            @Override
	            public void run() {
	                double dx = tempX - temp.getX();
	                double dy = GROUNDLEVEL - temp.getY();
	                double distance = Math.sqrt(dx * dx + dy * dy);

	                if (distance > HONEYBOMBSPEED) {
	                    temp.move(HONEYBOMBSPEED * dx / distance, HONEYBOMBSPEED * dy / distance);
	                } else {
	                    temp.setLocation(tempX, GROUNDLEVEL);
	                    cancel();

	                    new Timer().schedule(new TimerTask() {
	                        @Override
	                        public void run() {
	                            temp.setImage("explosion.png");
	                            temp.scale(0.5);
	                            
	                            if(imagesIntersect(temp,tiger)) {
	                            	setDamageGiven(HONEYBOMBVALUE);
	                            }

	                            new Timer().schedule(new TimerTask() {
	                                @Override
	                                public void run() {
	                                    //remove(temp);
	                                    parentProgram.remove(temp);
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
	        
	        double tempX = tiger.getX();
	        double tempY = tiger.getY();

	        TimerTask chargeTask = new TimerTask() {
	            @Override
	            public void run() {
	                double dx = tempX - hornet.getX();
	                double dy = tempY - hornet.getY();
	                double distance = Math.sqrt(dx * dx + dy * dy);

	                hornet.setImage("robot.png");

	                if (distance > CHARGESPEED) {
	                    hornet.move(CHARGESPEED * dx / distance, CHARGESPEED * dy / distance);
	                    
	                    if(imagesIntersect(hornet,tiger)) {
	                    	setDamageGiven(MELEEVALUE);
	                    	hornet.setImage("HornetPrototype.gif");
	                    	active = false;
	                    	cancel();
	                    }
	                } else {
	                    hornet.setLocation(tempX, tempY);
	                    hornet.setImage("HornetPrototype.gif");
	                    active = false;
	                    
	                    if(imagesIntersect(hornet,tiger)) {
	                    	setDamageGiven(MELEEVALUE);
	                    	cancel();
	                    }
	                    else {
	                    	cancel();
	                    }
	                }
	            }
	        };

	        timer.scheduleAtFixedRate(chargeTask, 0, 50);
	    }
	 
	public void spawnHornet() {
		//add(hornet);
		parentProgram.add(hornet);
		
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
            	if(!isDead()) {
	            	if(!isPaused) {
		                if (!active) {
		                    glideToTarget();
		                    checkSide();
		                }
	                }
            	}
            }
        };
        timer.scheduleAtFixedRate(moveTask, 0, 50);
        
        
        //GImage temp = new GImage("TigerPlaceHolder.png", 400, 400);
//        tigerTemp.setSize(200, 200);
//        add(tigerTemp);

        TimerTask actionTask = new TimerTask() {
            @Override
            public void run() {
            	if(!isDead()) {
	            	if(!isPaused) {
	            		if (!getActive()) {
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
            	}
            }
        };

        timer.scheduleAtFixedRate(actionTask, 500, 2000);
        
//        Timer t22 = new Timer();
//        t22.scheduleAtFixedRate(new TimerTask() {
//        	@Override
//        	public void run() {
//        		setTigerLoc(tigerTemp);
//        		System.out.println("Health: " + (tempHealth - getDamageGiven()));
//        	}
//        },0,500);
        
	}
//	@Override
//	public void run() {
//		spawnHornet();
//    }
//	public void init() {
//		setSize(1920,1080);
//	}
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//			new Hornet().start();
//	}

}