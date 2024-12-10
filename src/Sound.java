import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip backgroundClip;
    private static boolean isMuted = false; // Mute state tracker

    // Play a sound effect once
    public static void playSound(String filePath) {
        if (isMuted) return; // Don't play if muted
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    // Play background music (looped)
    public static void playBackgroundMusic(String filePath) {
        if (isMuted || (backgroundClip != null && backgroundClip.isRunning())) return; 
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }

    // Stop the background music
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    // Toggle mute/unmute
    public static void toggleMute() {
        isMuted = !isMuted;

        if (isMuted) {
            stopBackgroundMusic();  // Stop the background music when muted
            System.out.println("Sound muted.");
        } else {
            System.out.println("Sound unmuted.");
            if (backgroundClip != null && !backgroundClip.isRunning()) {
                backgroundClip.start(); // Resume the clip if it was paused
            }
        }
    }

    // Set volume
    public static void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // Volume range: -80.0 to 6.0
        }
    }

    // Check if a clip is playing
    public static boolean isPlaying(Clip clip) {
        return clip != null && clip.isRunning();
    }
}

