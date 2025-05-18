package stats;

import nonGame.HomeScreen;

public class Stats {
    private final StatsData data;

    public Stats() {
        this.data = StatsManager.loadStats();
    }

    public void handleGameOverStats(int seconds, int bombsFlagged) {
        data.secondsPlayed += seconds;
        data.bombsFlagged += bombsFlagged;
        data.gamesPlayed++;
        switch (HomeScreen.getChosenDifficulty()){
            case EASY:
                data.easyGamesPlayed++;
                break;
            case MEDIUM:
                data.mediumGamesPlayed++;
                break;
        }
        data.gamesWon = data.gamesPlayed - data.gamesLost;
        StatsManager.saveStats(data);
    }

    public void incrementGamesLost() {
        data.gamesLost++;
        StatsManager.saveStats(data);
    }

    public StatsData getStats() {
        return data;
    }
}

