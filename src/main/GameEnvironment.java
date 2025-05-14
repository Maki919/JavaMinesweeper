package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameEnvironment {

    public static boolean gameLost;
    public static int loseMessageX = -180;

    private BufferedImage gameEnvironment;
    private BufferedImage wonMessage;
    private BufferedImage lostMessage;

    public GameEnvironment() {
        try{
            gameEnvironment = ImageIO.read(new File("image/boardEnvironment.png"));
            wonMessage = ImageIO.read(new File("image/wonMessage.png"));
            lostMessage = ImageIO.read(new File("image/lostMessage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g) {
        g.drawImage(gameEnvironment, 0,0, MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT, null);

        if (gameLost) {
            g.drawImage(lostMessage, loseMessageX, 30, 150, 112, null);
        }

    }
}


