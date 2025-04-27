
package com.glebestraikh.jpacman;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        int rowCount = 23;
        int columnCount = 21;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("PacMan");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // нельзя изменять разер
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Pacman pacmanGame = new Pacman();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
