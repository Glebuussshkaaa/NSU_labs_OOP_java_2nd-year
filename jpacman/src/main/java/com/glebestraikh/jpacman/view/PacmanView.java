package com.glebestraikh.jpacman.view;

import com.glebestraikh.jpacman.controller.PacmanController;
import com.glebestraikh.jpacman.model.Block;
import com.glebestraikh.jpacman.model.MapData;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Set;
import javax.swing.JPanel;

public class PacmanView extends JPanel {
    private final MapData gameMap;
    private PacmanController controller;

    public PacmanView(MapData gameMap) {
        this.gameMap = gameMap;

        setPreferredSize(new Dimension(gameMap.getBoardWidth(), gameMap.getBoardHeight()));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void setController(PacmanController controller) {
        this.controller = controller;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (controller == null) {
            return;
        }

        Set<Block> walls = controller.getWalls();
        Set<Block> foods = controller.getFoods();
        Set<Block> ghosts = controller.getGhosts();
        Block pacman = controller.getPacman();

        for (Block wall : walls) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(),
                    wall.getSquareSize(), wall.getSquareSize(), null);
        }

        for (Block food : foods) {
            g.drawImage(food.getImage(), food.getX(), food.getY(),
                    food.getSquareSize(), food.getSquareSize(), null);
        }

        for (Block ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(),
                    ghost.getSquareSize(), ghost.getSquareSize(), null);
        }

        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(),
                pacman.getSquareSize(), pacman.getSquareSize(), null);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);

        int leftTextX = gameMap.getSquareSize() / 4;
        int textY = gameMap.getSquareSize() / 2;

        int rightTextX = gameMap.getBoardWidth() - 160;

        if (controller.isGameOver()) {
            String gameOverText = "Game Over: " + controller.getScore();
            int centerX = (gameMap.getBoardWidth() - g.getFontMetrics().stringWidth(gameOverText)) / 2;
            g.drawString(gameOverText, centerX, textY);

            g.drawString("High Score: " + controller.getHighScore(), rightTextX, textY);

            if (controller.isNewHighScore()) {
                textY += 25;
                g.setColor(Color.YELLOW);
                String newHighScoreText = "NEW HIGH SCORE!";
                int centerHighScoreX = (gameMap.getBoardWidth() - g.getFontMetrics().stringWidth(newHighScoreText)) / 2;
                g.drawString(newHighScoreText, centerHighScoreX, textY);
            }
        } else {
            g.drawString("Lives: x" + controller.getLives(), leftTextX, textY);
            leftTextX += 75;
            g.drawString(" Score: " + controller.getScore(), leftTextX, textY);

            g.drawString("High Score: " + controller.getHighScore(), rightTextX, gameMap.getSquareSize() / 2);
        }
    }
}