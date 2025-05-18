package com.glebestraikh.carfactory.ui.gui.components;

import com.glebestraikh.carfactory.ui.UIController;

public class EngineInfoPanel extends InfoPanel {

    public EngineInfoPanel(UIController controller) {
        super(controller);
        controller.addEngineStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Engine produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Engines in storages: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Engine production time in sec: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getEngineStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setEngineProductionTime(time);
    }
}
