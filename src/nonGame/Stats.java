package nonGame;

import javax.swing.*;

public class Stats extends JPanel {
    private static int statSecondsPlayed = 0;
    private static int statBombsFlagged = 0;
    private static int fieldsRevealed = 0;
    private static int statGamesPlayed = 0;
    private static int statGamesLost = 0;
    private static int statGamesWon = statGamesPlayed - statGamesLost;

    public Stats(){}

    public static void incrementGamesLost(){ statGamesLost++; }

    public static void handleGameOver(int seconds, int bombsFlagged){
        statSecondsPlayed += seconds;
        statBombsFlagged += bombsFlagged;
        statGamesPlayed++;
    }

}
