package com.glebestraikh.jpacman;

import java.util.HashSet;

public class GameUtils {
    public static boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    public static boolean IslocatedInSquare(Block block, int tileSize) {
        return block.x % tileSize == 0 && block.y % tileSize == 0;
    }

    public static boolean canChangeDirection(Block block, char newDirection, HashSet<Block> walls, int tileSize) {
        int nextX = block.x;
        int nextY = block.y;

        if (newDirection == 'U') {
            nextY -= tileSize;
        } else if (newDirection == 'D') {
            nextY += tileSize;
        } else if (newDirection == 'L') {
            nextX -= tileSize;
        } else if (newDirection == 'R') {
            nextX += tileSize;
        }

        Block virtualBlock = new Block(null, nextX, nextY, block.width, block.height);

        for (Block wall : walls) {
            if (collision(virtualBlock, wall)) {
                return false;
            }
        }
        return true;
    }
}
