package stats;

public class StatsData {
    public int secondsPlayed = 0;
    public int bombsFlagged = 0;
    public int fieldsRevealed = 0;
    public int gamesPlayed = 0;
    public int gamesLost = 0;
    public int gamesWon = 0;
    public int easyGamesPlayed = 0;
    public int mediumGamesPlayed = 0;

    public static String secodsToMinutes(int second) {
        return (int)(second / 60) + ":" + (int)(second % 60);
    }
}
