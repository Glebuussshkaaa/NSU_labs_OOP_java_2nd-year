package com.glebestraikh.carfactory.ui.gui;

import com.glebestraikh.carfactory.ui.UIController;
import com.glebestraikh.carfactory.ui.gui.components.AccessoryInfoPanel;
import com.glebestraikh.carfactory.ui.gui.components.BodyInfoPanel;
import com.glebestraikh.carfactory.ui.gui.components.CarInfoPanel;
import com.glebestraikh.carfactory.ui.gui.components.EngineInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorkSpaceFrame {

    private static final String TITLE = "Car Factory";
    private final JFrame frame = new JFrame();
    private final UIController controller;
    private final EngineInfoPanel engineInfoPanel;
    private final BodyInfoPanel bodyInfoPanel;
    private final AccessoryInfoPanel accessoryInfoPanel;
    private final CarInfoPanel carInfoPanel;

    public WorkSpaceFrame(UIController controller) {
        this.controller = controller;

        controller.initFactory();

        engineInfoPanel = new EngineInfoPanel(controller);
        bodyInfoPanel = new BodyInfoPanel(controller);
        accessoryInfoPanel = new AccessoryInfoPanel(controller);
        carInfoPanel = new CarInfoPanel(controller);

        initFrame();

        controller.startFactory();

        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setTitle(TITLE);
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2, 5, 5));
        frame.addWindowListener(new WindowClosingListener());

        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(engineInfoPanel);
        frame.getContentPane().add(bodyInfoPanel);
        frame.getContentPane().add(accessoryInfoPanel);
        frame.getContentPane().add(carInfoPanel);
    }

    private class WindowClosingListener extends WindowAdapter {

        private static final String EXIT_CONFIRM_TITLE = "Confirmation";
        private static final String EXIT_CONFIRM_MESSAGE = "Are you sure?";

        @Override
        public void windowClosing(WindowEvent e) {
            int res = JOptionPane.showConfirmDialog(frame, EXIT_CONFIRM_MESSAGE, EXIT_CONFIRM_TITLE,
                    JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                controller.stopFactory();
                frame.dispose();
                System.exit(0);
            }
        }
    }
}