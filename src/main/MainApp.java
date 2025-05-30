package main;

import nonGame.HomeScreen;
import stats.StatScreen;

import javax.swing.*;
import java.awt.*;


public class MainApp {
    public int FRAME_WIDTH = HomeScreen.getChosenDifficulty() == null ? 600 : (HomeScreen.getChosenDifficulty().getFrameWidth());
    public int FRAME_HEIGHT = HomeScreen.getChosenDifficulty() == null ? 600 : (HomeScreen.getChosenDifficulty().getFrameHeight());
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private GameBoard gameBoard;

    public MainApp() {

        frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        HomeScreen homeScreen = new HomeScreen(this, frame);
        StatScreen statScreen = new StatScreen(this);

        mainPanel.add(homeScreen, "homeScreen");
        mainPanel.add(statScreen, "statScreen");

        frame.add(mainPanel);

        showPanel("homeScreen");

        frame.setVisible(true);
    }
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void startGame() {
        gameBoard = new GameBoard(this);
        mainPanel.add(gameBoard, "gameBoard");
        showPanel("gameBoard");
    }
    public void backToHomeScreen() {
        HomeScreen.setChosenDifficulty(null);
        FRAME_WIDTH = 600;
        FRAME_HEIGHT = 600;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);

        showPanel("homeScreen");
    }

}
