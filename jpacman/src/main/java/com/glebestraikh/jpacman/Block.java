package com.glebestraikh.jpacman;

import java.awt.Image;
import java.util.HashSet;

public class Block {
    int x;
    int y;
    int width;
    int height;
    Image image;

    int startX;
    int startY;
    char direction = 'U'; // U D L R

    char desiredDirection = 'U'; // начальное направление
    int velocityX = 0;
    int velocityY = 0;

    Block(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.startY = y;
    }

    void updateDirection(char direction, HashSet<Block> walls, int tileSize) {
        char prevDirection = this.direction;
        this.direction = direction;
        updateVelocity(tileSize);
        this.x += this.velocityX;
        this.y += this.velocityY;
        for (Block wall : walls) {
            if (GameUtils.collision(this, wall)) {
                this.x -= this.velocityX;
                this.y -= this.velocityY;
                this.direction = prevDirection;
                updateVelocity(tileSize);
            }
        }
    }

    void updateVelocity(int tileSize) {
        if (this.direction == 'U') {
            this.velocityX = 0;
            this.velocityY = -tileSize / 8;
        } else if (this.direction == 'D') {
            this.velocityX = 0;
            this.velocityY = tileSize / 8;
        } else if (this.direction == 'L') {
            this.velocityX = -tileSize / 8;
            this.velocityY = 0;
        } else if (this.direction == 'R') {
            this.velocityX = tileSize / 8;
            this.velocityY = 0;
        }
    }

    void reset() {
        this.x = this.startX;
        this.y = this.startY;
    }
}