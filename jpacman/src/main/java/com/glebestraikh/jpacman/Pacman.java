package com.glebestraikh.jpacman;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class Pacman extends JPanel implements ActionListener, KeyListener {
    private final int rowCount = 21;
    private final int columnCount = 19;
    private final int tileSize = 32;
    private final int boardWidth = columnCount * tileSize;
    private final int boardHeight = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeUpGhostImage;
    private Image orangeDownGhostImage;
    private Image orangeRightGhostImage;
    private Image orangeLeftGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image pelletImage;
    private Image pacmanOpenUpImage;
    private Image pacmanOpenDownImage;
    private Image pacmanOpenLeftImage;
    private Image pacmanOpenRightImage;
    private Image pacmanClosedImage;

    private boolean mouthOpen = true;
    private int mouthAnimationCounter = 0;
    private int mouthAnimationSpeed = 5;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
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

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameLoop;
    char[] directions = {'U', 'D', 'L', 'R'}; //up down left right
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;

    Pacman() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        //load images
        loadImages();
        loadMap();

        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, tileSize);
        }
        //how long it takes to start timer, milliseconds gone between frames
        gameLoop = new Timer(41, this); //20fps (1000/50)
        gameLoop.start();
    }

    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("/sprite/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/sprite/blueGhost.png")).getImage();

        orangeUpGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeDownGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeLeftGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeRightGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();

        pinkGhostImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/sprite/redGhost.png")).getImage();
        pelletImage = new ImageIcon(getClass().getResource("/sprite/pellet.png")).getImage();

        pacmanOpenUpImage = new ImageIcon(getClass().getResource("/sprite/pacmanUp.png")).getImage();
        pacmanOpenDownImage = new ImageIcon(getClass().getResource("/sprite/pacmanDown.png")).getImage();
        pacmanOpenLeftImage = new ImageIcon(getClass().getResource("/sprite/pacmanLeft.png")).getImage();
        pacmanOpenRightImage = new ImageIcon(getClass().getResource("/sprite/pacmanRight.png")).getImage();
        pacmanClosedImage = new ImageIcon(getClass().getResource("/sprite/pacmanClosed.png")).getImage();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c * tileSize;
                int y = r * tileSize;

                if (tileMapChar == 'X') { //block wall
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                } else if (tileMapChar == 'b') { //blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'o') { //orange ghost
                    Block ghost = new Block(orangeRightGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'p') { //pink ghost
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'r') { //red ghost
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') { //pacman
                    pacman = new Block(pacmanOpenRightImage, x, y, tileSize, tileSize);
                } else if (tileMapChar == ' ') { //food
                    Block food = new Block(pelletImage, x, y, tileSize, tileSize);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        for (Block food : foods) {
            g.drawImage(food.image, food.x, food.y, food.width, food.height, null);
        }

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), tileSize / 2, tileSize / 2);
        } else {
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), tileSize / 2, tileSize / 2);
        }
    }

    public void move() {
        if (GameUtils.IslocatedInSquare(pacman, tileSize)) {
            if (GameUtils.canChangeDirection(pacman, pacman.desiredDirection, walls, tileSize)) {
                pacman.updateDirection(pacman.desiredDirection, walls, tileSize);
                updatePacmanImage();
            }
        }

        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        //check wall collisions
        for (Block wall : walls) {
            if (GameUtils.collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        mouthAnimationCounter++;
        if (mouthAnimationCounter >= mouthAnimationSpeed) {
            mouthAnimationCounter = 0;
            mouthOpen = !mouthOpen;
        }
        updatePacmanImage();

        //check ghost collisions
        for (Block ghost : ghosts) {
            if (GameUtils.collision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }

            if (ghost.y == tileSize * 9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U', walls, tileSize);
            }

            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (GameUtils.collision(ghost, wall)) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection, walls, tileSize);
                }
            }
        }

        //check food collision
        Block foodEaten = null;
        for (Block food : foods) {
            if (GameUtils.collision(pacman, food)) {
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

    public void resetPositions() {
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection, walls, tileSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

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
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.desiredDirection = 'U';
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.desiredDirection = 'D';
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.desiredDirection = 'L';
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.desiredDirection = 'R';
        }
    }

    public void updatePacmanImage() {
        if (mouthOpen) {
            if (pacman.direction == 'U') {
                pacman.image = pacmanOpenUpImage;
            } else if (pacman.direction == 'D') {
                pacman.image = pacmanOpenDownImage;
            } else if (pacman.direction == 'L') {
                pacman.image = pacmanOpenLeftImage;
            } else if (pacman.direction == 'R') {
                pacman.image = pacmanOpenRightImage;
            }
        } else {
            pacman.image = pacmanClosedImage;
        }
    }
}