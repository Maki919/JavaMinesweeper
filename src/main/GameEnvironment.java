package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameEnvironment {

    private static final Logger LOGGER = Logger.getLogger(GameEnvironment.class.getName());

    public enum gameState {
        ONGOING,
        LOST,
        WON
    }
    private gameState currentGameState = gameState.ONGOING;
    Timer gameLostTimer;
    private int resultMessageX = -180;
    private int secondsPlayed = 0;
    private int prevSecondsPlayedDigitLength = 1;
    private int secondsPlayedXOffset = 90;

    private BufferedImage gameEnvironment;
    private BufferedImage wonMessage;
    private BufferedImage lostMessage;

    public GameEnvironment(JPanel panel) {
        Timer time = new Timer(1000, e1 -> {
            if (currentGameState == gameState.ONGOING) {
                secondsPlayed++;

                int curSecondsPlayedDigitLength = String.valueOf(secondsPlayed).length();
                if (curSecondsPlayedDigitLength > prevSecondsPlayedDigitLength) {
                    secondsPlayedXOffset -= 5;
                    prevSecondsPlayedDigitLength = curSecondsPlayedDigitLength;
                }
                panel.repaint();
            }
        });
        time.start();

        try{
            gameEnvironment = ImageIO.read(new File("image/boardEnvironment.png"));
            wonMessage = ImageIO.read(new File("image/wonMessage.png"));
            lostMessage = ImageIO.read(new File("image/lostMessage.png"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Laden von Bildern", e);
        }

    }

    public void paint(Graphics g, GameBoard gameBoard) {


        g.drawImage(gameEnvironment, 0,0, MainApp.FRAME_WIDTH, MainApp.FRAME_HEIGHT, null);

        g.setColor(new Color(147, 123, 46));
        g.setFont(new Font("Adelle Sans Devanagari", Font.BOLD, 20));

        int remainingFlagsScore = gameBoard.getBombMaxAmount() - gameBoard.getFlagsPlaced();
        int remainingFlagsScoreXOffset = 104;
        if (remainingFlagsScore < 10) {
            remainingFlagsScoreXOffset -= 5;
        }
        g.drawString(""+ remainingFlagsScore, MainApp.FRAME_WIDTH/2 - remainingFlagsScoreXOffset, 41 );
        g.drawString(""+ secondsPlayed, MainApp.FRAME_WIDTH/2 + secondsPlayedXOffset, 41 );

        switch (currentGameState) {
            case LOST:
                g.drawImage(lostMessage, resultMessageX, 30, 150, 112, null);
                break;
            case WON:
                g.drawImage(wonMessage, resultMessageX, 30, 150, 112, null);
                break;
        }
    }
    public void resultMessageAnimation(JPanel panel) {
        gameLostTimer = new Timer(10, e1 -> {
            if (resultMessageX < 0) {
                resultMessageX += 1;
                panel.repaint();
            } else {
                gameLostTimer.stop();
            }
        });
        gameLostTimer.start();
    }

    public void setGameStateLost(GameBoard gameBoard) {
        currentGameState = gameState.LOST;
        resultMessageAnimation(gameBoard);
    }
    public void setGameStateWon(GameBoard gameBoard) {
        currentGameState = gameState.WON;
        resultMessageAnimation(gameBoard);
    }

}


