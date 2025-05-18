package com.glebestraikh.jpacman;

import com.glebestraikh.jpacman.controller.PacmanController;
import com.glebestraikh.jpacman.model.MapData;
import com.glebestraikh.jpacman.model.GameSprites;
import com.glebestraikh.jpacman.view.PacmanView;
import com.glebestraikh.jpacman.util.PacmanConfigurationException;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        try {
            GameSprites gameSprites = new GameSprites();
            MapData mapData = new MapData(gameSprites);

            int boardWidth = mapData.getBoardWidth();
            int boardHeight = mapData.getBoardHeight();

            JFrame frame = new JFrame("PacMan");
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PacmanView view = new PacmanView(mapData);
            PacmanController controller = new PacmanController(mapData, gameSprites, view);
            controller.initializeGame();

            frame.add(view);
            frame.pack();
            view.requestFocus();
            frame.setVisible(true);

        } catch (PacmanConfigurationException e) {
            System.err.println("Critical error during game initialization: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }
}