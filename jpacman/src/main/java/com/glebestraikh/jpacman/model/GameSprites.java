package com.glebestraikh.jpacman.model;

import com.glebestraikh.jpacman.util.PacmanConfigurationException;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;

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
        wallImage = loadImage("/sprite/wall.png");
        pelletImage = loadImage("/sprite/pellet.png");

        blueUpGhostImage = loadImage("/sprite/blueGhost/blueGhostUp.png");
        blueGhostDownImage = loadImage("/sprite/blueGhost/blueGhostDown.png");
        blueGhostLeftImage = loadImage("/sprite/blueGhost/blueGhostLeft.png");
        blueGhostRightImage = loadImage("/sprite/blueGhost/blueGhostRight.png");

        orangeUpGhostImage = loadImage("/sprite/orangeGhost/orangeGhostUp.png");
        orangeGhostDownImage = loadImage("/sprite/orangeGhost/orangeGhostDown.png");
        orangeGhostLeftImage = loadImage("/sprite/orangeGhost/orangeGhostLeft.png");
        orangeGhostRightImage = loadImage("/sprite/orangeGhost/orangeGhostRight.png");

        pinkUpGhostImage = loadImage("/sprite/pinkGhost/pinkGhostUp.png");
        pinkGhostDownImage = loadImage("/sprite/pinkGhost/pinkGhostDown.png");
        pinkGhostLeftImage = loadImage("/sprite/pinkGhost/pinkGhostLeft.png");
        pinkGhostRightImage = loadImage("/sprite/pinkGhost/pinkGhostRight.png");

        redUpGhostImage = loadImage("/sprite/redGhost/redGhostUp.png");
        redGhostDownImage = loadImage("/sprite/redGhost/redGhostDown.png");
        redGhostLeftImage = loadImage("/sprite/redGhost/redGhostLeft.png");
        redGhostRightImage = loadImage("/sprite/redGhost/redGhostRight.png");

        pacmanOpenUpImage = loadImage("/sprite/pacman/pacmanUp.png");
        pacmanOpenDownImage = loadImage("/sprite/pacman/pacmanDown.png");
        pacmanOpenLeftImage = loadImage("/sprite/pacman/pacmanLeft.png");
        pacmanOpenRightImage = loadImage("/sprite/pacman/pacmanRight.png");
        pacmanClosedImage = loadImage("/sprite/pacman/pacmanClosed.png");
    }

    private Image loadImage(String path) {
        URL resource = getClass().getResource(path);
        if (resource == null) {
            throw new PacmanConfigurationException("Could not load image resource: " + path);
        }
        return new ImageIcon(resource).getImage();
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
                throw new PacmanConfigurationException("Unknown ghost type: " + ghostType);
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
            default -> throw new PacmanConfigurationException("Unknown pacman direction: " + direction);
        };
    }
}