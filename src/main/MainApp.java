package main;

import javax.swing.*;

public class MainApp {
    JFrame frame;
    public MainApp() {
        frame = new JFrame();
        frame.setSize(470, 560);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new GameBoard());
        frame.setVisible(true);
    }
}
