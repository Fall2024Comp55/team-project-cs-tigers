import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
	private Image backgroundImage;
    private float alpha = 1.0f;  // Start fully opaque

    public GameFrame() {
        // Load the image
        backgroundImage = new ImageIcon("/Users/ibrahimtahir/git/team-project-cs-tigers1/media/BG1.png").getImage();

        // Set up the frame
        setTitle("PowerCat, Protector of UOP");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void displayStartScreen() {
        // Add the custom panel and start the fade effect
        add(new FadePanel());
        startFadeEffect();
    }

    private void startFadeEffect() {
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.02f;
                if (alpha <= 0) {
                    alpha = 0;
                    ((Timer) e.getSource()).stop();  // Stop the timer when fade is complete
                }
                repaint();  // Refresh the screen to show the updated transparency
            }
        });
        timer.start();
    }

    private class FadePanel extends JPanel {
        private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
