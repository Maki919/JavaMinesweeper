package main;

import javax.swing.*;

public class MainApp {
    public static final int FRAME_WIDTH = 530;
    public static final int FRAME_HEIGHT = 610;
    JFrame frame;

    public MainApp() {
        frame = new JFrame();
        frame.setSize(530, 610);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new GameBoard());
        frame.setVisible(true);
    }
}
