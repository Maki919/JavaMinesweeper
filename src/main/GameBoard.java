package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameBoard extends JPanel {
    public static final int fieldWH = 50;
    public static final int boardOffsetX = 10;
    public static final int boardOffsetY = 70;
    public static final int bombMaxAmount = 10;
    public static final int fieldMaxIndex = 8;


    private final Field[][] bombs = new Field[9][9];
    private final int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    private final Random rand = new Random();

    private final Map<Integer, BufferedImage> numberImages = new HashMap<>();

    public GameBoard() {

        for (int row = 0; row <= fieldMaxIndex; row++) {
            for (int col = 0; col <= fieldMaxIndex; col++) {
                bombs[row][col] = new Field(0, 0);
            }
        }
        try{
            numberImages.put(-3, ImageIO.read(new File("image/set1/-3.png")));
            numberImages.put(-2, ImageIO.read(new File("image/set1/-2.png")));
            numberImages.put(-1, ImageIO.read(new File("image/set1/-1.png")));
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
                    bombs[row][col].setStatus(1);
                    repaint();
                }
                if(SwingUtilities.isRightMouseButton(e)) {
                    if(bombs[row][col].getStatus() == 0) {
                        bombs[row][col].setStatus(2);
                    } else if(bombs[row][col].getStatus() == 2){
                        bombs[row][col].setStatus(0);
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
                bombs[randRow][randCol].setType(-1);
                bombCounter++;
            }
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (bombs[row][col].getType() == -1) {
                    continue;
                }
                int bombsInArea = 0;
                for (int[] direction : directions) {
                    int x = row + direction[0];
                    int y = col + direction[1];
                    if (x >= 0 && y >= 0 && x <= fieldMaxIndex && y <= fieldMaxIndex) {
                        if (bombs[x][y].getType() == -1) {
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
        for (int col = 0; col <= fieldMaxIndex; col++) {
            for (int row = 0; row <= fieldMaxIndex; row++) {

                if (bombs[row][col].getStatus() == 1) {
                    g.drawImage(numberImages.get(bombs[row][col].getType()),
                            boardOffsetX + col * fieldWH, boardOffsetY + row * fieldWH, fieldWH, fieldWH, null);
                } else if(bombs[row][col].getStatus() == 0) {
                    g.drawImage(numberImages.get(-2),
                            boardOffsetX + col * fieldWH, boardOffsetY + row * fieldWH, fieldWH, fieldWH, null);
                } else {
                    g.drawImage(numberImages.get(-3),
                            boardOffsetX + col * fieldWH, boardOffsetY + row * fieldWH, fieldWH, fieldWH, null);
                }
            }
        }
    }
}

