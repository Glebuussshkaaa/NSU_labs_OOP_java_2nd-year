package com.glebestraikh.jpacman;

import com.glebestraikh.jpacman.controller.PacmanController;
import com.glebestraikh.jpacman.model.MapData;
import com.glebestraikh.jpacman.model.GameSprites;
import com.glebestraikh.jpacman.view.PacmanView;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
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

        frame.add(view);
        frame.pack();
        view.requestFocus();
        frame.setVisible(true);
    }
}