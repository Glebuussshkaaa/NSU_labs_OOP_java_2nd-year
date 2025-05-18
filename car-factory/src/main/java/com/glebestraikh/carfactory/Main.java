package com.glebestraikh.carfactory;

import com.glebestraikh.carfactory.ui.UIController;
import com.glebestraikh.carfactory.ui.gui.StartMenuFrame;

import javax.swing.SwingUtilities;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        try (InputStream config = Main.class.getResourceAsStream("/logging.properties")) {
            if (config == null) {
                throw new java.io.FileNotFoundException("Could not find logging.properties in resources.");
            }
            LogManager.getLogManager().readConfiguration(config);
        } catch (Exception e) {
            System.err.println("Failed to load logging.properties: " + e.getMessage());
            System.exit(1);
        }

        UIController controller = new UIController();
        SwingUtilities.invokeLater(() -> new StartMenuFrame(controller));
    }
}