import javax.swing.*; //GUI
import java.awt.*; // dimensions 
import java.util.Timer;
import java.util.TimerTask;

public class Round1 extends Round {
    private JLabel hornetLabel; // represents hornet character
    private JLabel backgroundLabel; // represents background
    private JButton pauseButton; // pause button
    private boolean isPaused = false; // tracks pause state
    private int hornetX = 540; // initial x position of the hornet
    private int hornetY = 300; // initial y position of the hornet
    private Timer hornetMovementTimer; // timer for hornet movement
    private JDialog parentDialog; // parent dialog to manage modal display

    public static void main(String[] args) {
        Round1 round1 = new Round1();
        round1.startRound();
    }

    @Override
    public void startRound() {
        updateCaption("Welcome to Round 1: Tiger vs. Hornet at Burn's Tower!");
        showContent(); // show content for round 1
        startHornetMovement(); // start the hornet's movement
    }

    @Override
    public void showContent() {
        // create a layered pane for component stacking
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1280, 720));

        // set up background (add this FIRST to the lowest layer)
        ImageIcon backgroundImage = new ImageIcon(new ImageIcon("media/BurnsBackground.png")
                .getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH)); // scale background
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1280, 720); // set size
        layeredPane.add(backgroundLabel, Integer.valueOf(0)); // add to the lowest layer

        // set up hornet character (add to the next layer)
        ImageIcon hornetImage = new ImageIcon("media/HornetPrototype.gif"); // hornet placeholder
        hornetLabel = new JLabel(hornetImage);
        hornetLabel.setBounds(hornetX, hornetY, 150, 150); // initial position
        layeredPane.add(hornetLabel, Integer.valueOf(1)); // add hornet on top of background

        // set up pause button (add to the topmost layer)
        pauseButton = new JButton("Pause");
        pauseButton.setBounds(1150, 20, 100, 40); // position in the upper-right corner
        pauseButton.addActionListener(e -> {
            isPaused = true; // set pause state to true
            stopHornetMovement(); // stop hornet movement immediately
            SwingUtilities.invokeLater(() -> {
            	// makes sure the code inside { ... } runs safely on the special GUI thread.

                PauseMenu pauseMenu = new PauseMenu();
                pauseMenu.pauseGame(parentDialog, this::resumeGame); // show pause menu with modal
            });
        });
        layeredPane.add(pauseButton, Integer.valueOf(2)); // add pause button on top of everything

        // display everything in a JDialog
        parentDialog = new JDialog();
        parentDialog.setTitle("Round 1: Burn's Tower");
        parentDialog.setModal(true);
        parentDialog.setContentPane(layeredPane);
        parentDialog.pack();
        parentDialog.setLocationRelativeTo(null); // center the dialog on the screen
        parentDialog.setVisible(true);
    }

    @Override
    public void hideContent() {
        if (hornetLabel != null) hornetLabel.setVisible(false);
        if (backgroundLabel != null) backgroundLabel.setVisible(false);
        if (pauseButton != null) pauseButton.setVisible(false);
    }

    @Override
    public void updateCaption(String caption) {
        JOptionPane.showMessageDialog(parentDialog, caption, "Round 1", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resumeGame() {
        isPaused = false; // set pause state to false
        startHornetMovement(); // resume hornet movement immediately
    }

    private void startHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel(); // stop any existing timer
        }

        hornetMovementTimer = new Timer();
     // sets up a task to move the hornet repeatedly at a regular time interval.
        hornetMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    // Update hornet's position for basic floating movement
                    hornetX += 5; // move right
                    if (hornetX > 1080) hornetX = 540; // reset position if off-screen

                    SwingUtilities.invokeLater(() -> {
                        hornetLabel.setBounds(hornetX, hornetY, 150, 150); // update label position
                        hornetLabel.repaint();
                    });
                }
            }
        }, 0, 50); // move every 50ms
    }

    private void stopHornetMovement() {
        if (hornetMovementTimer != null) {
            hornetMovementTimer.cancel(); // stop the timer immediately
            hornetMovementTimer = null; // release resources
        }
    }
}

