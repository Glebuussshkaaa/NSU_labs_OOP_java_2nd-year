package com.glebestraikh.jpacman.util;

public class PacmanConfigurationException extends RuntimeException {

    public PacmanConfigurationException(String message) {
        super(message);
    }

    public PacmanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
