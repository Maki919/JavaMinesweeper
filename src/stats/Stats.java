package stats;

import nonGame.HomeScreen;

public class Stats {
    private final StatsData data;

    public Stats() {
        this.data = StatsManager.loadStats();
    }

    public void handleGameOverStats(int seconds, int bombsFlagged){
        data.secondsPlayed += seconds;
        data.bombsFlagged += bombsFlagged;
        data.gamesPlayed++;
        data.gamesLost = data.gamesPlayed - data.gamesWon;
        switch (HomeScreen.getChosenDifficulty()){
            case EASY:
                data.easyGamesPlayed++;
                break;
            case MEDIUM:
                data.mediumGamesPlayed++;
                break;
        }
        StatsManager.saveStats(data);
    }
    public void handleGameWonStats(int seconds) {
        data.gamesWon++;
        switch (HomeScreen.getChosenDifficulty()){
            case EASY:
                if (data.fastestEasyGamesPlayed == 0 || data.fastestEasyGamesPlayed >= seconds)
                    data.fastestEasyGamesPlayed = seconds;
                break;
            case MEDIUM:
                if (data.fastestMediumGamesPlayed == 0 || data.fastestMediumGamesPlayed >= seconds)
                    data.fastestMediumGamesPlayed = seconds;
                break;
        }
        StatsManager.saveStats(data);
    }
}

