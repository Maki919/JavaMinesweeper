package main;

import nonGame.HomeScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Field.*;
import static nonGame.HomeScreen.getChosenDifficulty;

public class GameBoard extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(GameBoard.class.getName());

    public static final int fieldWH = 50;

    private final int bombMaxAmount = getChosenDifficulty().getBombsMaxAmount();
    private final int fieldMaxIndex = getChosenDifficulty().getFieldMaxIndex();
    public final int boardOffsetX = (getChosenDifficulty().getFrameWidth() - ((fieldMaxIndex + 1) * fieldWH)) / 2; //
    public final int boardOffsetY = 100;
    private int flagsPlaced = 0;
    private int bombsFlagged = 0;

    private final GameEnvironment gameEnvironment = new GameEnvironment(this);

    //Richtungen der angrenzenden Spielfelder
    private final int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    private final Field[][] fields = new Field[fieldMaxIndex+1][fieldMaxIndex+1];
    private final Random rand = new Random();


    //Map number to image
    private final Map<Integer, BufferedImage> numberImages = new HashMap<>();

    public GameBoard() {
        new GameEnvironment(this);
        if (HomeScreen.getChosenDifficulty() == Difficulty.EASY) {
            setBackground(new Color(86, 64, 26));
        } else {
            setBackground(new Color(62, 82, 51));
        }
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                fields[row][col] = new Field(0, STATUS_HIDDEN);
            }
        }
        try{
            //Flag
            numberImages.put(-3, ImageIO.read(new File("image/set1/-3.png")));
            //Button
            numberImages.put(-2, ImageIO.read(new File("image/set1/-2.png")));
            //Bomb
            numberImages.put(-1, ImageIO.read(new File("image/set1/-1.png")));
            //Numbers
            numberImages.put( 0, ImageIO.read(new File("image/set1/0.png")));
            numberImages.put( 1, ImageIO.read(new File("image/set1/1.png")));
            numberImages.put( 2, ImageIO.read(new File("image/set1/2.png")));
            numberImages.put( 3, ImageIO.read(new File("image/set1/3.png")));
            numberImages.put( 4, ImageIO.read(new File("image/set1/4.png")));
            numberImages.put( 5, ImageIO.read(new File("image/set1/5.png")));
            numberImages.put( 6, ImageIO.read(new File("image/set1/6.png")));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Laden von Bildern", e);
        }
        generateFieldTypes();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int row = (e.getY() - boardOffsetY) / fieldWH;
                int col = (e.getX() - boardOffsetX) / fieldWH;

                if(SwingUtilities.isLeftMouseButton(e)) {
                    if(fields[row][col].getStatus() == STATUS_FLAGGED)
                        flagsPlaced--;

                    switch (fields[row][col].getType()){
                        case 0:             //open 0-Field
                            revealType0(row, col);
                            break;
                        case TYPE_BOMB:     //Game Lost
                            gameEnvironment.setGameStateLost(GameBoard.this);
                            revealWholeBoard();
                            break;
                        default:            //open non-0/non-bomb-Fields
                            fields[row][col].setStatus(STATUS_REVEALED);
                            break;

                    }
                    repaint();
                }
                if(SwingUtilities.isRightMouseButton(e)) {
                    if(fields[row][col].getStatus() == STATUS_HIDDEN) {
                        //flag
                        if (flagsPlaced < bombMaxAmount) {
                            fields[row][col].setStatus(STATUS_FLAGGED);
                            flagsPlaced++;
                            if (fields[row][col].getType() == TYPE_BOMB) {
                                bombsFlagged++;
                                //Game Won
                                if (bombsFlagged == bombMaxAmount){
                                    gameEnvironment.setGameStateWon(GameBoard.this);
                                    revealWholeBoard();
                                }
                            }
                        }
                    } else if(fields[row][col].getStatus() == STATUS_FLAGGED){
                        //un flag
                        fields[row][col].setStatus(STATUS_HIDDEN);
                        flagsPlaced--;
                        if (fields[row][col].getType() == TYPE_BOMB)
                            bombsFlagged--;
                    }
                    repaint();
                }
            }
        });
    }

    private void generateFieldTypes(){
        int bombCounter = 0;
        while (bombCounter < bombMaxAmount) {
            int randRow = rand.nextInt(fieldMaxIndex+1);
            int randCol = rand.nextInt(fieldMaxIndex+1);
            if (fields[randRow][randCol].getType() == 0) {
                fields[randRow][randCol].setType(TYPE_BOMB);
                bombCounter++;
            }
        }
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                if (fields[row][col].getType() == TYPE_BOMB) {
                    continue;
                }
                int bombsInArea = 0;
                for (int[] direction : directions) {
                    int y = row + direction[0];
                    int x = col + direction[1];
                    if (x >= 0 && y >= 0 && x <= fieldMaxIndex && y <= fieldMaxIndex) {
                        if (fields[y][x].getType() == TYPE_BOMB) {
                            bombsInArea++;
                        }
                    }
                }
                fields[row][col].setType(bombsInArea);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {

                if (fields[row][col].getStatus() == STATUS_REVEALED) {
                    paintFieldTexture(row, col, fields[row][col].getType(), g);

                } else if(fields[row][col].getStatus() == STATUS_HIDDEN) {
                    paintFieldTexture(row, col, -2, g);

                } else {
                    paintFieldTexture(row, col, TYPE_FLAG, g);

                }
            }
        }
        gameEnvironment.paint(g, this);
    }

    private void paintFieldTexture(int row, int col, int type, Graphics g) {
        g.drawImage(numberImages.get(type), boardOffsetX + col * fieldWH, boardOffsetY + row * fieldWH, fieldWH, fieldWH, null);
    }

    private void revealWholeBoard(){
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                fields[row][col].setStatus(STATUS_REVEALED);
            }
        }
        repaint();
    }

    private void revealType0(int row, int col) {
        if (fields[row][col].getStatus() == STATUS_REVEALED) return;

        fields[row][col].setStatus(STATUS_REVEALED);

        for(int dir = 0; dir < 8; dir++) {
            int x = row + directions[dir][1];
            int y = col + directions[dir][0];

            if (x >= 0 && y >= 0 && x <= fieldMaxIndex && y <= fieldMaxIndex) {
                if(fields[x][y].getType() == 0 && fields[x][y].getStatus() == STATUS_HIDDEN) {
                    revealType0(x, y);
                } else if(fields[x][y].getType() > 0) {
                    fields[x][y].setStatus(STATUS_REVEALED);
                }
            }
        }
    }

    public int getFlagsPlaced() { return this.flagsPlaced; }
    public int getBombsFlagged() { return this.bombsFlagged; }
    public int getBombMaxAmount(){ return this.bombMaxAmount; }
}

