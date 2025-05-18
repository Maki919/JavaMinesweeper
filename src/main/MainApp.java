package main;

import nonGame.HomeScreen;
import nonGame.Stats;

import javax.swing.*;
import java.awt.*;


public class MainApp {
    public static int FRAME_WIDTH = HomeScreen.getChosenDifficulty() == null ? 200 : (HomeScreen.getChosenDifficulty().getFrameWidth());
    public static int FRAME_HEIGHT = HomeScreen.getChosenDifficulty() == null ? 200 : (HomeScreen.getChosenDifficulty().getFrameHeight());
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

        HomeScreen homeScreen = new HomeScreen(this, frame);
        Stats stats = new Stats();

        mainPanel.add(homeScreen, "homeScreen");
        mainPanel.add(stats, "stats");

        frame.add(mainPanel);

        showPanel("homeScreen");

        frame.setVisible(true);
    }
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    public void startGame() {
        GameBoard gameBoard = new GameBoard();
        mainPanel.add(gameBoard, "gameBoard");
        showPanel("gameBoard");
    }

}
