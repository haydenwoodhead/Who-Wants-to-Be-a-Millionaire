package views;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by hayden on 5/25/17.
 */
public class SoundView {


    /**
     * This isn't technically displaying anything but I think it's more of a view (in that it gives info to the user)
     * than a model.
     */

    public static final String CORRECT_SOUND = "sounds/correct_level_1.wav";
    public static final String INCORRECT_SOUND = "sounds/incorrect.wav";
    public static final String LEAVING_SOUND = "sounds/leaves_game.wav";
    public static final String WIN_SOUND = "sounds/win_millionaire.wav";
    public static final String FIFTY_SOUND = "sounds/50_50.wav";
    public static final String ASK_SOUND = "sounds/ask.wav";
    public static final String SWITCH_SOUND = "sounds/switch.wav";


    /**
     * Play a given sound file
     * @param soundName
     */
    public static void playSound(String soundName) {
        AudioInputStream audioInputStream = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sound.start();
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }
}
