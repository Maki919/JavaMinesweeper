package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameEnvironment {

    public static byte gameLost = 0;
    public static int loseMessageX = -180;
    private final Timer time;
    private byte secondsPlayed = 0;

    private BufferedImage gameEnvironment;
    private BufferedImage wonMessage;
    private BufferedImage lostMessage;

    public GameEnvironment(JPanel panel) {
        time = new Timer(1000, e1->{
            if(gameLost == 0) {
                secondsPlayed++;
                panel.repaint();
            }
        });
        time.start();

        try{
            gameEnvironment = ImageIO.read(new File("image/boardEnvironment.png"));
            wonMessage = ImageIO.read(new File("image/wonMessage.png"));
            lostMessage = ImageIO.read(new File("image/lostMessage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g, GameBoard gameBoard) {
        g.drawImage(gameEnvironment, 0,0, MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT, null);
        g.setColor(Color.WHITE);
        g.drawString(""+ (gameBoard.getBombMaxAmount() - gameBoard.getFlagsPlaced()), 163, 40 );
        g.drawString(""+ secondsPlayed, 340, 40 );

        switch (gameLost) {
            case 1:
                g.drawImage(lostMessage, loseMessageX, 30, 150, 112, null);
                break;
            case -1:
                g.drawImage(wonMessage, loseMessageX, 30, 150, 112, null);
                break;
        }
    }
}


