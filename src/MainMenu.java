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
		// TODO Auto-generated method stub
		
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
            if (!showingControls) {
                // Transition from start screen to controls
                remove(startGif);
                showControlScreen();
                showingControls = true;
            } else {
                // Transition from controls to Round1
                remove(controlImage);
                Sound.stopBackgroundMusic();
                GameClass.transitionToRound1(); // Move to Round1 after control screen
            }
        }
    }

    @Override
    public void run() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // scale images to fit the window size
                if (startGif != null) scaleImageToWindow(startGif);
                if (controlImage != null) scaleImageToWindow(controlImage);
            }
        });
        requestFocus();
    }

    private void addComponentListener(ComponentAdapter componentAdapter) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        new MainMenu().start();
    }
} 