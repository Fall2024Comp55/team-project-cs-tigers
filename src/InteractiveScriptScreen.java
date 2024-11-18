import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InteractiveScriptScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private Image backgroundImage; // The background image
    private float opacity = 1.0f; // Start fully visible
    private Timer fadeTimer;

    public InteractiveScriptScreen() {
        // Load the background image
        backgroundImage = new ImageIcon("/Users/ibrahimtahir/git/team-project-cs-tigers1/media/BG2.png").getImage(); // Set the correct path

        // Set up the frame
        setTitle("Interactive Script");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add a custom panel to display the background and text
        InteractivePanel interactivePanel = new InteractivePanel();
        add(interactivePanel, BorderLayout.CENTER);

        // Add key listener to detect when the user presses Enter or T
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Proceed when Enter is pressed (for example, for script continuation)
                    moveToNextScreen();
                } else if (e.getKeyCode() == KeyEvent.VK_T) {
                    // Proceed to the actual game when "T" is pressed
                    startFadeOut();
                }
            }
        });

        // Make the frame visible and focused
        setVisible(true);
        setFocusable(true); // Ensure it can receive key events

        // Create the "Proceed" button
        JButton proceedButton = new JButton("Proceed");
        proceedButton.setFont(new Font("Arial", Font.PLAIN, 20));
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startFadeOut();
            }
        });
    }

    // Custom JPanel that overrides paintComponent to draw the background image
    private class InteractivePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set the transparency based on opacity
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Method to start the fade-out effect
    private void startFadeOut() {
        // Create a timer to gradually reduce opacity
        fadeTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f; // Decrease opacity
                if (opacity <= 0) {
                    // Stop the timer once fully faded
                    fadeTimer.stop();
                    startGame(); // Start the actual game after fade-out
                }
                repaint(); // Repaint to update the screen with new opacity
            }
        });
        fadeTimer.start();
    }

    // Method to transition to the next screen (when Enter is pressed)
    private void moveToNextScreen() {
        // You can add logic to move to the next part of the script
        System.out.println("Proceeding to next part of the script...");
    }

    // Method to start the actual game (when "T" is pressed)
    private void startGame() {
        // Close this screen and transition to the game
        dispose(); // Close current frame
        System.out.println("Starting the game...");
        
        // Transition to the actual game screen
        // Replace this with the actual game logic or screen you want to display
        //new GameScreen(); // Make sure to implement this class for the game screen
    }

    public static void main(String[] args) {
        new InteractiveScriptScreen();
    }
}
