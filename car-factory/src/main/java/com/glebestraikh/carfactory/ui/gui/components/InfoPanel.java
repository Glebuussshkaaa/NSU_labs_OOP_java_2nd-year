package com.glebestraikh.carfactory.ui.gui.components;

import com.glebestraikh.carfactory.observer.Observer;
import com.glebestraikh.carfactory.observer.StorageContext;
import com.glebestraikh.carfactory.ui.UIController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public abstract class InfoPanel extends JPanel implements Observer {
    private static final int SLIDER_MAX_VALUE = 30;
    private static final int SLIDER_MAJOR_SPACING = SLIDER_MAX_VALUE / 6;
    private static final int SLIDER_MINOR_SPACING = 1;
    private static final Font FONT = new Font(Font.DIALOG, Font.PLAIN, 32);

    protected final UIController controller;
    private final JLabel totalProductCounter = new JLabel();
    private final JLabel currentProductCounterLabel = new JLabel();
    private final JSlider timeSlider = new JSlider(JSlider.HORIZONTAL);

    protected InfoPanel(UIController controller) {
        this.controller = controller;
        initPanel();
    }

    private void initPanel() {
        initTotalProductCount();
        initCurrentProductCountLabel();
        initTimeSlider();

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        add(totalProductCounter, gbc);

        gbc.gridy = 2;
        add(currentProductCounterLabel, gbc);

        gbc.gridy = 4;
        add(createSliderTitle(), gbc);

        gbc.gridy = 5;
        add(timeSlider, gbc);
    }

    private void initTotalProductCount() {
        totalProductCounter.setFont(FONT);
        totalProductCounter.setHorizontalAlignment(SwingConstants.CENTER);
        setTotalProductCount(0);
    }

    private void initCurrentProductCountLabel() {
        currentProductCounterLabel.setFont(FONT);
        currentProductCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setCurrentProductCount(0);
    }

    private void initTimeSlider() {
        timeSlider.setBackground(Color.WHITE);
        timeSlider.setFont(FONT);
        timeSlider.setMajorTickSpacing(SLIDER_MAJOR_SPACING);
        timeSlider.setMinorTickSpacing(SLIDER_MINOR_SPACING);
        timeSlider.setPaintTrack(true);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        timeSlider.setMinimum(0);
        timeSlider.setMaximum(SLIDER_MAX_VALUE);
        timeSlider.setValue(SLIDER_MAX_VALUE / 6);
        timeSlider.addChangeListener(new SliderListener());

        setFactoryTime(SLIDER_MAX_VALUE / 6);
    }

    private JLabel createSliderTitle() {
        JLabel sliderTitle = new JLabel();
        sliderTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sliderTitle.setFont(FONT);
        sliderTitle.setText(getTimeSliderTitle());

        return sliderTitle;
    }

    @Override
    public void update(StorageContext StorageMovingContext) {
        setCurrentProductCount(StorageMovingContext.currentCount());
        setTotalProductCount(StorageMovingContext.totalProduced());
    }

    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int time = Math.max(1, timeSlider.getValue());
            setFactoryTime(time);
        }
    }

    private void setTotalProductCount(Integer totalProductCount) {
        totalProductCounter.setText(getTotalProductCounterTitle() + totalProductCount);
        totalProductCounter.repaint();
    }

    private void setCurrentProductCount(Integer currentProductCount) {
        String values = String.format("%s / %s", currentProductCount, getStorageCapacity());
        currentProductCounterLabel.setText(getCurrentProductCounterTitle() + values);
        currentProductCounterLabel.repaint();
    }

    protected abstract void setFactoryTime(int time);

    protected abstract String getTotalProductCounterTitle();

    protected abstract String getCurrentProductCounterTitle();

    protected abstract String getTimeSliderTitle();

    protected abstract int getStorageCapacity();
}