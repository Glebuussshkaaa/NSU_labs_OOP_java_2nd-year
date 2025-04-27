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

    private final int rowCount;
    private final int columnCount;
    private final int tileSize;
    private final GameResources resources;

    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;

    public MapLoader(GameResources resources, int rowCount, int columnCount, int tileSize) {
        this.resources = resources;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tileSize = tileSize;
    }

    public void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c * tileSize;
                int y = r * tileSize;

                if (tileMapChar == 'X') { //block wall
                    Block wall = new Block(resources.getWallImage(), x, y, tileSize, tileSize);
                    walls.add(wall);
                } else if (tileMapChar == 'b') { //blue ghost
                    Block ghost = new Block(resources.getBlueGhostImage(), x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'o') { //orange ghost
                    Block ghost = new Block(resources.getOrangeGhostImage('R'), x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'p') { //pink ghost
                    Block ghost = new Block(resources.getPinkGhostImage(), x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'r') { //red ghost
                    Block ghost = new Block(resources.getRedGhostImage(), x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') { //pacman
                    pacman = new Block(resources.getPacmanImage('R', true), x, y, tileSize, tileSize);
                } else if (tileMapChar == ' ') { //food
                    Block food = new Block(resources.getPelletImage(), x, y, tileSize, tileSize);
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
}