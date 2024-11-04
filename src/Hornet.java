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
    private double LocationX;
    private double LocationY;
    private static final double SPEED = 15.0;
    private TimerTask moveTask;
    private TimerTask moveTask2;
    private TimerTask moveTask3;

	
	public Hornet() {
        setRandomTarget();
    }
	private void setRandomTarget() {
        LocationX = rgen.nextDouble(0, 1400);
        LocationY = rgen.nextDouble(0, 800);
    }
	private void glideToTarget() {
        double dx = LocationX - hornet.getX();
        double dy = LocationY - hornet.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            hornet.move(SPEED * dx / distance, SPEED * dy / distance);
        } else {
            hornet.setLocation(LocationX, LocationY);
            //setRandomTarget();
        }
    }
	private void glideToEnemy(GImage s, double x, double y,boolean t) {
        double dx = x - s.getX();
        double dy = y - s.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            s.move(SPEED * dx / distance, SPEED * dy / distance);
        } else {
        	if(t) {
        		s.setLocation(LocationX, LocationY);
	            //setRandomTarget();
	            //remove(s);
        	}
        	else {
	            remove(s);
        	}
        }
    }
	public void checkSide(double x) {
		if(hornet.getX() > x) {
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
	public void move(double x,double y) {
        double distance = Math.sqrt(x * x + y * y);
        double SPEED = 20.0;
        
        hornet.move(SPEED * x / distance, SPEED * y / distance);
        		
	}
	public void stingerAttack(double x,double y) {
		GImage s = new GImage("robot.png",hornet.getX(),hornet.getY());
		add(s);
		
		moveTask2 = new TimerTask() {
            @Override
            public void run() {
            	glideToEnemy(s,x,y,false);
            }
        };
        timer.scheduleAtFixedRate(moveTask2, 0, 50);
	}
	public void honeyBombAttack(double x) {
		GImage temp = new GImage("robot.png",hornet.getX(),hornet.getY());
		add(temp);
		
		moveTask3 = new TimerTask() {
            @Override
            public void run() {
            	glideToEnemy(temp,x,500,true);
            }
            
        };
        timer.scheduleAtFixedRate(moveTask3, 0, 50);
        
        Timer t = new Timer();
        
        t.schedule(new TimerTask() {
        	@Override
        	public void run() {
        		temp.setImage("explosion.png");
        		
        		Timer t2 = new Timer();
        		
        		//t2.schedule();
        	}
        }, honeyBombValue);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		add(hornet);
		GImage temp = new GImage("TigerPlaceHolder.png",750,500);
		temp.setSize(200, 200);
		add(temp);
		
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setRandomTarget();
                stingerAttack(750,500);
            }
        }, 0, 3000);

        moveTask = new TimerTask() {
            @Override
            public void run() {
                glideToTarget();
                checkSide(600);
            }
        };
        timer.scheduleAtFixedRate(moveTask, 0, 50);
        		
	}
	public void init() {
		setSize(1700,1000);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Hornet().start();
	}

}
