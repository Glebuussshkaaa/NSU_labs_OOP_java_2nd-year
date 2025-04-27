package com.glebestraikh.jpacman;

import java.util.HashSet;
import java.util.Set;

public class MapLoader {
    private final String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "X       bpo       X",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    private static final int rowCount = 21;
    private static final int columnCount = 19;
    private static final int squareSize = 32;
    private static final int boardWidth = columnCount * squareSize;
    private static final int boardHeight = rowCount * squareSize;

    private final GameSprites GameSprites;

    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;

    public MapLoader(GameSprites resources) {
        this.GameSprites = resources;
    }

    public void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c * squareSize;
                int y = r * squareSize;

                if (tileMapChar == 'X') { //block wall
                    Block wall = new Block(GameSprites.getWallImage(), x, y, squareSize);
                    walls.add(wall);
                } else if (tileMapChar == 'b') { //blue ghost
                    Block ghost = new Block(GameSprites.getBlueGhostImage(), x, y, squareSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'o') { //orange ghost
                    Block ghost = new Block(GameSprites.getOrangeGhostImage('R'), x, y, squareSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'p') { //pink ghost
                    Block ghost = new Block(GameSprites.getPinkGhostImage(), x, y, squareSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'r') { //red ghost
                    Block ghost = new Block(GameSprites.getRedGhostImage(), x, y, squareSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') { //pacman
                    pacman = new Block(GameSprites.getPacmanImage('R', true), x, y, squareSize);
                } else if (tileMapChar == ' ') { //food
                    Block food = new Block(GameSprites.getPelletImage(), x, y, squareSize);
                    foods.add(food);
                }
            }
        }
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