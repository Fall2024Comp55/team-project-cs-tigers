import javax.swing.JOptionPane;

public class Round1 extends Round {

    // start round 1 with specific contents
    @Override
    public void startRound() {
        updateCaption("Round 1: Battle with Hornet at Burn's Tower!");
        setupBackground();
        showContent();
        System.out.println("setting up tiger vs hornet at Burn's Tower"); // placeholder for actual display setup
    }

    // sets up Burn's Tower as the background for round 1
    private void setupBackground() {
        System.out.println("Setting background to 'Burn's Tower' for round 1");
        // code to set Burn's Tower as the background
    }

    // advances to the next round (could trigger Round2)
    @Override
    protected void nextRound() {
        endRound();
        JOptionPane.showMessageDialog(null, "Round 1 complete! Moving to Round 2.", "Round Transition", JOptionPane.INFORMATION_MESSAGE);
        // logic to start Round2 could be triggered here
    }

    // shows tiger and hornet on screen
    @Override
    public void showContent() {
        super.showContent();
        System.out.println("displaying tiger and hornet for round 1!");
        // actual code to display tiger and hornet at Burn's Tower
    }

    // hides tiger and hornet (useful for pause menu)
    @Override
    public void hideContent() {
        super.hideContent();
        System.out.println("hiding tiger and hornet for round 1!");
        // actual code to hide tiger and hornet
    }
}

