package com.glebestraikh.carfactory.ui.gui.components;

import com.glebestraikh.carfactory.ui.UIController;

public class AccessoryInfoPanel extends InfoPanel {
    public AccessoryInfoPanel(UIController controller) {
        super(controller);
        controller.addAccessoryStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Accessory produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Accessories in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Accessory production time in sec: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getAccessoryStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setAccessoryProductionTime(time);
    }
}

