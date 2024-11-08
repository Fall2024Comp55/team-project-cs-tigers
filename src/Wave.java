import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Wave {
	private GImage wave = new GImage("Robot.png",500,500);
	private int hp = 100;
	private String stageName = "Chris Kjeldsen Pool";
	private int waveAttackValue = 10;
	private int meleeValue = 5;
	private int seaweedAttackValue = 15;
	private double tigerLocX;
	private double tigerLocY;
	
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
	
	public int getWaveAttackValue() {
		return waveAttackValue;
	}
	
	public int getMeleeValue() {
		return meleeValue;
	}
	
	public int getSeaweedValue() {
		return seaweedAttackValue;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
