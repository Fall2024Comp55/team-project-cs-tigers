import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Wave {
	private double hp = 125;
	private static final int WAVEATTACKVALUE = 10;
	private static final int MELEEATTACKVALUE = 5;
	private static final int SEAWEEDATTACKVALUE = 6;
	private static final int BUBBLEBOMBVALUE = 7;
	private static final double SPEED = 15.0;
	private static final double WAVESPEED = 10.0;
	private double WINDOWHEIGHT = 20.0;
	private double WINDOWWIDTH = 20.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private boolean isFacingRight = false;
	private boolean isPaused = false;
	private boolean isDead = false;
	private boolean isWaveActive = false;
	private boolean isTigerDead = false;
	private boolean waveHitCooldown = false;
	private static final double GROUNDLEVEL = 700;
	private int damageGiven = 0;
	private GImage tiger = new GImage("",0,0);
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GraphicsProgram parentProgram;
	private GImage wave = new GImage("Willie.png",800,GROUNDLEVEL);
	
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
	
	public void setTigerDeath() {
		isTigerDead = true;
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
	public boolean isDead() {
		if(hp <= 0) {
			isDead = true;
		}
		
		return isDead;
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
		
		if (isAttackActive) {
	        return; 
	    }
		
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
		waveAttack.scale(0.550);;
		waveAttack.setLocation(WINDOWWIDTH, GROUNDLEVEL - waveAttack.getHeight());
		parentProgram.add(waveAttack);
		//isAttackActive = true;
		isWaveActive = true;
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isPaused) {
					moveWaveAttack(waveAttack,0,GROUNDLEVEL - waveAttack.getHeight());
					if(imagesIntersect(waveAttack,tiger,false)) {
						if(!isPaused) {
							if(!waveHitCooldown) {
								setDamageGiven(WAVEATTACKVALUE);
								waveHitCooldown = true;
								
								new Timer().schedule(new TimerTask() {
									@Override
									public void run() {
										waveHitCooldown = false;
									}
								}, 500);
							}
						}
					}
					
					if(waveAttack.getX() == 0) {
						cancel();
					}
				}
			}
		}, 0, 50);
		
		Timer t2 = new Timer();
		t2.schedule(new TimerTask() {
			@Override
			public void run() {
				//isAttackActive = false;
				isWaveActive = false;
			}
		}, 1000);
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
						isAttackActive = false;
					}
				}, 1000);
			}
		}, 500);
	}
	
	public void bubbleBombAttack() {	
        GImage temp = new GImage("bubble.gif",0,0);
        temp.scale(0.3);
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
                            
                            if(imagesIntersect(temp,tiger,false)) {
                            	if(!isPaused) {
                            		setDamageGiven(BUBBLEBOMBVALUE);
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
        };
        
        new Timer().schedule(bubbleBombTask,0);
    }
	
	private void waterWhip() {
		isAttackActive = true;
		
		if(isFacingRight) {
			wave.setImage("WilliePunchR.png");
		}
		else {
			wave.setImage("WilliePunchL.png");
		}
		
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
		wave.scale(0.4);
		wave.setLocation(1700, GROUNDLEVEL - wave.getHeight());
		parentProgram.add(wave);
		
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isTigerDead) {
					if(!isDead()) {
						if(!isAttackActive) {
							if(!isWalkActive) {
								double tempX = rgen.nextDouble(0,WINDOWWIDTH - wave.getWidth());
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
								
								t.scheduleAtFixedRate(t2, 0, 50);
							}
						}
					}
				}
			}
		}, 0, 500);
		
		Timer attackTimer = new Timer();
		attackTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isTigerDead) {
					if(!isDead()) {
						if(!isPaused) {
							if(!isAttackActive) {
								 double dx = wave.getX() - tiger.getX();
						         double dy = wave.getY() - tiger.getY();
						         double distance = Math.sqrt(dx * dx + dy * dy);
						        if(distance <= 200) {
				                    int choice = rgen.nextInt(1, 10);
				                    if (choice == 10) {
				                        seaweedAttack();
				                    } else if (choice == 9) {
				                    	if(!isWaveActive) {
				                    		waveAttack();
				                    	}
				                    } else if (choice >= 1 && choice <= 7) {
				                        waterWhip();
				                    } else if(choice == 8) {
				                    	bubbleBombAttack();
				                    	bubbleBombAttack();
				                    	bubbleBombAttack();
				                    }
						        }
						        else {
						        	int choice = rgen.nextInt(1, 10);
				                    if (choice >= 1 && choice <= 3) {
				                        seaweedAttack();
				                    } else if (choice >= 8 && choice <= 10) {
				                    	if(!isWaveActive) {
				                    		waveAttack();
				                    	}
				                    }else if(choice >= 4 && choice <= 7 ) {
				                    	bubbleBombAttack();
				                    	bubbleBombAttack();
				                    	bubbleBombAttack();
				                    }
						        }
							}
						}
					}
				}
			}
		},500, 1500);
	}
	public void startMovement() {
		// TODO Auto-generated method stub
		
	}
	public void stopMovement() {
		// TODO Auto-generated method stub
		
	}
	

}
