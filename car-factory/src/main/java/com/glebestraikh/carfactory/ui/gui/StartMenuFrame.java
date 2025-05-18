package com.glebestraikh.carfactory.ui.gui;

import com.glebestraikh.carfactory.ui.UIController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class StartMenuFrame {
    private static final String TITLE = "Welcome To Car Factory";
    private static final String BACKGROUND_IMAGE_FILE = "start_menu_background.png";
    private static final String START_BUTTON_TITLE = "Start";

    private final JFrame frame = new JFrame(TITLE);
    private final UIController controller;

    public StartMenuFrame(UIController controller) {
        this.controller = controller;
        initFrame();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExitConfirmationListener());

        BackgroundPanel backgroundPanel = createBackgroundPanel();
        frame.setContentPane(backgroundPanel);
    }

    private BackgroundPanel createBackgroundPanel() {
        Image bgImage = loadBackgroundImage();
        BackgroundPanel panel = new BackgroundPanel(bgImage);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        MenuButton startButton = createStartButton();
        panel.add(startButton, gbc);

        return panel;
    }

    private Image loadBackgroundImage() {
        URL url = getClass().getClassLoader().getResource(BACKGROUND_IMAGE_FILE);
        if (url == null) {
            throw new IllegalStateException("Background image not found: " + BACKGROUND_IMAGE_FILE);
        }
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    private MenuButton createStartButton() {
        MenuButton button = new MenuButton(START_BUTTON_TITLE);
        button.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new WorkSpaceFrame(controller));
        });
        return button;
    }

    private static class ExitConfirmationListener extends WindowAdapter {
        private static final String TITLE = "Confirmation";
        private static final String MESSAGE = "Are you sure you want to exit?";

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame, MESSAGE, TITLE, JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
                System.exit(0);
            }
        }
    }

    private static class MenuButton extends JButton {
        private static final int FONT_SIZE = 32;

        public MenuButton(String text) {
            super(text);
            setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
            setForeground(Color.BLACK);
            setFocusPainted(true);
            setContentAreaFilled(true);
            setBorderPainted(true);
            setMargin(new Insets(10, 20, 10, 20));
        }
    }

    private static class BackgroundPanel extends JPanel {
        private static final int BORDER_SIZE = 30;
        private final Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
            setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}