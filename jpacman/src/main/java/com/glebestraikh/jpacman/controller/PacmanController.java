package com.glebestraikh.jpacman.controller;


import com.glebestraikh.jpacman.model.Block;
import com.glebestraikh.jpacman.model.MapData;
import com.glebestraikh.jpacman.model.GameSprites;
import com.glebestraikh.jpacman.view.PacmanView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Set;
import javax.swing.Timer;

public class PacmanController implements ActionListener, KeyListener {
    private boolean mouthOpen = true;
    private static int mouthAnimationCounter = 0;
    private static final int mouthAnimationSpeed = 5;

    private final MapData gameMap;
    private final GameSprites gameSprites;
    private final PacmanView view;

    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;

    private final Timer gameLoop;
    private final char[] directions = {'U', 'D', 'L', 'R'};
    private final Random random = new Random();
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    public PacmanController(MapData gameMap, GameSprites gameSprites, PacmanView view) {
        this.gameSprites = gameSprites;
        this.gameMap = gameMap;
        this.view = view;

        view.setController(this);
        view.addKeyListener(this);

        initializeGame();

        gameLoop = new Timer(41, this);
        gameLoop.start();
    }

    public void initializeGame() {
        walls = gameMap.getWalls();
        foods = gameMap.getFoods();
        ghosts = gameMap.getGhosts();
        pacman = gameMap.getPacman();

        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, gameMap.getSquareSize());
            updateGhostImage(ghost);
        }
    }

    private void move() {
        movePacman();
        animatePacman();
        moveGhosts();
        checkFoodCollisions();

        if (foods.isEmpty()) {
            resetLevel();
        }
    }

    private void movePacman() {
        if (CollisionDetector.isLocatedInSquare(pacman, gameMap.getSquareSize())) {
            if (CollisionDetector.canChangeDirection(pacman, pacman.getDesiredDirection(), walls, gameMap.getSquareSize())) {
                pacman.updateDirection(pacman.getDesiredDirection(), walls, gameMap.getSquareSize());
                updatePacmanImage();
            }
        }

        pacman.updatePosition();

        // Check wall collisions
        for (Block wall : walls) {
            if (CollisionDetector.checkCollision(pacman, wall)) {
                pacman.revertMovement();
                break;
            }
        }
    }

    private void animatePacman() {
        mouthAnimationCounter++;
        if (mouthAnimationCounter >= mouthAnimationSpeed) {
            mouthAnimationCounter = 0;
            mouthOpen = !mouthOpen;
            updatePacmanImage();
        }
    }

    private void moveGhosts() {
        for (Block ghost : ghosts) {
            // Check if pacman collided with ghost
            if (CollisionDetector.checkCollision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
                return;
            }

            // Special behavior for ghosts at a certain Y position
            if (ghost.getY() == gameMap.getSquareSize() * 9 &&
                    ghost.getDirection() != 'U' &&
                    ghost.getDirection() != 'D') {
                ghost.updateDirection('U', walls, gameMap.getSquareSize());
            }

            // Move ghost
            ghost.updatePosition();

            // Check wall collisions for ghosts
            boolean collided = false;
            for (Block wall : walls) {
                if (CollisionDetector.checkCollision(ghost, wall)) {
                    ghost.revertMovement();
                    collided = true;
                    break;
                }
            }

            // If ghost hit a wall, change direction
            if (collided) {
                char newDirection = directions[random.nextInt(4)];
                ghost.updateDirection(newDirection, walls, gameMap.getSquareSize());
                updateGhostImage(ghost);
            }
        }
    }

    private void checkFoodCollisions() {
        Block foodEaten = null;
        for (Block food : foods) {
            if (CollisionDetector.checkCollision(pacman, food)) {
                foodEaten = food;
                score += 10;
                break;
            }
        }

        if (foodEaten != null) {
            foods.remove(foodEaten);
        }
    }

    private void resetLevel() {
        initializeGame();
        resetPositions();
    }

    private void resetPositions() {
        pacman.reset();
        pacman.setVelocity(0, 0);

        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, gameMap.getSquareSize());
            updateGhostImage(ghost);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        }

        view.repaint();

        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            resetGame();
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                pacman.setDesiredDirection('U');
                break;
            case KeyEvent.VK_DOWN:
                pacman.setDesiredDirection('D');
                break;
            case KeyEvent.VK_LEFT:
                pacman.setDesiredDirection('L');
                break;
            case KeyEvent.VK_RIGHT:
                pacman.setDesiredDirection('R');
                break;
        }
    }

    private void resetGame() {
        resetLevel();
        lives = 3;
        score = 0;
        gameOver = false;
        gameLoop.start();
    }

    private void updatePacmanImage() {
        pacman.setImage(gameSprites.getPacmanImage(pacman.getDirection(), mouthOpen));
    }

    private void updateGhostImage(Block ghost) {
        char type = ghost.getType();
        ghost.setImage(gameSprites.getGhostImage(type, ghost.getDirection()));
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return gameOver;
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