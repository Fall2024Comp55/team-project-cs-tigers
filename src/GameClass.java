import acm.program.*;
import java.awt.event.KeyEvent;

public class GameClass extends GraphicsProgram {

    private static GraphicsProgram currentScreen; // Active screen
    private static boolean isTransitioning = false; // Prevents overlapping transitions

    public static void main(String[] args) {
        new GameClass().start(); // Start the application
    }

    @Override
    public void init() {
        addKeyListeners();  // Register key events
    }

    @Override
    public void run() {
        startMainMenu(); // Start the game with the Main Menu
    }

    // Start Main Menu
    public static void startMainMenu() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Starting Main Menu...");
            switchScreen(new MainMenu());  // Switch to MainMenu
            isTransitioning = false;
        }
    }

    // Transition to Round1
    public static void transitionToRound1() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Transitioning to Round1...");
            Sound.stopBackgroundMusic();
            switchScreen(new Round1());
            isTransitioning = false;
        }
    }

    // Transition to Round2
    public static void transitionToRound2(boolean playerWon) {
        if (!isTransitioning) {
            isTransitioning = true;
            Sound.stopBackgroundMusic();
            if (playerWon) {
                System.out.println("Transitioning to Round2...");
                switchScreen(new Round2());
            } else {
                System.out.println("Player lost in Round1. Returning to MainMenu.");
                switchScreen(new MainMenu());
            }
            isTransitioning = false;
        }
    }

    // Transition to Round3
    public static void transitionToRound3(boolean playerWon) {
        if (!isTransitioning) {
            isTransitioning = true;
            Sound.stopBackgroundMusic();
                
            if (playerWon) {
                System.out.println("Transitioning to Round3...");
                switchScreen(new Round3());
            } else {
                System.out.println("Player lost in Round2. Returning to MainMenu.");
                switchScreen(new MainMenu());
            }
            isTransitioning = false;
        }
    }

    // Handle Key Events (M to Mute)
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {  // Check if "M" is pressed
            Sound.toggleMute();  // Mute or unmute the sound
        }
    }

    // Cleanly switch screens
    private static void switchScreen(GraphicsProgram newScreen) {
        if (currentScreen != null) {
            currentScreen.removeAll(); // Clear all content from the current screen
        }
        currentScreen = newScreen;     // Assign the new screen
        currentScreen.start();         // Start the new screen
    }
}

//CREDITS: 
//COMP55 PowerCat Protector of UOP Project
//Fall 2024 
//
//Created by:
//Paul Marro 
//Rogelio Calderon
//Faizah Muthana
//Ibrahim Tahir
//
//Sound credits:
//
//Street Fighter II Arcade Music - Opening Theme - CPS1
//
//Street Fighter II Arcade Music - Sagat Stage - CPS1
//
//Street Fighter II Arcade Music - Ken Stage - CPS1
//
//Street Fighter II Arcade Music - Chun Li Stage - CPS1
//
//Street Fighter II: Win Theme (1991)
//
//Punch_02.wav by thefsoundman -- https://freesound.org/s/118513/ -- License: Creative Commons 0
//
//Swoosh.ogg by WizardOZ -- https://freesound.org/s/419341/ -- License: Creative Commons 0
//
//Bomb - Small by Zangrutz -- https://freesound.org/s/155235/ -- License: Attribution 3.0
//
//Whip 01.wav by erkanozan -- https://freesound.org/s/51755/ -- License: Creative Commons 0
//
//Popping Bubble Wrap 8 by Geoff-Bremner-Audio -- https://freesound.org/s/754232/ -- License: Creative Commons 0
//
//Bubbles6.wav by kwahmah_02 -- https://freesound.org/s/261568/ -- License: Attribution 3.0
//
//SeaweedSlide_01.WAV by gmtechb -- https://freesound.org/s/49172/ -- License: Attribution 3.0
//
//thunder3.ogg by Josh74000MC -- https://freesound.org/s/475094/ -- License: Creative Commons 0
//
//Heavy Impacts by RICHERlandTV -- https://freesound.org/s/232358/ -- License: Attribution 4.0
//
//whoosh04.wav by FreqMan -- https://freesound.org/s/25073/ -- License: Attribution 4.0

