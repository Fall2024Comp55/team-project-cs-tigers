import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Wave {
	private double hp = 100;
	private String stageName = "Chris Kjeldsen Pool";
	private static final int WAVEATTACKVALUE = 10;
	private static final int MELEEATTACKVALUE = 5;
	private static final int SEAWEEDATTACKVALUE = 15;
	private static final int BUBBLEBOMBVALUE = 15;
	private int ATTACKSPEED = 1000;
	private static final double SPEED = 15.0;
	private static final double WAVESPEED = 20.0;
	private double WINDOWHEIGHT = 20.0;
	private double WINDOWWIDTH = 20.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private boolean isFacingRight = false;
	private boolean isPaused = false;
	private static final double GROUNDLEVEL = 700;
	private int damageGiven = 0;
	private GImage tiger = new GImage("",0,0);
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GraphicsProgram parentProgram;
	private GImage wave = new GImage("Willie.png",800,GROUNDLEVEL);
	private GRect rect = new GRect(wave.getX(),wave.getY());
	
//	public int tempHealth = 200;
    //GImage tigerTemp = new GImage("Hornet.gif", 500, GROUNDLEVEL - 320);

	public Wave(GraphicsProgram parentProgram) {
		this.parentProgram = parentProgram;
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
	
	public GImage getWaveLoc() {
		return wave;
	}
	
	public String getStage() {
		return stageName;
	}
	
	public double getGroundLevel() {
		return GROUNDLEVEL;
	}
	
	public void setWindowHeight(double h) {
		WINDOWHEIGHT = h;
	}
	public void setWindowWidth(double h) {
		WINDOWWIDTH = h;
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
		return (wave.getX() + wave.getWidth()) /2;
	}
	public double getCenterY() {
		return (wave.getY() + wave.getHeight())/2;
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
			//if(!isAttackActive) {
				wave.setImage("Willie.png");
				isFacingRight = false;
			//}
		}
		else {
			//if(!isAttackActive) {
				wave.setImage("WillieFlipped.png");
				isFacingRight = true;
			//}
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
		
//		if (isAttackActive) {
//	        return; 
//	    }
		
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
            checkSide();
        } else {
        		s.setLocation(x, y);
        		//isWalkActive = false;
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
        		//isAttackActive = false;
        }
	}
	
	private void waveAttack() {
		GImage waveAttack = new GImage("Wave.gif",1800,GROUNDLEVEL);
		//add(waveAttack);
		waveAttack.scale(0.7);;
		waveAttack.setLocation(WINDOWWIDTH - waveAttack.getWidth(), GROUNDLEVEL - waveAttack.getHeight());
		parentProgram.add(waveAttack);
		isAttackActive = true;
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isPaused) {
					moveWaveAttack(waveAttack,0,GROUNDLEVEL - waveAttack.getHeight());
					if(imagesIntersect(waveAttack,tiger,false)) {
						if(!isPaused) {
							setDamageGiven(WAVEATTACKVALUE);
						}
					}
					
					if(waveAttack.getX() == 0) {
						cancel();
					}
				}
			}
		}, 0, 500);
		
		Timer t2 = new Timer();
		t2.schedule(new TimerTask() {
			@Override
			public void run() {
				isAttackActive = false;
			}
		}, 300);
	}
	
	private void seaweedAttack() {
		isAttackActive = true;
		
		if(!isFacingRight) {
			wave.setImage("SeaweedPoseCropped.png");
		}
		else {
			wave.setImage("SeaweedPoseCroppedFlipped.png");
		}
		
		double tempX = tiger.getX();
		
		Timer temp = new Timer();
		temp.schedule(new TimerTask() {
			@Override
			public void run() {
				//wave.setImage();
				GImage seaweed = new GImage("Seaweed.gif",500,GROUNDLEVEL);
				//seaweed.setLocation(tempX, GROUNDLEVEL - seaweed.getHeight());
				//add(seaweed);
				seaweed.scale(0.5);
				seaweed.setLocation(tempX, GROUNDLEVEL - seaweed.getHeight());
				parentProgram.add(seaweed);
				isAttackActive = false;
				
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
						//isAttackActive = false;
					}
				}, 1000);
			}
		}, 500);
	}
	
	public void bubbleBombAttack() {	
        GImage temp = new GImage("bubble.gif",0,0);
        temp.scale(0.5);
        temp.setLocation(rgen.nextDouble(0,WINDOWWIDTH - temp.getWidth()), rgen.nextDouble(GROUNDLEVEL - temp.getHeight()));
        //add(temp);
        parentProgram.add(temp);
        
        TimerTask bubbleBombTask = new TimerTask() {
            @Override
            public void run() {
            	if(!isPaused) {
            		new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            temp.setImage("explosion.gif");
                            //temp.scale(0.5);
                            
                            if(imagesIntersect(temp,tiger,false)) {
                            	if(!isPaused) {
                            		setDamageGiven(BUBBLEBOMBVALUE);
                            	}
                            }

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //remove(temp);
                                    parentProgram.remove(temp);
                                }
                            }, 1000);
                        }
                    }, 1500);
            	}
            }
        };
        
        new Timer().schedule(bubbleBombTask,0);
    }
	
	private void waterWhip() {
		isAttackActive = true;
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
					if(!isFacingRight) {
						wave.setImage("Willie.png");
					}
					else {
						wave.setImage("WillieFlipped.png");
					}
				}
				else {
					if(!isFacingRight) {
						wave.setImage("Willie.png");
					}
					else {
						wave.setImage("WillieFlipped.png");
					}
				}
				
				isAttackActive = false;
			}
		}, 300);
	}
	
	public void spawnWave() {
		//add(wave);
		wave.scale(0.4);
		wave.setLocation(1700, GROUNDLEVEL - wave.getHeight());
		parentProgram.add(wave);
//		rect.setLocation(wave.getX(), wave.getY());
//		rect.setSize(wave.getWidth(), wave.getHeight());
//		rect.setFilled(true);
//		parentProgram.add(rect);
		
		//
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//if(!isAttackActive) {
					if(!isWalkActive) {
						double tempX = rgen.nextDouble(0,WINDOWHEIGHT - wave.getHeight());
						double tempY = GROUNDLEVEL - wave.getHeight();
						
						Timer t = new Timer();
						TimerTask t2 = new TimerTask() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(!isPaused) {
									walkToEnemy(wave,tempX,tempY);
									isWalkActive = true;
									
									if(wave.getX() == tempX && wave.getY() == tempY) {
										isWalkActive = false;
										cancel();
									}
								}
							}
						};	
						
						t.scheduleAtFixedRate(t2, 0, 200);
					}
				}
			//}
		}, 0, 500);
		
		Timer attackTimer = new Timer();
		attackTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isPaused) {
					 double dx = wave.getX() - tiger.getX();
			         double dy = wave.getY() - tiger.getY();
			         double distance = Math.sqrt(dx * dx + dy * dy);
			        if(distance <= 200) {
			        	ATTACKSPEED = 300;
	                    int choice = rgen.nextInt(1, 10);
	                    if (choice == 10) {
	                        seaweedAttack();
	                    } else if (choice == 9) {
	                        waveAttack();
	                    } else if (choice >= 1 && choice <= 7) {
	                        waterWhip();
	                    } else if(choice == 8) {
	                    	bubbleBombAttack();
	                    	bubbleBombAttack();
	                    	bubbleBombAttack();
	                    }
			        }
			        else {
			        	ATTACKSPEED = 2000;
			        	int choice = rgen.nextInt(1, 10);
	                    if (choice >= 1 && choice <= 3) {
	                        seaweedAttack();
	                    } else if (choice >= 8 && choice <= 10) {
	                        waveAttack();
	                    }else if(choice >= 4 && choice <= 7 ) {
	                    	bubbleBombAttack();
	                    	bubbleBombAttack();
	                    	bubbleBombAttack();
	                    }
			        }
				}
			}
		},500, 1500);
		
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
