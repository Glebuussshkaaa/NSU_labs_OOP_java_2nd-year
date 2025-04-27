package com.glebestraikh.jpacman;

import java.awt.Image;
import javax.swing.ImageIcon;

public class GameResources {
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

    public GameResources() {
        loadImages();
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

    // Getters
    public Image getWallImage() {
        return wallImage;
    }

    public Image getBlueGhostImage() {
        return blueGhostImage;
    }

    public Image getOrangeGhostImage(char direction) {
        if (direction == 'U') return orangeUpGhostImage;
        if (direction == 'D') return orangeDownGhostImage;
        if (direction == 'L') return orangeLeftGhostImage;
        return orangeRightGhostImage;
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