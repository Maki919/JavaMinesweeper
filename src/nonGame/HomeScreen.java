package nonGame;

import main.Difficulty;
import main.MainApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeScreen extends JPanel {

    private static Difficulty chosenDifficulty;
    private BufferedImage homeScreenBackground;

    public HomeScreen(MainApp mainApp, JFrame frame) {
        try {
            homeScreenBackground = ImageIO.read(new File("image/homeScreenBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        JButton easyGamemode = new JButton("Easy");
        easyGamemode.setBounds(MainApp.FRAME_WIDTH/2 - 100 , MainApp.FRAME_HEIGHT/2 -62, 200, 52);
        handleButtonGameAction(easyGamemode, frame, mainApp, Difficulty.EASY);
        handleButtonStyle(easyGamemode);

        JButton mediumGamemode = new JButton("Medium");
        mediumGamemode.setBounds(MainApp.FRAME_WIDTH/2 -100 , MainApp.FRAME_HEIGHT/2 + 12 , 200, 52);
        handleButtonGameAction(mediumGamemode, frame, mainApp, Difficulty.MEDIUM);
        handleButtonStyle(mediumGamemode);

        JButton statButton = new JButton("Stats");
        statButton.setBounds(MainApp.FRAME_WIDTH/2 -100 , MainApp.FRAME_HEIGHT/2 + 86 , 200, 52);
        statButton.addActionListener(e -> mainApp.showPanel("statScreen"));
        handleButtonStyle(statButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (homeScreenBackground != null)
            g.drawImage(homeScreenBackground, 0, 0, MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT, null);
    }

    public static Difficulty getChosenDifficulty() {
        return chosenDifficulty;
    }

    private void changeFrameSize(JFrame frame) {
        MainApp.FRAME_WIDTH = chosenDifficulty.getFrameWidth();
        MainApp.FRAME_HEIGHT = chosenDifficulty.getFrameHeight();
        frame.setSize(MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
    }
    private void handleButtonStyle(JButton button) {
        button.setFont(new Font("Adelle Sans Devanagari", Font.BOLD, 20));
        button.setForeground(new Color(141, 98, 23));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        add(button);
    }
    private void handleButtonGameAction(JButton button, JFrame frame, MainApp mainApp, Difficulty difficulty) {
        button.addActionListener(e -> {
            chosenDifficulty = difficulty;
            mainApp.startGame();
            changeFrameSize(frame);
        });
    }
}
