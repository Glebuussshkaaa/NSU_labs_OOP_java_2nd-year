package com.glebestraikh.jpacman.model;

import java.awt.Image;
import javax.swing.ImageIcon;

public class GameSprites {
    private Image wallImage;

    private Image pelletImage;

    private Image blueUpGhostImage;
    private Image blueGhostDownImage;
    private Image blueGhostRightImage;
    private Image blueGhostLeftImage;

    private Image orangeUpGhostImage;
    private Image orangeGhostDownImage;
    private Image orangeGhostRightImage;
    private Image orangeGhostLeftImage;

    private Image pinkUpGhostImage;
    private Image pinkGhostDownImage;
    private Image pinkGhostRightImage;
    private Image pinkGhostLeftImage;

    private Image redUpGhostImage;
    private Image redGhostDownImage;
    private Image redGhostRightImage;
    private Image redGhostLeftImage;

    private Image pacmanOpenUpImage;
    private Image pacmanOpenDownImage;
    private Image pacmanOpenLeftImage;
    private Image pacmanOpenRightImage;
    private Image pacmanClosedImage;

    public GameSprites() {
        loadImages();
    }

    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("/sprite/wall.png")).getImage();

        pelletImage = new ImageIcon(getClass().getResource("/sprite/pellet.png")).getImage();

        blueUpGhostImage = new ImageIcon(getClass().getResource("/sprite/blueGhost/blueGhostUp.png")).getImage();
        blueGhostDownImage = new ImageIcon(getClass().getResource("/sprite/blueGhost/blueGhostDown.png")).getImage();
        blueGhostLeftImage = new ImageIcon(getClass().getResource("/sprite/blueGhost/blueGhostLeft.png")).getImage();
        blueGhostRightImage = new ImageIcon(getClass().getResource("/sprite/blueGhost/blueGhostRight.png")).getImage();

        orangeUpGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostUp.png")).getImage();
        orangeGhostDownImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostDown.png")).getImage();
        orangeGhostLeftImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostLeft.png")).getImage();
        orangeGhostRightImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();

        pinkUpGhostImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost/pinkGhostUp.png")).getImage();
        pinkGhostDownImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost/pinkGhostDown.png")).getImage();
        pinkGhostLeftImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost/pinkGhostLeft.png")).getImage();
        pinkGhostRightImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost/pinkGhostRight.png")).getImage();

        redUpGhostImage = new ImageIcon(getClass().getResource("/sprite/redGhost/redGhostUp.png")).getImage();
        redGhostDownImage = new ImageIcon(getClass().getResource("/sprite/redGhost/redGhostDown.png")).getImage();
        redGhostLeftImage = new ImageIcon(getClass().getResource("/sprite/redGhost/redGhostLeft.png")).getImage();
        redGhostRightImage = new ImageIcon(getClass().getResource("/sprite/redGhost/redGhostRight.png")).getImage();

        pacmanOpenUpImage = new ImageIcon(getClass().getResource("/sprite/pacman/pacmanUp.png")).getImage();
        pacmanOpenDownImage = new ImageIcon(getClass().getResource("/sprite/pacman/pacmanDown.png")).getImage();
        pacmanOpenLeftImage = new ImageIcon(getClass().getResource("/sprite/pacman/pacmanLeft.png")).getImage();
        pacmanOpenRightImage = new ImageIcon(getClass().getResource("/sprite/pacman/pacmanRight.png")).getImage();
        pacmanClosedImage = new ImageIcon(getClass().getResource("/sprite/pacman/pacmanClosed.png")).getImage();
    }

    // Getters
    public Image getWallImage() {
        return wallImage;
    }

    public Image getGhostImage(char ghostType, char direction) {
        switch (ghostType) {
            case 'b':
                if (direction == 'U') return blueUpGhostImage;
                if (direction == 'D') return blueGhostDownImage;
                if (direction == 'L') return blueGhostLeftImage;
                return blueGhostRightImage;

            case 'o':
                if (direction == 'U') return orangeUpGhostImage;
                if (direction == 'D') return orangeGhostDownImage;
                if (direction == 'L') return orangeGhostLeftImage;
                return orangeGhostRightImage;

            case 'p':
                if (direction == 'U') return pinkUpGhostImage;
                if (direction == 'D') return pinkGhostDownImage;
                if (direction == 'L') return pinkGhostLeftImage;
                return pinkGhostRightImage;

            case 'r':
                if (direction == 'U') return redUpGhostImage;
                if (direction == 'D') return redGhostDownImage;
                if (direction == 'L') return redGhostLeftImage;
                return redGhostRightImage;

            default:
                return null;
        }
    }

    public Image getPelletImage() {
        return pelletImage;
    }

    public Image getPacmanImage(char direction, boolean mouthOpen) {
        if (!mouthOpen) {
            return pacmanClosedImage;
        }

        return switch (direction) {
            case 'U' -> pacmanOpenUpImage;
            case 'D' -> pacmanOpenDownImage;
            case 'L' -> pacmanOpenLeftImage;
            case 'R' -> pacmanOpenRightImage;
            default -> null;
        };
    }
}