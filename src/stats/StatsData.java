package stats;

public class StatsData {
    public int secondsPlayed = 0;
    public int bombsFlagged = 0;
    //public int fieldsRevealed = 0;
    public int gamesPlayed = 0;
    public int gamesLost = 0;
    public int gamesWon = 0;
    public int easyGamesPlayed = 0;
    public int mediumGamesPlayed = 0;
    public int fastestEasyGamesPlayed = 0;
    public int fastestMediumGamesPlayed = 0;

    public static String secondsToHours(int second) {
        int secondsMod = second % 60;
        int minutes = (second % 3600) / 60;
        int hours = second / 3600;

        return prevZeros(hours) + ":" + prevZeros(minutes) + ":" + prevZeros(secondsMod);
    }
    public static String secondsToMinutes (int second) {
        int secondsMod = second % 60;
        int minutes = second / 60;

        return prevZeros(minutes) + ":" + prevZeros(secondsMod);
    }
    private static String prevZeros(int number){
        return String.valueOf(number).length() == 1 ? "0" + number: String.valueOf(number);
    }
}
