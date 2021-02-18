package model;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class PlayMusic {

    String filePath = "music.wav ";

    public void play(String filePath)
    {
        InputStream music;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(null,"Audio file not Found");
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException exception) {
            JOptionPane.showMessageDialog(null,exception);
        }
    }
}
