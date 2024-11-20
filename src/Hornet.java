import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Hornet extends GraphicsProgram {
	
	private double hp = 100;
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
    private static final double STINGERSPEED = 20.0;
    private static final double HONEYBOMBSPEED = 20.0;
    private static final double CHARGESPEED = 25.0;
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
	
	public void glideToEnemy(GImage s, double x, double y, double speed) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            s.move(speed * dx / distance, speed * dy / distance);
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
	 public void stingerAttack() {
	 	double tempX = tigerLocX;
	 	double tempY = tigerLocY;
	 	
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
        
        
        
        
        
        add(s);
        
        TimerTask stingerTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, tempY, STINGERSPEED);
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

	                if (distance > HONEYBOMBSPEED) {
	                    temp.move(HONEYBOMBSPEED * dx / distance, HONEYBOMBSPEED * dy / distance);
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

	                if (distance > CHARGESPEED) {
	                    hornet.move(CHARGESPEED * dx / distance, CHARGESPEED * dy / distance);
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
	 
	public void spawnHornet() {
		add(hornet);
		
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
                    glideToTarget();
                    checkSide();
                }
            }
        };
        timer.scheduleAtFixedRate(moveTask, 0, 50);
        
        
        //everything below here is either temp which will get deleted or will go in game class.
        GImage temp = new GImage("TigerPlaceHolder.png", 400, 400);
        temp.setSize(200, 200);
        add(temp);

        TimerTask actionTask = new TimerTask() {
            @Override
            public void run() {
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
        };

        timer.scheduleAtFixedRate(actionTask, 500, 2000);
	}
	@Override
	public void run() {
		spawnHornet();
		//
    }
	public void init() {
		setSize(1920,1080);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Hornet().start();
	}

}
// commenting out rogelios code so i dont ruin anything
/*import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Hornet extends GraphicsProgram {
	
	private double hp = 100;
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
    private static final double STINGERSPEED = 20.0;
    private static final double HONEYBOMBSPEED = 20.0;
    private static final double CHARGESPEED = 25.0;
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
	
	public void glideToEnemy(GImage s, double x, double y, double speed) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            s.move(speed * dx / distance, speed * dy / distance);
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
	 public void stingerAttack() {
	 	double tempX = tigerLocX;
	 	double tempY = tigerLocY;
	 	
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
        
        
        
        
        
        add(s);
        
        TimerTask stingerTask = new TimerTask() {
            @Override
            public void run() {
                glideToEnemy(s, tempX, tempY, STINGERSPEED);
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

	                if (distance > HONEYBOMBSPEED) {
	                    temp.move(HONEYBOMBSPEED * dx / distance, HONEYBOMBSPEED * dy / distance);
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

	                if (distance > CHARGESPEED) {
	                    hornet.move(CHARGESPEED * dx / distance, CHARGESPEED * dy / distance);
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
	 
	public void spawnHornet() {
		add(hornet);
		
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
                    glideToTarget();
                    checkSide();
                }
            }
        };
        timer.scheduleAtFixedRate(moveTask, 0, 50);
        
        
        //everything below here is either temp which will get deleted or will go in game class.
        GImage temp = new GImage("TigerPlaceHolder.png", 400, 400);
        temp.setSize(200, 200);
        add(temp);

        TimerTask actionTask = new TimerTask() {
            @Override
            public void run() {
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
        };

        timer.scheduleAtFixedRate(actionTask, 500, 2000);
	}
	@Override
	public void run() {
		spawnHornet();
		//
    }
	public void init() {
		setSize(1920,1080);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Hornet().start();
	}

}*/