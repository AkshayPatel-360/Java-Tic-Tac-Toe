package view;

import model.PlayMusic;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class GameGUI extends JFrame {
    PlayMusic playMusic = new PlayMusic();

    JButton buttons[] = new JButton[16];
    JTextArea gameLogText;
    int alternate = 0; //if this number is a even, then put a X. If it's odd, then put an O for player turn
    int xScore = 0;
    int oScore = 0;
    int drawCount = 0;
    int buttonPressedNumber = 0;
    private static final File logFile = new File("GameLog.txt");

    public GameGUI(String title) {
        super(title);
        setLayout(new GridLayout());
        createComponent();
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Close Application
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createComponent() {
        setLayout(new GridLayout(6, 3));
        setBounds(300, 200, 350, 400);
        initializeGameplayButtons();
        initializeFuncButton();
        buttons[9].setText("Player X");
        buttons[10].setText("Player O");
        buttons[11].setText("RESET");
        buttons[12].setText("" + xScore);
        buttons[13].setText("" + oScore);
        buttons[14].setText("EXIT");
        buttons[15].setText("Game Log");

        gameLogText = new JTextArea();
        add(gameLogText);
        buttons[14].setForeground(Color.red);

        buttons[11].addActionListener(e -> resetButtons());
        buttons[14].addActionListener(e -> {
            try {
                PrintWriter pw = new PrintWriter("GameLog.txt");
                write("X Score : 0");
                write("O Score : 0");
                write("Draw : 0");
            } catch (FileNotFoundException exception) {
                System.out.println("File do not exist");
            }
            System.exit(0);
        });
        buttons[15].addActionListener(e -> readFromFile());
        setVisible(true);
    }

    public void initializeFuncButton() {
        for (int i = 9; i <= 15; i++) {
            buttons[i] = new JButton();
            add(buttons[i]);

        }
    }

    public void initializeGameplayButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].setBackground(Color.white);
            buttons[i].setBorder(new LineBorder(Color.black));
            buttons[i].setBorderPainted(true);
            buttons[i].addActionListener(new buttonListener());

            add(buttons[i]);
        }
    }

    private class buttonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked


            if (alternate % 2 == 0) {
                buttonClicked.setText("X");
                buttonClicked.setEnabled(false);

            } else {
                buttonClicked.setText("O");
                buttonClicked.setEnabled(false);
            }

            if (checkForWin()) {
                //Win Message Display
                if (alternate % 2 == 0) {
                    playMusic.play("WIN.wav");
                    JOptionPane.showMessageDialog(null, "X Won");
                    xScore++;
                    buttons[12].setText("" + xScore);
                    writeScore();


                } else {
                    playMusic.play("WIN.wav");
                    JOptionPane.showMessageDialog(null, "O Won");
                    oScore++;
                    buttons[13].setText("" + oScore);
                    writeScore();
                }
                alternate = randomPlayer();
                resetButtons();
            }
            if (buttonPressedNumber == 9) {
                playMusic.play("GameDraw.wav");
                JOptionPane.showMessageDialog(null, "Draw");
                drawCount++;
                writeScore();
                resetButtons();
            }
            alternate++;
        }
    }

    private int randomPlayer() {
        Random r = new Random();
        int low = 1;
        int high = 3;
        int rand = r.nextInt(high - low) + low;
        return rand;
    }

    private void writeScore() {
        try {
            PrintWriter pw = new PrintWriter("GameLog.txt");
            write("X Score : " + xScore);
            write("O Score : " + oScore);
            write("Draw : " + drawCount);

        } catch (FileNotFoundException exception) {
            System.out.println("File do not Exist");
        }
    }

    public boolean checkForWin() {
        buttonPressedNumber++;
        /**   Reference: the button array is arranged like this as the board
         *      0 | 1 | 2
         *      3 | 4 | 5
         *      6 | 7 | 8
         */
        //horizontal win check
        if (checkAdjacent(0, 1) && checkAdjacent(1, 2))
            return true;
        else if (checkAdjacent(3, 4) && checkAdjacent(4, 5))
            return true;
        else if (checkAdjacent(6, 7) && checkAdjacent(7, 8))
            return true;

            //vertical win check
        else if (checkAdjacent(0, 3) && checkAdjacent(3, 6))
            return true;
        else if (checkAdjacent(1, 4) && checkAdjacent(4, 7))
            return true;
        else if (checkAdjacent(2, 5) && checkAdjacent(5, 8))
            return true;

            //diagonal win check
        else if (checkAdjacent(0, 4) && checkAdjacent(4, 8))
            return true;
        else if (checkAdjacent(2, 4) && checkAdjacent(4, 6))
            return true;
        else
            return false;
    }

    public boolean checkAdjacent(int a, int b) {
        if (buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals(""))
            return true;
        else
            return false;
    }

    public void resetButtons() {
        buttonPressedNumber = 0;
        for (int i = 0; i <= 8; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
    }

    private void write(String message) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(logFile, true))) {
            out.println(message);
        } catch (FileNotFoundException exception) {
            System.out.println("File do not Exist");
        }
    }

    private void readFromFile() {
        try (BufferedReader in = new BufferedReader(new FileReader(logFile))) {
            String str;
            gameLogText.setText("");
            while ((str = in.readLine()) != null) {
                gameLogText.append(str + "\n");
            }
        } catch (IOException e) {
            System.out.println("Something wrong with input");
        }
    }
}
