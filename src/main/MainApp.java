package main;

import nonGame.HomeScreen;
import nonGame.Stats;

import javax.swing.*;
import java.awt.*;


public class MainApp {
    public static final int FRAME_WIDTH = (HomeScreen.getChosenDifficulty().getFrameWidth());
    public static final int FRAME_HEIGHT = (HomeScreen.getChosenDifficulty().getFrameHeight());
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainApp() {

        frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        GameBoard gameBoard = new GameBoard();
        Stats stats = new Stats();

        mainPanel.add(gameBoard, "gameBoard");
        mainPanel.add(stats, "stats");

        frame.add(mainPanel);

        showPanel("gameBoard");

        frame.setVisible(true);
    }
    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
