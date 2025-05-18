package stats;

import main.MainApp;
import nonGame.HomeScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class StatScreen extends JPanel {
    private StatsData statsData;

    private BufferedImage statBackgroundImage;

    public StatScreen() {
        statsData = StatsManager.loadStats();
        try{
            statBackgroundImage = ImageIO.read(new File("image/statScreenBackground.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(statBackgroundImage, 0, 0, getWidth(), getHeight(), null);

        int statTextX = MainApp.FRAME_WIDTH/2 - 150;
        int statDataX = MainApp.FRAME_HEIGHT/2 + 100;
        int statY = MainApp.FRAME_HEIGHT/2 - 75;
        g.setColor(new Color(0x195F4E));
        g.setFont(new Font("Dialog", Font.BOLD, 15));

        g.drawString("Time played: ", statTextX, statY);
        g.drawString(StatsData.secodsToMinutes(statsData.secondsPlayed), statDataX, statY);

        statY += 40;
        g.drawString("Games played: ", statTextX, statY);
        g.drawString(""+ statsData.gamesPlayed, statDataX, statY);

        statY += 20;
        g.drawString("Easy games played: ", statTextX, statY);
        g.drawString(""+ statsData.easyGamesPlayed, statDataX, statY);

        statY += 20;
        g.drawString("Medium games played: ", statTextX, statY);
        g.drawString(""+ statsData.mediumGamesPlayed, statDataX, statY);

        statY += 40;
        g.drawString("Games won: ", statTextX, statY);
        g.drawString(""+ statsData.gamesWon, statDataX, statY);

        statY += 20;
        g.drawString("Games lost: ", statTextX, statY);
        g.drawString(""+ statsData.gamesLost, statDataX, statY);

        statY += 40;
        g.drawString("Bombs flagged: ", statTextX, statY);
        g.drawString(""+ statsData.bombsFlagged, statDataX, statY);
    }
}
