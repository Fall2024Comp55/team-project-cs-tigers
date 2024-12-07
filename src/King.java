import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class King {
	
	private double hp = 150;
	private int damageGiven = 0;
	private static final int TRIDENTATTACKVALUE = 3;
	private static final int MELEEVALUE = 2;
	private static final int GRENADEATTACKVALUE = 5;
	private static final int LIGHTINGBOLTVALUE = 8;
	private double WINDOWHEIGHT = 20.0;
	private double WINDOWWIDTH = 20.0;
	private static final double SPEED = 15.0;
	private static final double THROWSPEED = 20.0;
	private static final double LIGHTINGSPEED = 25.0;
	private static final double GRENADESPEED = 25.0;
	private boolean isWalkActive = false;
	private boolean isAttackActive = false;
	private boolean isFacingRight = false;
	private boolean isPaused = false;
	private boolean isTridentThrowActive = false;
	private static final double GROUNDLEVEL = 800;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GImage king = new GImage("KingLeft.png",0,GROUNDLEVEL);
	private GImage tiger = new GImage("",0,0);
	private GraphicsProgram parentProgram;
	private double kingWidth = 0;
	private double kingHeight = 0;
	
	public King(GraphicsProgram parentProgram) {
		this.parentProgram = parentProgram;
	}
	public void addToProgram(GraphicsProgram program) {
        program.add(king);
    }
	public void checkPaused(boolean t) {
		isPaused = t;
	}
	public void setTigerLoc(GImage s) {
		tiger = s;
	}
	public void setWindowHeight(double h) {
		WINDOWHEIGHT = h;
	}
	public void setWindowWidth(double h) {
		WINDOWWIDTH = h;
	}
	public GImage getKingLoc() {
		return king;
	}
	public void setDamageGiven(int i) {
		damageGiven = damageGiven + i;
	}
	public void setDamageGivenToZero() {
		damageGiven = 0;
	}
	public double getGroundLevel() {
		return GROUNDLEVEL;
	}
	public int getDamageGiven() {
		return damageGiven;
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
	public int getTridentAttackValue() {
		return TRIDENTATTACKVALUE;
	}
	public int getMeleeValue() {
		return MELEEVALUE;
	}
	public int getGrenadeValue() {
		return GRENADEATTACKVALUE;
	}
	public int getLightingValue() {
		return LIGHTINGBOLTVALUE;
	}
	public double getCenterX() {
		return (king.getX() + king.getWidth())/2;
	}
	public double getCenterY() {
		return (king.getY() + king.getHeight())/2;
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
		if(king.getX() > tiger.getX()) {
			king.setImage("tritonLeft.png");
			//king.setSize(KINGWIDTH, KINGHEIGHT);
			isFacingRight = false;
		}
		else {
			king.setImage("KingRight.png");
			//king.setSize(KINGWIDTH, KINGHEIGHT);
			isFacingRight = true;
		}
	}
	//
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
	
	public void glideToEnemy(GImage s, double x, double y, double speed) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            s.move(speed * dx / distance, speed * dy / distance);
        } else {
            s.setLocation(x, y);
        }
    }
	
	public void grenadeAttack() {	
        GImage temp = new GImage("LightingBall.gif", king.getX(), king.getY());
        temp.setSize(120, 120);
        double tempX = rgen.nextDouble(0,1080 - 120);
        parentProgram.add(temp);
        //add(temp);
        
        TimerTask lightingBombTask = new TimerTask() {
            @Override
            public void run() {
            	if(!isPaused) {
	                double dx = tempX - temp.getX();
	                double dy = (GROUNDLEVEL - temp.getHeight()) - temp.getY();
	                double distance = Math.sqrt(dx * dx + dy * dy);
	
	                if (distance > GRENADESPEED) {
	                    temp.move(GRENADESPEED * dx / distance, GRENADESPEED * dy / distance);
	                } else {
	                    temp.setLocation(tempX, GROUNDLEVEL - temp.getHeight());
	                    cancel();
	
	                    new Timer().schedule(new TimerTask() {
	                        @Override
	                        public void run() {
	                            temp.setImage("explosion.gif");
	                            temp.setSize(120, 120);
	                            
	                            if(imagesIntersect(temp,tiger,false)) {
	                            	if(!isPaused) {
	                            		setDamageGiven(GRENADEATTACKVALUE);
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
	                    }, 750);
	                }
            	}
            }
        };
        
        new Timer().scheduleAtFixedRate(lightingBombTask, 0, 50);
    }
	
	public void lightingBolt() {
		double tempX = rgen.nextDouble(0,WINDOWWIDTH - 120);
        GImage s = new GImage("LightingBolt.gif", tempX, 0);
        s.setSize(120,120);
        parentProgram.add(s);
        //add(s);
        
        TimerTask lightingTask = new TimerTask() {
            @Override
            public void run() {
            	if(!isPaused) {
	                glideToEnemy(s, tempX, GROUNDLEVEL,LIGHTINGSPEED);
	                
	                if(imagesIntersect(s,tiger,false)) {
	                	if(!isPaused) {
							setDamageGiven(LIGHTINGBOLTVALUE);
							parentProgram.remove(s);
							//remove(s);
							cancel();
	                	}
					}
	                
	                if (s.getX() == tempX && s.getY() == GROUNDLEVEL) {
						parentProgram.remove(s);
	                	//remove(s);
	                    cancel();
	                }
            	}
            }
        };
        
        new Timer().scheduleAtFixedRate(lightingTask, 0, 50);
	}
	
	public void tridentThrowAttack() {
		isTridentThrowActive = true;
	 	double tempX = tiger.getX();
	 	double tempY = tiger.getY();
 		GImage s = new GImage("LightingTrident.gif", king.getX(), king.getY());
 		
	 	if(king.getX() - tiger.getX() > 0) {
	 		s.setImage("LightingTridentFlipped.gif");
	 	}
	 	
        s.setSize(200,200);
        parentProgram.add(s);
        
        TimerTask tridentTask = new TimerTask() {
            @Override
            public void run() {
            	if(!isPaused) {
	                glideToEnemy(s, tempX, tempY, THROWSPEED);
	                
	                if(imagesIntersect(s,tiger,false)) {
	                	if(!isPaused) {
							setDamageGiven(TRIDENTATTACKVALUE);
							parentProgram.remove(s);
							isTridentThrowActive = false;
							cancel();
	                	}
					}
	                
	                if (s.getX() == tempX && s.getY() == tempY) {
						parentProgram.remove(s);
						isTridentThrowActive = false;
	                    cancel();
	                }
            	}
            }
        };
        
        new Timer().scheduleAtFixedRate(tridentTask, 0, 50);
	 }
	
	public void tridentStab() {
		if(isFacingRight) {
			king.setImage("tritonPunchR.png");
		}
		else {
			king.setImage("tritonPunchL.png");
		}
		
		//king.setSize(kingWidth, kingHeight);
		
		if(imagesIntersect(king,tiger,true)) {
			if(!isPaused) {
				setDamageGiven(MELEEVALUE);
			}
		}
		
		Timer temp = new Timer();
		
		temp.schedule(new TimerTask() {
			@Override
			public void run() {
				if(isWalkActive) {
					if(isFacingRight) {
						king.setImage("KingRight.png");
					}
					else {
						king.setImage("tritonLeft.png");
					}
				}
				else {
					if(isFacingRight) {
						king.setImage("KingRight.png");
					}
					else {
						king.setImage("tritonLeft.png");
					}
				}
			}
		}, 300);
	}
	
	public void spawnKing() {
		//king.setSize(KINGWIDTH, KINGHEIGHT);
		//add(king);
		king.setLocation(800, GROUNDLEVEL - king.getHeight());
		kingWidth = king.getWidth();
		kingHeight = king.getHeight();
		parentProgram.add(king);
		
		Timer movementTimer = new Timer();
		movementTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isDead()) {
					if(!isAttackActive) {
						if(!isWalkActive) {
							double tempX = rgen.nextDouble(0,WINDOWWIDTH - king.getWidth());
							double tempY = GROUNDLEVEL - king.getHeight();
							
							Timer t = new Timer();
							TimerTask t2 = new TimerTask() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(!isPaused) {
										walkToEnemy(king,tempX,tempY);
										isWalkActive = true;
										
										if(king.getX() == tempX && king.getY() == tempY) {
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
		}, 0, 500);
		
		Timer attackTimer = new Timer();
		attackTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(!isDead()) {
					if(!isPaused) {
						if(!isAttackActive) {
							 double dx = king.getX() - tiger.getX();
					         double dy = king.getY() - tiger.getY();
					         double distance = Math.sqrt(dx * dx + dy * dy);
					        if(distance <= 200) {
			                    int choice = rgen.nextInt(1, 10);
			                    if (choice == 10) {
			                    	grenadeAttack();
			                    	grenadeAttack();
			                    	grenadeAttack();
			                    } else if (choice == 9) {
			                    	if(!isTridentThrowActive) {
			                    		tridentThrowAttack();
			                    	}
			                    } else if (choice >= 1 && choice <= 7) {
			                        tridentStab();
			                    } else if(choice == 8) {
			                    	lightingBolt();
			                    	lightingBolt();
			                    	lightingBolt();
			                    }
					        }
					        else {
					        	int choice = rgen.nextInt(1, 10);
			                    if (choice >= 1 && choice <= 3) {
			                    	lightingBolt();
			                    	lightingBolt();
			                    	lightingBolt();
			                    } else if (choice >= 7 && choice <= 10) {
			                    	if(!isTridentThrowActive) {
			                    		tridentThrowAttack();
			                    	}
			                    }else if(choice >= 4 && choice <= 6 ) {
			                    	grenadeAttack();
			                    	grenadeAttack();
			                    	grenadeAttack();
			                    }
					        }
						}
					}
				}
			}
		},500, 1500);
		
		
	}
}
