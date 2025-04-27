package com.glebestraikh.jpacman;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.Random;
import javax.swing.*;

public class Pacman extends JPanel implements ActionListener, KeyListener {
    private boolean mouthOpen = true;
    private int mouthAnimationCounter = 0;
    private final int mouthAnimationSpeed = 5;

    private final GameSprites GameSprites;
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
        GameSprites = new GameSprites();
        mapLoader = new MapLoader(GameSprites);

        setPreferredSize(new Dimension(mapLoader.getBoardWidth(), mapLoader.getBoardHeight()));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        initializeGame();

        gameLoop = new Timer(41, this);
        gameLoop.start();
    }

    private void initializeGame() {
        mapLoader.loadMap();
        walls = mapLoader.getWalls();
        foods = mapLoader.getFoods();
        ghosts = mapLoader.getGhosts();
        pacman = mapLoader.getPacman();

        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, mapLoader.getSquareSize());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        for (Block wall : walls) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(),
                    wall.getSquareSize(), wall.getSquareSize(), null);
        }

        for (Block food : foods) {
            g.drawImage(food.getImage(), food.getX(), food.getY(),
                    food.getSquareSize(), food.getSquareSize(), null);
        }

        for (Block ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(),
                    ghost.getSquareSize(), ghost.getSquareSize(), null);
        }

        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(),
                pacman.getSquareSize(), pacman.getSquareSize(), null);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        if (gameOver) {
            g.drawString("Game Over: " + score,  mapLoader.getSquareSize() / 2,  mapLoader.getSquareSize() / 2);
        } else {
            g.drawString("x" + lives + " Score: " + score,  mapLoader.getSquareSize() / 2,  mapLoader.getSquareSize() / 2);
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
        if (CollisionDetector.isLocatedInSquare(pacman,  mapLoader.getSquareSize())) {
            if (CollisionDetector.canChangeDirection(pacman, pacman.getDesiredDirection(), walls,  mapLoader.getSquareSize())) {
                pacman.updateDirection(pacman.getDesiredDirection(), walls,  mapLoader.getSquareSize());
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
            if (ghost.getY() ==  mapLoader.getSquareSize() * 9 &&
                    ghost.getDirection() != 'U' &&
                    ghost.getDirection() != 'D') {
                ghost.updateDirection('U', walls,  mapLoader.getSquareSize());
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
                ghost.updateDirection(newDirection, walls,  mapLoader.getSquareSize());
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
            ghost.updateDirection(newDirection, walls,  mapLoader.getSquareSize());
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
        pacman.setImage(GameSprites.getPacmanImage(pacman.getDirection(), mouthOpen));
    }
}