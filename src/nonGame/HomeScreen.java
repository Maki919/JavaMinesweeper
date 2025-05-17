package nonGame;

import main.Difficulty;

import javax.swing.*;

public class HomeScreen extends JPanel {
    private static Difficulty chosenDifficulty = Difficulty.MEDIUM;

    public static Difficulty getChosenDifficulty() { return chosenDifficulty; }
}
