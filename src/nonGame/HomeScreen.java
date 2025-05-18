package nonGame;

import main.Difficulty;
import main.Main;
import main.MainApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JPanel {

    private static Difficulty chosenDifficulty;

    public HomeScreen(MainApp mainApp, JFrame frame) {
        setLayout(null);
        JButton easyGamemode = new JButton("Easy");
        easyGamemode.setBounds(20,50,50, 20);
        easyGamemode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenDifficulty = Difficulty.EASY;
                changeFrameSize(frame);
                mainApp.startGame();
            }
        });
        add(easyGamemode);

        JButton mediumGamemode = new JButton("Medium");
        mediumGamemode.setBounds(20,20,50, 20);
        mediumGamemode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenDifficulty = Difficulty.MEDIUM;
                changeFrameSize(frame);
                mainApp.startGame();
            }
        });
        add(mediumGamemode);
    }
    public static Difficulty getChosenDifficulty() { return chosenDifficulty; }
    private void changeFrameSize(JFrame frame) {
        MainApp.FRAME_WIDTH = chosenDifficulty.getFrameWidth();
        MainApp.FRAME_HEIGHT = chosenDifficulty.getFrameHeight();
        frame.setSize(MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}
