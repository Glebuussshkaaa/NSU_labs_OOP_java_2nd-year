package com.glebestraikh.jpacman.model;

import com.glebestraikh.jpacman.controller.CollisionDetector;

import java.awt.Image;
import java.util.Set;

public class Block {
    private final char type;
    private int x;
    private int y;
    private final int squareSize;
    private Image image;

    private final int startX;
    private final int startY;
    private char direction = 'U';
    private char desiredDirection = 'U';
    private int velocityX = 0;
    private int velocityY = 0;

    public Block(Image image, int x, int y, int squareSize, char type) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.squareSize = squareSize;
        this.startX = x;
        this.startY = y;
        this.type = type;
    }

    public void updateDirection(char direction, Set<Block> walls, int tileSize) {
        char prevDirection = this.direction;
        this.direction = direction;
        updateVelocity(tileSize);
        this.x += this.velocityX;
        this.y += this.velocityY;
        for (Block wall : walls) {
            if (CollisionDetector.checkCollision(this, wall)) {
                this.x -= this.velocityX;
                this.y -= this.velocityY;
                this.direction = prevDirection;
                updateVelocity(tileSize);
            }
        }
    }

    private void updateVelocity(int tileSize) {
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

    public void reset() {
        this.x = this.startX;
        this.y = this.startY;
    }

    public void updatePosition() {
        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    public void revertMovement() {
        this.x -= this.velocityX;
        this.y -= this.velocityY;
    }

    public void setVelocity(int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public char getDirection() {
        return direction;
    }
    public char getType() {
        return type;
    }

    public char getDesiredDirection() {
        return desiredDirection;
    }

    public void setDesiredDirection(char desiredDirection) {
        this.desiredDirection = desiredDirection;
    }
}