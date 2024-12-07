import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Hornet {
	
	GImage hornet = new GImage("Hornet.gif",800,100);
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double hp = 100;
	private boolean active;
    private double LocationX;
    private double LocationY;
    private int damageGiven = 0;
    private boolean isPaused = false;
    private boolean isFacingRight = false;
    private static final double GROUNDLEVEL = 700.0;
    private static final double SPEED = 15.0;
    private static final double STINGERSPEED = 20.0;
    private static final double HONEYBOMBSPEED = 20.0;
    private static final double CHARGESPEED = 25.0;
    private static final int RANGEATTACKVALUE = 2;
	private static final int MELEEVALUE = 10;
	private static final int HONEYBOMBVALUE = 5;
	private int numOfStingers = 0; 
    private TimerTask moveTask;
	Timer timer = new Timer();
	private GImage tiger = new GImage("",0,0);
	private GraphicsProgram parentProgram;
	private double WINDOWWIDTH = 20.0;
	
//	public int tempHealth = 200;
//    GImage tigerTemp = new GImage("TigerPlaceHolder.png", 400, 400);

	
	public Hornet(GraphicsProgram parentProgram) {
        setRandomTarget();
//        hornet = new GImage("HornetPrototype.gif", 100, 100);
        this.parentProgram = parentProgram;
    }
	
	public void addToProgram(GraphicsProgram program) {
        program.add(hornet);
    }
	public void checkPaused(boolean t) {
		isPaused = t;
	}
	public void setWindowWidth(double h) {
		WINDOWWIDTH = h;
	}
	
	public void setTigerLoc(GImage t) {
		tiger = t;
	}
	public GImage getHornetLoc() {
		return hornet;
	}
	public double getGroundLevel() {
		return GROUNDLEVEL;
	}
	public void setRandomTarget() {
	        LocationX = rgen.nextDouble(0, WINDOWWIDTH - hornet.getWidth());
	        LocationY = rgen.nextDouble(0, GROUNDLEVEL - hornet.getHeight());
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
			hornet.setImage("Hornet.gif");
			isFacingRight = false;
		}
		else {
			hornet.setImage("HornetFlipped.gif");
			isFacingRight = true;
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
	public void setDamageGivenToZero() {
		damageGiven = 0;
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
		 
		 if (numOfStingers >= 3) {
			    return;  
		}
		 
		double tempX = tiger.getX();
		double tempY = tiger.getY();
		double hornetX = hornet.getX();
		double hornetY = hornet.getY();
		 
        GImage s = new GImage("StraightRightStinger.png", hornetX, hornetY);
        
        double dx = tempX - hornetX;
        double dy = tempY - hornetY;
        
        if (dx > 0 && dy > 0) {
            s.setImage("BottomRightCornerStinger.png");
        } else if (dx > 0 && dy < 0) {
            s.setImage("TopRightStinger.png");
        } else if (dx < 0 && dy > 0) {
            s.setImage("BottomLeftCornerStinger.png");
        } else if (dx < 0 && dy < 0) {
            s.setImage("TopLeftCornerStinger.png");
        } else if (dx > 0) {
            s.setImage("StraightRightStinger.png");
        } else if (dx < 0) {
            s.setImage("StraightLeftStinger.png");
        } else if (dy > 0) {
            s.setImage("StraightDownStinger.png");
        } else if (dy < 0) {
            s.setImage("StraightUpStinger.png");
        }
        
        s.scale(0.5);
        
        numOfStingers = numOfStingers + 1;
        
        parentProgram.add(s);
        
        TimerTask stingerTask = new TimerTask() {
            @Override
            public void run() {
            		if(!isPaused) {
		                double dx = tempX - s.getX();
		                double dy = GROUNDLEVEL - s.getY();
		                double distance = Math.sqrt(dx * dx + dy * dy);
	
		                if (distance > STINGERSPEED) {
		                    s.move(STINGERSPEED * dx / distance, STINGERSPEED * dy / distance);
		                    if (imagesIntersect(s, tiger)) {
			                	if(!isPaused) {
				                    setDamageGiven(RANGEATTACKVALUE);
				                    parentProgram.remove(s);
				                    numOfStingers = numOfStingers - 1;
				                   //remove(s);
				                    cancel();
			                	}
			                }
		                    
		                } else {
		                    s.setLocation(tempX, GROUNDLEVEL);
		                    if (imagesIntersect(s, tiger)) {
			                	if(!isPaused) {
				                    setDamageGiven(RANGEATTACKVALUE);
				                    parentProgram.remove(s);
				                    numOfStingers = numOfStingers - 1;
				                   //remove(s);
				                    cancel();
			                	}
			                }
		                    parentProgram.remove(s);
		                    numOfStingers = numOfStingers - 1;
		                    cancel();
		                }
            	}
            }   
        };
        
        timer.scheduleAtFixedRate(stingerTask, 0, 50);        
	 }
	 public void honeyBombAttack() {	
		 	double tempX = tiger.getX();
	        GImage temp = new GImage("honeyBomb.png", hornet.getX(), hornet.getY());
	        temp.scale(0.5);
	        parentProgram.add(temp);
	        
	        TimerTask honeyBombTask = new TimerTask() {
	            @Override
	            public void run() {
	            	if(!isPaused) {
		                double dx = tempX - temp.getX();
		                double dy = (GROUNDLEVEL - temp.getHeight()) - temp.getY();
		                double distance = Math.sqrt(dx * dx + dy * dy);
	
		                if (distance > HONEYBOMBSPEED) {
		                    temp.move(HONEYBOMBSPEED * dx / distance, HONEYBOMBSPEED * dy / distance);
		                } else {
		                    temp.setLocation(tempX, GROUNDLEVEL - temp.getHeight());
		                    cancel();
	
		                    new Timer().schedule(new TimerTask() {
		                        @Override
		                        public void run() {
		                            temp.setImage("explosion.gif");
		                            
		                            if(imagesIntersect(temp,tiger)) {
		                            	if(!isPaused) {
		                            		setDamageGiven(HONEYBOMBVALUE);
		                            	}
		                            }
	
		                            new Timer().schedule(new TimerTask() {
		                                @Override
		                                public void run() {
		                                    parentProgram.remove(temp);
		                                }
		                            }, 1000);
		                        }
		                    }, 1500);
		                }
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
	            	if(!isPaused) {
		                double dx = tempX - hornet.getX();
		                double dy = tempY - hornet.getY();
		                double distance = Math.sqrt(dx * dx + dy * dy);
	
		                if(isFacingRight) {
		                	hornet.setImage("HornetChargeFlipped.gif");
		                }
		                else {
		                	hornet.setImage("HornetCharge.gif");
		                }
	
		                if (distance > CHARGESPEED) {
		                    hornet.move(CHARGESPEED * dx / distance, CHARGESPEED * dy / distance);
		                    
		                    if(imagesIntersect(hornet,tiger)) {
		                    	if(!isPaused) {
			                    	setDamageGiven(MELEEVALUE);
			                    	if(!isFacingRight) {
			                    		hornet.setImage("Hornet.gif");
			                    	}
			                    	else {
			                    		hornet.setImage("HornetFlipped.gif");
			                    	}
			                    	active = false;
			                    	cancel();
		                    	}
		                    }
		                } else {
		                    hornet.setLocation(tempX, tempY);
		                    if(!isFacingRight) {
	                    		hornet.setImage("Hornet.gif");
	                    	}
	                    	else {
	                    		hornet.setImage("HornetFlipped.gif");
	                    	}
		                    active = false;
		                    
		                    if(imagesIntersect(hornet,tiger)) {
		                    	if(!isPaused) {
			                    	setDamageGiven(MELEEVALUE);
			                    	cancel();
		                    	}
		                    }
		                    else {
		                    	cancel();
		                    }
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
	            			int choice = rgen.nextInt(1, 10);
			                if (choice >= 9 && choice <= 10) {
			                    chargeAttack();
			                } else if (choice >= 1 && choice <= 4) {
			                	if(numOfStingers < 3) {
			                		stingerAttack();
			                	}
			                } else if (choice >= 5 && choice <= 8) {
			                    honeyBombAttack();
			                }
	                	}
	                }
            	}
            }
        };

        timer.scheduleAtFixedRate(actionTask, 500, 1500);
        
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
// round1
	public void stopMovement() {
		// TODO Auto-generated method stub
		
	}

	public void startMovement() {
		// TODO Auto-generated method stub
		
	}

}