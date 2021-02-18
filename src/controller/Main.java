package controller;

import view.GameModeGUI;
import model.PlayMusic;

public class Main {

    public static void main(String[] args) {
       new GameModeGUI();
       PlayMusic playMusic =new PlayMusic();
       playMusic.play("Background.wav");

    }
}
