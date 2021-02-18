package view;

import view.GameGUI;

import javax.swing.*;
import java.awt.*;

public class GameModeGUI extends JFrame {

    GameGUI gameGUI;

    public GameModeGUI() {
        setTitle("Game Mode");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        createComponents();
        setVisible(true);
    }

    private void createComponents() {
        JLabel chooseModeLabel = new JLabel("Choose a game mode");
        JButton playAgainstFriendBtn = new JButton(" Local game against a friend ");
        chooseModeLabel.setAlignmentX(CENTER_ALIGNMENT);
        playAgainstFriendBtn.setAlignmentX(CENTER_ALIGNMENT);

        playAgainstFriendBtn.addActionListener(e -> {
            dispose();
            gameGUI = new GameGUI("Tic Tac Toe");
        });
        add(chooseModeLabel);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(playAgainstFriendBtn);

    }
}
