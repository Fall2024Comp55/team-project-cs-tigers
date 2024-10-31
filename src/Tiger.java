import acm.graphics.*;

public class Tiger {
	public static final int hp = 100; //temp hp value change later
	public static final int attack_val = 10; //temp attack value change later
	public static final int speed = 1; //temp value that affects the character's movement speed if movement logic changes remove this
	private GImage idle;
	private GImage attack;
	private GImage specialAttack;
	
	public int getHP() {
		return hp;
	}
	
	public int getAtkVal() {
		return attack_val;
	}
	
	public int specialAttackVal() { 
		return attack_val * 2;
	}
	
	public GImage returnIdle() {
		return GImage idle;
	}
	
	public GImage getAttackAnimation() {
		return GImage attack;
	}
	
	public GImage specialAttack() {
		return Image specialAttack;
	}
	
	
	
	//public GImage spawnTiger(getXPosition, getYPosition) { //placeholder for spawnTiger function change/replace as needed
		//return //GImage specialTiger = new(insert image file when we have, getXPosition, getYPosition)
		
	
	//	}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

