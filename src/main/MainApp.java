package main;

import nonGame.Stats;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static final int FRAME_WIDTH = 530;
    public static final int FRAME_HEIGHT = 610;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainApp() {

        frame = new JFrame("JavaMinesweeper");
        frame.setSize(530, 610);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        GameBoard gameBoard = new GameBoard();
        Stats stats = new Stats();

        mainPanel.add(gameBoard, "gameBoard");
        mainPanel.add(stats, "stats");

        showPanel("stats");

        frame.setVisible(true);
    }
    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
