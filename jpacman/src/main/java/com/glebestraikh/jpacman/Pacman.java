package com.glebestraikh.jpacman;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.Random;
import javax.swing.*;

public class Pacman extends JPanel implements ActionListener, KeyListener {
    private final int rowCount = 21;
    private final int columnCount = 19;
    private final int tileSize = 32;
    private final int boardWidth;
    private final int boardHeight;

    private boolean mouthOpen = true;
    private int mouthAnimationCounter = 0;
    private final int mouthAnimationSpeed = 5;

    private final GameResources resources;
    private final MapLoader mapLoader;

    private Set<Block> walls;
    private Set<Block> foods;
    private Set<Block> ghosts;
    private Block pacman;

    private final Timer gameLoop;
    private final char[] directions = {'U', 'D', 'L', 'R'}; //up down left right
    private final Random random = new Random();
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    public Pacman() {
        boardWidth = columnCount * tileSize;
        boardHeight = rowCount * tileSize;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // Initialize game resources and map
        resources = new GameResources();
        mapLoader = new MapLoader(resources, rowCount, columnCount, tileSize);
        initializeGame();

        // Set up game loop
        gameLoop = new Timer(41, this); // 20fps (1000/50)
        gameLoop.start();
    }

    private void initializeGame() {
        mapLoader.loadMap();
        walls = mapLoader.getWalls();
        foods = mapLoader.getFoods();
        ghosts = mapLoader.getGhosts();
        pacman = mapLoader.getPacman();

        // Initialize ghost directions
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, tileSize);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Draw walls
        for (Block wall : walls) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(),
                    wall.getWidth(), wall.getHeight(), null);
        }

        // Draw foods
        for (Block food : foods) {
            g.drawImage(food.getImage(), food.getX(), food.getY(),
                    food.getWidth(), food.getHeight(), null);
        }

        // Draw ghosts
        for (Block ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(),
                    ghost.getWidth(), ghost.getHeight(), null);
        }

        // Draw pacman
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(),
                pacman.getWidth(), pacman.getHeight(), null);

        // Draw score and lives
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        if (gameOver) {
            g.drawString("Game Over: " + score, tileSize / 2, tileSize / 2);
        } else {
            g.drawString("x" + lives + " Score: " + score, tileSize / 2, tileSize / 2);
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
        if (CollisionDetector.isAlignedToGrid(pacman, tileSize)) {
            if (CollisionDetector.canChangeDirection(pacman, pacman.getDesiredDirection(), walls, tileSize)) {
                pacman.updateDirection(pacman.getDesiredDirection(), walls, tileSize);
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
            if (ghost.getY() == tileSize * 9 &&
                    ghost.getDirection() != 'U' &&
                    ghost.getDirection() != 'D') {
                ghost.updateDirection('U', walls, tileSize);
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
                ghost.updateDirection(newDirection, walls, tileSize);
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
            ghost.updateDirection(newDirection, walls, tileSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        }
        repaint();

        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not used
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
        pacman.setImage(resources.getPacmanImage(pacman.getDirection(), mouthOpen));
    }
}