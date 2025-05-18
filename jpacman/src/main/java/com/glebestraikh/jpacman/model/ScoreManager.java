package com.glebestraikh.jpacman.model;

import com.glebestraikh.jpacman.util.PacmanConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class ScoreManager {
    private static final String SCORE_FILE_NAME = "pacmanHighScore.txt";
    private static final String HIGH_SCORE_KEY = "highScore";
    private int highScore;
    private final File scoreFile;

    public ScoreManager() {
        this.scoreFile = getScoreFile();
        this.highScore = loadHighScore();
    }

    private File getScoreFile() {
        try {
            URL resourceUrl = getClass().getResource("/" + SCORE_FILE_NAME);

            if (resourceUrl != null) {
                return new File(resourceUrl.toURI());
            } else {
                File file = new File(SCORE_FILE_NAME);
                if (!file.exists()) {
                    boolean created = file.createNewFile();
                    if (!created) {
                        throw new PacmanConfigurationException("Failed to create new high score file.");
                    }
                }
                return file;
            }
        } catch (Exception e) {
            throw new PacmanConfigurationException("Error locating or creating score file: " + SCORE_FILE_NAME, e);
        }
    }

    private int loadHighScore() {
        Properties properties = new Properties();

        if (scoreFile.exists()) {
            try (FileInputStream fis = new FileInputStream(scoreFile)) {
                properties.load(fis);
                String highScoreStr = properties.getProperty(HIGH_SCORE_KEY, "0");
                return Integer.parseInt(highScoreStr);
            } catch (IOException | NumberFormatException e) {
                throw new PacmanConfigurationException("Error loading high score from file: " + SCORE_FILE_NAME, e);
            }
        }

        return 0;
    }

    public boolean updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            return true;
        }
        return false;
    }

    private void saveHighScore() {
        Properties properties = new Properties();
        properties.setProperty(HIGH_SCORE_KEY, String.valueOf(highScore));

        try (FileOutputStream fos = new FileOutputStream(scoreFile)) {
            properties.store(fos, "PacMan High Score");
        } catch (IOException e) {
            throw new PacmanConfigurationException("Error saving high score to file: " + SCORE_FILE_NAME, e);
        }
    }

    public int getHighScore() {
        return highScore;
    }
}