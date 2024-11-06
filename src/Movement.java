import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Movement extends GraphicsProgram {
	public static final int PROGRAM_HEIGHT = 1080;
	public static final int PROGRAM_WIDTH = 1920;
	private GImage tigerTesting = new GImage("TigerPlaceHolder.png", 300, 100); //image for movement testing
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 	case KeyEvent.VK_UP:
		 		tigerTesting.move(0, -5); // Move up
	            break;
	        case KeyEvent.VK_DOWN:
	            tigerTesting.move(0, 5); // Move down
              break;
          case KeyEvent.VK_LEFT:
	            tigerTesting.move(-15, 0); // Move left
	            break;
	        case KeyEvent.VK_RIGHT:
	            tigerTesting.move(15, 0); // Move right
	            break;
	        }
	    }
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}	
	
	
	
	
	public void run() {
        add(tigerTesting);
        tigerTesting.setSize(tigerTesting.getWidth() / 3, tigerTesting.getHeight() / 3);
        addKeyListeners();
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		new Movement().start();

	}

}
