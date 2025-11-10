package com.comp2042;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MusicPlayerWav {
    private Clip clip;

    //Play background music in loop
    public void playMusic(String resourcePath) {
        try {
            URL soundUrl = getClass().getResource(resourcePath);
            if (soundUrl == null) {
                System.err.println("Sound file not found: " + resourcePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop forever
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /** Stop the music */
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
