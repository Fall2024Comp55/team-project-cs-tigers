import javax.swing.JOptionPane;

public class Round1 extends Round {
    // start round 1 with specific contents
    @Override
    public void startRound() {
        updateCaption("Round 1: Battle with Hornet!");
        showContent();
        System.out.println("setting up tiger vs hornet"); // placeholder for actual display setup
    }

    // advances to the next round (could trigger Round2)
    @Override
    protected void nextRound() {
        endRound();
        JOptionPane.showMessageDialog(null, "Round 1 complete! Moving to Round 2.", "Round Transition", JOptionPane.INFORMATION_MESSAGE);
        // here you might trigger Round2 start
    }

    // shows tiger and hornet on screen
    @Override
    public void showContent() {
        super.showContent();
        System.out.println("displaying tiger and hornet for round 1");
        // actual code to display tiger and hornet
    }

    // hides tiger and hornet (useful for pause menu)
    @Override
    public void hideContent() {
        super.hideContent();
        System.out.println("hiding tiger and hornet for round 1");
        // actual code to hide tiger and hornet
    }
}

