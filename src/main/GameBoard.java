package main;

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

import static main.Field.*;

public class GameBoard extends JPanel {

    public static final int fieldWH = 50;
    public static final int boardOffsetX = 40;
    public static final int boardOffsetY = 100;
    private final int bombMaxAmount = 10;
    private final int fieldMaxIndex = 8;
    private int flagsPlaced = 0;
    private int bombsFlagged = 0;

    private final GameEnvironment gameEnvironment = new GameEnvironment(this);
    private Timer gameLostTimer;

    //Richtungen der angrenzenden Spielfelder
    private final int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    private final Field[][] bombs = new Field[9][9];
    private final Random rand = new Random();


    //Map number to image
    private final Map<Integer, BufferedImage> numberImages = new HashMap<>();

    public GameBoard() {
        new GameEnvironment(this);
        setBackground(new Color(86, 64, 26));
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                bombs[row][col] = new Field(0, STATUS_HIDDEN);
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
            e.getStackTrace();
        }
        generateFieldTypes();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int row = (e.getY() - boardOffsetY) / fieldWH;
                int col = (e.getX() - boardOffsetX) / fieldWH;

                if(SwingUtilities.isLeftMouseButton(e)) {
                    if(bombs[row][col].getStatus() == STATUS_FLAGGED)
                        flagsPlaced--;

                    if(bombs[row][col].getType() == 0) {
                        //open 0-Field
                        revealType0(row, col);
                    } else if(bombs[row][col].getType() == TYPE_BOMB) {
                        //GameOver
                        GameEnvironment.gameLost = 1;
                        resultMessageAnimation();
                        revealWholeBoard();
                    } else {
                        //open non-0/non-bomb-Fields
                        bombs[row][col].setStatus(STATUS_REVEALED);
                    }
                    repaint();
                }
                if(SwingUtilities.isRightMouseButton(e)) {
                    if(bombs[row][col].getStatus() == STATUS_HIDDEN) {
                        //flag
                        if (flagsPlaced < bombMaxAmount) {
                            bombs[row][col].setStatus(STATUS_FLAGGED);
                            flagsPlaced++;
                            if (bombs[row][col].getType() == TYPE_BOMB) {
                                bombsFlagged++;

                                if (bombsFlagged == bombMaxAmount){
                                    GameEnvironment.gameLost = -1;
                                    resultMessageAnimation();
                                    revealWholeBoard();
                                }
                            }
                        }
                    } else if(bombs[row][col].getStatus() == STATUS_FLAGGED){
                        //un flag
                        bombs[row][col].setStatus(STATUS_HIDDEN);
                        flagsPlaced--;
                        if (bombs[row][col].getType() == TYPE_BOMB)
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
            int randRow = rand.nextInt(9);
            int randCol = rand.nextInt(9);
            if (bombs[randRow][randCol].getType() == 0) {
                bombs[randRow][randCol].setType(TYPE_BOMB);
                bombCounter++;
            }
        }
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                if (bombs[row][col].getType() == TYPE_BOMB) {
                    continue;
                }
                int bombsInArea = 0;
                for (int[] direction : directions) {
                    int x = row + direction[0];
                    int y = col + direction[1];
                    if (x >= 0 && y >= 0 && x <= fieldMaxIndex && y <= fieldMaxIndex) {
                        if (bombs[x][y].getType() == TYPE_BOMB) {
                            bombsInArea++;
                        }
                    }
                }
                bombs[row][col].setType(bombsInArea);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {

                if (bombs[row][col].getStatus() == 1) {
                    paintFieldTexture(row, col, bombs[row][col].getType(), g);

                } else if(bombs[row][col].getStatus() == 0) {
                    paintFieldTexture(row, col, -2, g);

                } else {
                    paintFieldTexture(row, col, -3, g);

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
                bombs[row][col].setStatus(1);
                repaint();
            }
        }
    }

    private void revealType0(int row, int col) {
        if (bombs[row][col].getStatus() == 1) return;

        bombs[row][col].setStatus(1);

        for(int dir = 0; dir < 8; dir++) {
            int x = row + directions[dir][1];
            int y = col + directions[dir][0];

            if (x >= 0 && y >= 0 && x <= fieldMaxIndex && y <= fieldMaxIndex) {
                if(bombs[x][y].getType() == 0 && bombs[x][y].getStatus() == 0) {
                    revealType0(x, y);
                } else if(bombs[x][y].getType() > 0) {
                    bombs[x][y].setStatus(1);
                }
            }
        }
    }
    private void resultMessageAnimation(){
        gameLostTimer = new Timer(10, e1 -> {
            if (GameEnvironment.loseMessageX < 0) {
                GameEnvironment.loseMessageX += 1;
                repaint();
            } else {
                gameLostTimer.stop();
            }
        });
        gameLostTimer.start();
    }

    public int getFlagsPlaced() { return this.flagsPlaced; }
    public int getBombMaxAmount(){ return this.bombMaxAmount; }
    public int getFieldMaxIndex(){ return this.fieldMaxIndex; }
}

