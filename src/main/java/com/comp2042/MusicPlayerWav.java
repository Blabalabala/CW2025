package com.comp2042;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * A simple WAV music player that can play and loop background music.
 * <p>
 * Supports loading a WAV file from the classpath and looping it indefinitely.
 * </p>
 */
public class MusicPlayerWav {

    private Clip clip;

    /**
     * Plays a WAV file from the specified resource path and loops it continuously.
     *
     * @param resourcePath the path to the WAV file within the classpath
     */
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

    /**
     * Stops the currently playing music if it is running.
     */
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
