package com.glebestraikh.jpacman;

import java.util.Set;

public class CollisionDetector {
    public static boolean checkCollision(Block a, Block b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }

    public static boolean isAlignedToGrid(Block block, int tileSize) {
        return block.getX() % tileSize == 0 && block.getY() % tileSize == 0;
    }

    public static boolean canChangeDirection(Block block, char newDirection, Set<Block> walls, int tileSize) {
        int nextX = block.getX();
        int nextY = block.getY();

        if (newDirection == 'U') {
            nextY -= tileSize;
        } else if (newDirection == 'D') {
            nextY += tileSize;
        } else if (newDirection == 'L') {
            nextX -= tileSize;
        } else if (newDirection == 'R') {
            nextX += tileSize;
        }

        Block virtualBlock = new Block(null, nextX, nextY, block.getWidth(), block.getHeight());

        for (Block wall : walls) {
            if (checkCollision(virtualBlock, wall)) {
                return false;
            }
        }
        return true;
    }
}