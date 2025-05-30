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
    MainApp mainApp;
    public HomeScreen(MainApp mainApp, JFrame frame) {
        this.mainApp = mainApp;
        try {
            homeScreenBackground = ImageIO.read(new File("image/homeScreenBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        JButton easyGamemode = new JButton("Easy");
        easyGamemode.setBounds(mainApp.FRAME_WIDTH/2 - 100 , mainApp.FRAME_HEIGHT/2 -62, 200, 52);
        handleButtonGameAction(easyGamemode, frame, mainApp, Difficulty.EASY);
        handleButtonStyle(easyGamemode);
        add(easyGamemode);

        JButton mediumGamemode = new JButton("Medium");
        mediumGamemode.setBounds(mainApp.FRAME_WIDTH/2 -100 , mainApp.FRAME_HEIGHT/2 + 12 , 200, 52);
        handleButtonGameAction(mediumGamemode, frame, mainApp, Difficulty.MEDIUM);
        handleButtonStyle(mediumGamemode);
        add(mediumGamemode);

        JButton statButton = new JButton("Stats");
        statButton.setBounds(mainApp.FRAME_WIDTH/2 -100 , mainApp.FRAME_HEIGHT/2 + 86 , 200, 52);
        statButton.addActionListener(e -> mainApp.showPanel("statScreen"));
        handleButtonStyle(statButton);
        add(statButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (homeScreenBackground != null)
            g.drawImage(homeScreenBackground, 0, 0, mainApp.FRAME_WIDTH, mainApp.FRAME_HEIGHT, null);
    }

    public static Difficulty getChosenDifficulty() {
        return chosenDifficulty;
    }
    public static void setChosenDifficulty(Difficulty chosenDifficulty) {
        HomeScreen.chosenDifficulty = chosenDifficulty;
    }

    private void changeFrameSize(JFrame frame) {
        mainApp.FRAME_WIDTH = chosenDifficulty.getFrameWidth();
        mainApp.FRAME_HEIGHT = chosenDifficulty.getFrameHeight();
        frame.setSize(mainApp.FRAME_WIDTH, mainApp.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
    }
    public static void handleButtonStyle(JButton button) {
        button.setFont(new Font("Adelle Sans Devanagari", Font.BOLD, 20));
        button.setForeground(new Color(141, 98, 23));
        makeButtonInvisible(button);
    }
    public static void makeButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    private void handleButtonGameAction(JButton button, JFrame frame, MainApp mainApp, Difficulty difficulty) {
        button.addActionListener(e -> {
            chosenDifficulty = difficulty;
            mainApp.startGame();
            changeFrameSize(frame);
        });
    }
}
