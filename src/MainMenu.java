import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;

public class MainMenu extends GraphicsProgram {
    private GImage startGif;
    private GImage controlImage;
    private boolean showingControls = false;

    @Override
    public void init() {
        this.setSize(getWidth(), getHeight());

        // Display start GIF
        showStartGif();

        // Add key listeners for user interaction
        addKeyListeners();
    }

    private void setSize(double width, double height) {
        // Placeholder for resizing, handled by ACM
    }

    private void showStartGif() {
        Sound.playBackgroundMusic("media/youtube_ARJzWXXupeE_audio (remux).wav");
        startGif = new GImage("media/Start.gif", 0, 0);
        startGif.setSize(getWidth(), getHeight());
        add(startGif);
    }

    private void showControlScreen() {
        controlImage = new GImage("media/control.png", 0, 0);
        controlImage.setSize(getWidth(), getHeight());
        add(controlImage);
    }

    private void scaleImageToWindow(GImage image) {
        image.setSize(getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_T) { 
            // Handle "T" key for control screen transition
            if (!showingControls) {
                remove(startGif);
                showControlScreen();
                showingControls = true;
            } else {
                remove(controlImage);
                Sound.stopBackgroundMusic();
                GameClass.transitionToRound1(); 
            }
        }

        // Handle "M" key for mute toggle
        if (e.getKeyCode() == KeyEvent.VK_M) { 
            Sound.toggleMute();  // Mute or unmute sound
        }
    }

    @Override
    public void run() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // Rescale images to fit the window size
                if (startGif != null) scaleImageToWindow(startGif);
                if (controlImage != null) scaleImageToWindow(controlImage);
            }
        });
        requestFocus(); // Ensure the screen gets focus for key input
    }

    private void addComponentListener(ComponentAdapter componentAdapter) {
        // Placeholder for ACM event listener
    }

    public static void main(String[] args) {
        new MainMenu().start(); // Launch the main menu
    }
}
