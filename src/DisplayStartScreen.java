import javax.swing.*;
import java.awt.*;

public class DisplayStartScreen extends JFrame {
    private static final long serialVersionUID = 1L;
	private Image backgroundImage;

    public DisplayStartScreen() {
        // Load the image
        backgroundImage = new ImageIcon("/Users/ibrahimtahir/git/team-project-cs-tigers1/media/BG1.png").getImage();

        // Set up the frame
        setTitle("PowerCat, Protector of UOP!");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a custom JPanel
        add(new BackgroundPanel());
        setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        new DisplayStartScreen();
    }
}
