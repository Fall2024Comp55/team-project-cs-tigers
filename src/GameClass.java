//import java.util.Scanner;
import javax.swing.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;

public class GameClass {

	public static void main (String[]args) {
	    System.out.println("Game is starting!!!\n");
	    
	    GameClass.startInteractiveScript();
	    DisplayStartScreen();
	    
	}

	
	private static void DisplayStartScreen() {
		SwingUtilities.invokeLater(() -> new DisplayStartScreen());		
		
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
	
	public static void startInteractiveScript() {
        SwingUtilities.invokeLater(() -> new InteractiveScriptScreen());
    }
	
}
