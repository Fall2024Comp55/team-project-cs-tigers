import java.util.Scanner;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameClass {

	public static void main (String[]args) {
	    System.out.println("Game is starting!!!\n");
	    
	    displayStartScreen();
	}
	
	public static void displayStartScreen() {
	    System.out.println("Welcome to the Game!");
	    System.out.println("Press any key to start...");
	    
	    Scanner userKey = new Scanner (System.in);
	    
	   // if(public void keyPressed){;} checking if user pressed a key 
}
	
	public void displayEndScreen() {
		System.out.println("Game over\n");
		System.out.println("Thank you for playing.");
	}
	
	public void registerPlayer(String name) {
		System.out.println("Welcome, " + name + "!");
	}
	
	public void nextLevel() {
	    System.out.println("Get ready for the next level!");
//reset player health for the next level
	    //method to be added 
	    
	    displayGameInfo();
	}

	private void displayGameInfo() {
		// data to be added in terms of player victory or loss, health, points/score
		//score+=1
		//System.out.println("Your Score is" + score);
		
		
	}

	
}
