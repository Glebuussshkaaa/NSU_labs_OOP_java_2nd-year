package com.glebestraikh.jpacman.model;

import com.glebestraikh.jpacman.util.PacmanConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapData {
    private static final int SQUARE_SIZE = 32;
    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;
    private int boardWidth;
    private int boardHeight;

    public MapData(GameSprites gameSprites) {
        loadMap(gameSprites);
    }

    private void loadMap(GameSprites gameSprites) {
        List<String> tileMap = new ArrayList<>();
        Set<Block> walls = new HashSet<>();
        Set<Block> foods = new HashSet<>();
        Set<Block> ghosts = new HashSet<>();
        Block pacman = null;

        InputStream inputStream = MapData.class.getResourceAsStream("/board.txt");
        if (inputStream == null) {
            throw new PacmanConfigurationException("Could not find board.txt resource.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tileMap.add(line);
            }
        } catch (IOException e) {
            throw new PacmanConfigurationException("Error reading board.txt", e);
        }

        if (tileMap.isEmpty()) {
            throw new PacmanConfigurationException("board.txt is empty or could not be read properly.");
        }

        int rowCount = tileMap.size();
        int columnCount = tileMap.getFirst().length();
        int boardWidth = columnCount * SQUARE_SIZE;
        int boardHeight = rowCount * SQUARE_SIZE;

        for (int r = 0; r < rowCount; r++) {
            String row = tileMap.get(r);
            if (row.length() != columnCount) {
                throw new PacmanConfigurationException(
                        "Inconsistent row length at row " + r + ". Expected: " + columnCount + ", Found: " + row.length()
                );
            }
            for (int c = 0; c < columnCount; c++) {
                char tileMapChar = row.charAt(c);
                int x = c * SQUARE_SIZE;
                int y = r * SQUARE_SIZE;

                switch (tileMapChar) {
                    case 'X' -> walls.add(new Block(gameSprites.getWallImage(), x, y, SQUARE_SIZE, tileMapChar));
                    case 'b' -> ghosts.add(new Block(gameSprites.getGhostImage('b', 'R'), x, y, SQUARE_SIZE, tileMapChar));
                    case 'o' -> ghosts.add(new Block(gameSprites.getGhostImage('o', 'R'), x, y, SQUARE_SIZE, tileMapChar));
                    case 'p' -> ghosts.add(new Block(gameSprites.getGhostImage('p', 'R'), x, y, SQUARE_SIZE, tileMapChar));
                    case 'r' -> ghosts.add(new Block(gameSprites.getGhostImage('r', 'R'), x, y, SQUARE_SIZE, tileMapChar));
                    case 'P' -> pacman = new Block(gameSprites.getPacmanImage('R', true), x, y, SQUARE_SIZE, tileMapChar);
                    case ' ' -> foods.add(new Block(gameSprites.getPelletImage(), x, y, SQUARE_SIZE, tileMapChar));
                    case 'O' -> {}
                    default -> throw new PacmanConfigurationException("Unknown tile character '" + tileMapChar + "' at (" + r + "," + c + ")");
                }
            }
        }

        if (pacman == null) {
            throw new PacmanConfigurationException("Pacman starting position (P) is missing in board.txt.");
        }

        this.walls = walls;
        this.foods = foods;
        this.ghosts = ghosts;
        this.pacman = pacman;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public Set<Block> getWalls() {
        return walls;
    }

    public Set<Block> getFoods() {
        return foods;
    }

    public Set<Block> getGhosts() {
        return ghosts;
    }

    public Block getPacman() {
        return pacman;
    }

    public int getSquareSize() {
        return SQUARE_SIZE;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}