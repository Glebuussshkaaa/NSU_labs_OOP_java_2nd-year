package com.glebestraikh.jpacman;

import java.awt.Image;
import javax.swing.ImageIcon;

public class GameSprites {
    private Image wallImage;

    private Image pelletImage;

    private Image blueGhostImage;

    private Image orangeUpGhostImage;
    private Image orangeGhostDownImage;
    private Image orangeGhostRightImage;
    private Image orangeGhostLeftImage;

    private Image pinkGhostImage;

    private Image redGhostImage;

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
        blueGhostImage = new ImageIcon(getClass().getResource("/sprite/blueGhost/blueGhostRight.png")).getImage();

        orangeUpGhostImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeGhostDownImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeGhostLeftImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();
        orangeGhostRightImage = new ImageIcon(getClass().getResource("/sprite/orangeGhost/orangeGhostRight.png")).getImage();

        pinkGhostImage = new ImageIcon(getClass().getResource("/sprite/pinkGhost/pinkGhostRight.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/sprite/redGhost/redGhostRight.png")).getImage();
        pelletImage = new ImageIcon(getClass().getResource("/sprite/pellet.png")).getImage();

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

    public Image getBlueGhostImage() {
        return blueGhostImage;
    }

    public Image getOrangeGhostImage(char direction) {
        if (direction == 'U') return orangeUpGhostImage;
        if (direction == 'D') return orangeGhostDownImage;
        if (direction == 'L') return orangeGhostLeftImage;
        return orangeGhostRightImage;
    }

    public Image getPinkGhostImage() {
        return pinkGhostImage;
    }

    public Image getRedGhostImage() {
        return redGhostImage;
    }

    public Image getPelletImage() {
        return pelletImage;
    }

    public Image getPacmanImage(char direction, boolean mouthOpen) {
        if (!mouthOpen) {
            return pacmanClosedImage;
        }

        switch (direction) {
            case 'U': return pacmanOpenUpImage;
            case 'D': return pacmanOpenDownImage;
            case 'L': return pacmanOpenLeftImage;
            case 'R': return pacmanOpenRightImage;
            default: return pacmanOpenRightImage;
        }
    }
}