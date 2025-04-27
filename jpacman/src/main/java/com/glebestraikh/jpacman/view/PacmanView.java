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
        if (controller.isGameOver()) {
            g.drawString("Game Over: " + controller.getScore(), gameMap.getSquareSize() / 2, gameMap.getSquareSize() / 2);
        } else {
            g.drawString("x" + controller.getLives() + " Score: " + controller.getScore(), gameMap.getSquareSize() / 2, gameMap.getSquareSize() / 2);
        }
    }
}