package com.glebestraikh.jpacman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapData {
    private static final int squareSize = 32;
    private List<String> tileMap;
    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;
    private int rowCount;
    private int columnCount;
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

        try (InputStream inputStream = MapData.class.getResourceAsStream("/board.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                tileMap.add(line);
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load board.txt", e);
        }

        int rowCount = tileMap.size();
        int columnCount = tileMap.get(0).length();
        int boardWidth = columnCount * squareSize;
        int boardHeight = rowCount * squareSize;

        for (int r = 0; r < rowCount; r++) {
            String row = tileMap.get(r);
            for (int c = 0; c < columnCount; c++) {
                char tileMapChar = row.charAt(c);
                int x = c * squareSize;
                int y = r * squareSize;

                if (tileMapChar == 'X') {
                    walls.add(new Block(gameSprites.getWallImage(), x, y, squareSize, tileMapChar));
                } else if (tileMapChar == 'b') {
                    ghosts.add(new Block(gameSprites.getGhostImage('b', 'R'), x, y, squareSize, tileMapChar));
                } else if (tileMapChar == 'o') {
                    ghosts.add(new Block(gameSprites.getGhostImage('o', 'R'), x, y, squareSize, tileMapChar));
                } else if (tileMapChar == 'p') {
                    ghosts.add(new Block(gameSprites.getGhostImage('p', 'R'), x, y, squareSize, tileMapChar));
                } else if (tileMapChar == 'r') {
                    ghosts.add(new Block(gameSprites.getGhostImage('r', 'R'), x, y, squareSize, tileMapChar));
                } else if (tileMapChar == 'P') {
                    pacman = new Block(gameSprites.getPacmanImage('R', true), x, y, squareSize, tileMapChar);
                } else if (tileMapChar == ' ') {
                    foods.add(new Block(gameSprites.getPelletImage(), x, y, squareSize, tileMapChar));
                }
            }
        }

        this.tileMap = tileMap;
        this.walls = walls;
        this.foods = foods;
        this.ghosts = ghosts;
        this.pacman = pacman;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    // Геттеры для полей класса
    public List<String> getTileMap() {
        return tileMap;
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

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}